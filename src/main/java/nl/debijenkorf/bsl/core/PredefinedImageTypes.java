package nl.debijenkorf.bsl.core;

/**
 * Created by Georget on 4/22/2017.
 * This class is used as data binding object between http requeest path variables and object properties.
 * It can also contain request parameter
 */

public class PredefinedImageTypes {

    public enum SCALE {
        Crop("Crop"),Fill("Fill"),Skew("Skew");

        private String type;

        SCALE (String type) {
            this.type = type;
        }

        public String toString() {
            return this.type;
        }
    }

    public enum IMAGETYPE {
        JPG("jpg"),PNG("png");

        private String type;

        IMAGETYPE (String type) {
            this.type = type;
        }

        public String toString() {
            return this.type;
        }
    }


    private String typeName;
    private int height;
    private int width;
    private int quality;
    private SCALE scaleType;
    private int fillColor;
    private IMAGETYPE imageType;
    private String sourceName;


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public SCALE getScaleType() {
        return scaleType;
    }

    public void setScaleType(SCALE scaleType) {
        this.scaleType = scaleType;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public IMAGETYPE getImageType() {
        return imageType;
    }

    public void setImageType(IMAGETYPE imageType) {
        this.imageType = imageType;
    }



    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "PredefinedImageTypes{" +
                "typeName='" + typeName + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", quality=" + quality +
                ", scaleType=" + scaleType +
                ", fillColor=" + fillColor +
                ", imageType=" + imageType +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }
}
