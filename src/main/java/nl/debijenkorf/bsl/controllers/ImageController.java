package nl.debijenkorf.bsl.controllers;

import nl.debijenkorf.bsl.core.PredefinedImageTypes;
import nl.debijenkorf.bsl.services.BSLStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by deBijenkorf on 06/03/15.
 */

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    BSLStartService bslStartService;

    public ImageController(){}

    @RequestMapping(value = {"/show/{typeName}/{height}/{width}/{quality}/{scaleType}/{fillColor}/JPG",
                            "/show/{typeName}/{height}/{width}/{quality}/{scaleType}/{fillColor}/JPG/{sourceName}"}, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showJPGImage (
            @RequestParam(value = "filename", required = true) String filename,
            PredefinedImageTypes predefinedImageTypes
    ) throws Exception{

        byte[] image = bslStartService.obtainImage(predefinedImageTypes, filename, "jpg");

        return image;
    }

    @RequestMapping(value = {"/show/{typeName}/{height}/{width}/{quality}/{scaleType}/{fillColor}/PNG",
            "/show/{typeName}/{height}/{width}/{quality}/{scaleType}/{fillColor}/PNG/{sourceName}"}, method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] showPNGImage (
            @RequestParam(value = "filename", required = true) String filename,
            PredefinedImageTypes predefinedImageTypes
    ) throws Exception{

        byte[] image = bslStartService.obtainImage(predefinedImageTypes, filename,"png");

        return image;
    }


    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String handleException(HttpServletRequest req, Exception  exception) {
        System.out.println("Request: " + req.getRequestURL() + " raised " + exception);
        final String response_html = "<html><b1>" + "Request: " + req.getRequestURL() + " raised " + exception +"</b1></html>";
        return response_html;
    }

}
