package net.dryanhild.collada.schema15.fx;

public interface Sampler {

    ImageInstance getImage();

    WrapStyle getWrapS();

    WrapStyle getWrapT();

    WrapStyle getWrapP();

    FilterStyle getMinFilter();

    FilterStyle getMagFilter();

    FilterStyle getMipFilter();

    float[] getBorderColor();

    public enum WrapStyle {
        WRAP, MIRROR, CLAMP, BORDER
    }

    public enum FilterStyle {
        NONE, NEAREST, LINEAR
    }

}
