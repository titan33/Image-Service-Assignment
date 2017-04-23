package nl.debijenkorf.bsl.services.impl;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import nl.debijenkorf.bsl.core.PredefinedImageTypes;
import nl.debijenkorf.bsl.services.BSLStartService;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amelingm on 16-2-2015.
 */
public class BSLStartServiceImpl implements BSLStartService {

    // this map is to simulate Amazon S3 persistent bucketscale
    private static final Map<String, Object> AMAZON_S3_Bucket = new ConcurrentHashMap<String, Object>();


    public String getMessage(){
        return "Thunderbirds are go..!";
    }

    @Override
    public byte[] obtainImage(final PredefinedImageTypes predefinedImageTypes, final String filename, final String imageType) throws Exception {

        String imageKey = predefinedImageTypes.getTypeName() +
                predefinedImageTypes.getHeight() +
                predefinedImageTypes.getWidth() +
                predefinedImageTypes.getQuality() +
                predefinedImageTypes.getScaleType() +
                predefinedImageTypes.getFillColor() +
                imageType + filename;

        // first validate request properties
        Assert.notNull(predefinedImageTypes.getTypeName(),"Type name must not be null");
        Assert.notNull(predefinedImageTypes.getHeight(),"Height must not be null");
        Assert.notNull(predefinedImageTypes.getWidth(),"Width must not be null");
        Assert.notNull(predefinedImageTypes.getQuality(),"Quality must not be null");
        Assert.notNull(predefinedImageTypes.getScaleType(),"Scale type must not be null");
        Assert.notNull(predefinedImageTypes.getFillColor(),"Fill color must not be null");
        Assert.notNull(imageType,"Image type must not be null");
        Assert.isTrue(1 <= predefinedImageTypes.getQuality() && predefinedImageTypes.getQuality() <= 100,"Quality must be between 1 and 100");



        final BufferedImage image, newImage;

        if (!AMAZON_S3_Bucket.containsKey(imageKey)) {
            //get the hardcoded image  13_0777701000516430_pro_flt_frt_01_1108_1528_1022139.jpg
            URL url = new URL("http://cdn.debijenkorf.nl/INTERSHOP/static/WFS/dbk-shop-Site/-/dbk-shop/nl_NL/product-images/077/770/" + filename);
            image = ImageIO.read(url);
            //do some cool stuff here using the "definition" and "filename" variables

            if ("Crop".equals(predefinedImageTypes.getScaleType().toString())) {
                newImage = Thumbnails.of(image).crop(Positions.CENTER_LEFT).
                        size(predefinedImageTypes.getWidth(), predefinedImageTypes.getHeight()).
                        keepAspectRatio(false).outputQuality(predefinedImageTypes.getQuality() / 100).
                        outputFormat(imageType).asBufferedImage();

            } else if ("Fill".equals(predefinedImageTypes.getScaleType().toString())) { // this is not done
                newImage = Thumbnails.of(image).crop(Positions.CENTER_LEFT).
                        size(predefinedImageTypes.getWidth(), predefinedImageTypes.getHeight()).
                        keepAspectRatio(true).outputQuality(predefinedImageTypes.getQuality() / 100).
                        outputFormat(imageType).asBufferedImage();
            } else { // scale type is Skew
                newImage = Thumbnails.of(image).
                        forceSize(predefinedImageTypes.getWidth(), predefinedImageTypes.getHeight()).
                        outputQuality(predefinedImageTypes.getQuality() / 100).
                        outputFormat(imageType).asBufferedImage();
            }
            AMAZON_S3_Bucket.put(imageKey, newImage);
        } else {
            newImage = (BufferedImage) AMAZON_S3_Bucket.get(imageKey);
        }

        //prepare the output stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, imageType, baos);
        baos.flush();

        return baos.toByteArray();
    }


}
