package net.dryanhild.collada.schema15.fx;

/**
 * Indicates an instance of an image.
 * 
 */
public interface ImageInstance {

    int getWidth();

    int getHeight();

    byte[] getImageData();

    public enum Format {
        GREYSCALE, RGB, RGBA
    }

}
