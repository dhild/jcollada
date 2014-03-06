package net.dryanhild.collada.schema15.data.fx.glsl;

import net.dryanhild.collada.data.fx.FilterStyle;
import net.dryanhild.collada.data.fx.ImageInstance;
import net.dryanhild.collada.data.fx.ImageWrapStyle;
import net.dryanhild.collada.data.fx.Sampler;

public class Sampler2DUniform implements Sampler {

    private ImageWrapStyle wrapS;
    private ImageWrapStyle wrapT;
    private ImageWrapStyle wrapP;
    private FilterStyle minFilter;
    private FilterStyle magFilter;
    private FilterStyle mipFilter;
    private float[] borderColor;

    @Override
    public ImageInstance getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getDimensions() {
        return 2;
    }

    public void setWrapS(ImageWrapStyle wrapS) {
        this.wrapS = wrapS;
    }

    public void setWrapT(ImageWrapStyle wrapT) {
        this.wrapT = wrapT;
    }

    public void setWrapP(ImageWrapStyle wrapP) {
        this.wrapP = wrapP;
    }

    @Override
    public ImageWrapStyle getWrapS() {
        return wrapS;
    }

    @Override
    public ImageWrapStyle getWrapT() {
        return wrapT;
    }

    @Override
    public ImageWrapStyle getWrapP() {
        return wrapP;
    }

    @Override
    public FilterStyle getMinFilter() {
        return minFilter;
    }

    public void setMinFilter(FilterStyle minFilter) {
        this.minFilter = minFilter;
    }

    @Override
    public FilterStyle getMagFilter() {
        return magFilter;
    }

    public void setMagFilter(FilterStyle magFilter) {
        this.magFilter = magFilter;
    }

    @Override
    public FilterStyle getMipFilter() {
        return mipFilter;
    }

    public void setMipFilter(FilterStyle mipFilter) {
        this.mipFilter = mipFilter;
    }

    public void setBorderColor(float[] borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public float[] getBorderColor() {
        return borderColor;
    }

}
