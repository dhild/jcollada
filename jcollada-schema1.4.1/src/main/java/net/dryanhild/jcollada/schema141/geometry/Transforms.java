/*
 * Copyright (c) 2013, D. Ryan Hild <d.ryan.hild@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.dryanhild.jcollada.schema141.geometry;

import java.util.List;

import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.xml.bind.JAXBElement;

import net.dryanhild.jcollada.schema141.gen.ColladaMatrix;
import net.dryanhild.jcollada.schema141.gen.Lookat;
import net.dryanhild.jcollada.schema141.gen.Rotate;
import net.dryanhild.jcollada.schema141.gen.Skew;

import com.sun.j3d.loaders.ParsingErrorException;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
@SuppressWarnings("boxing")
public final class Transforms {

    private Transforms() {
    }

    public static Transform3D getLookatTransform(Lookat lookat) {
        List<Double> values = lookat.getValues();
        Point3d eye = new Point3d(values.get(0), values.get(1), values.get(2));
        Point3d center = new Point3d(values.get(3), values.get(4), values.get(5));
        Vector3d up = new Vector3d(values.get(6), values.get(7), values.get(8));
        Transform3D t = new Transform3D();
        t.lookAt(eye, center, up);
        return t;
    }

    public static Matrix4d getMatrix(ColladaMatrix matrix) {
        List<Double> values = matrix.getValues();
        Matrix4d mat = new Matrix4d();
        mat.m00 = values.get(0);
        mat.m01 = values.get(1);
        mat.m02 = values.get(2);
        mat.m03 = values.get(3);
        mat.m10 = values.get(4);
        mat.m11 = values.get(5);
        mat.m12 = values.get(6);
        mat.m13 = values.get(7);
        mat.m20 = values.get(8);
        mat.m21 = values.get(9);
        mat.m22 = values.get(10);
        mat.m23 = values.get(11);
        mat.m30 = values.get(12);
        mat.m31 = values.get(13);
        mat.m32 = values.get(14);
        mat.m33 = values.get(15);
        return mat;
    }

    public static Transform3D getMatrixTransform(ColladaMatrix matrix) {
        return new Transform3D(getMatrix(matrix));
    }

    public static AxisAngle4d getRotation(Rotate rotate) {
        List<Double> values = rotate.getValues();
        AxisAngle4d rotation = new AxisAngle4d(values.get(0), values.get(1), values.get(2), values.get(3));
        return rotation;
    }

    public static Transform3D getRotateTransform(Rotate rotate) {
        Matrix4d matrix = new Matrix4d();
        matrix.setIdentity();
        matrix.set(getRotation(rotate));
        return new Transform3D(matrix);
    }

    public static Matrix4d getScale(JAXBElement<?> scale) {
        if (!"scale".equals(scale.getName())) {
            throw new ParsingErrorException("Expected <scale>, found <" + scale.getName() + ">");
        }
        System.out.println("Type: " + scale.getDeclaredType());
        Matrix4d matrix = new Matrix4d();
        matrix.setIdentity();
        // matrix.m00 = scale.getValues().get(0);
        // matrix.m11 = scale.getValues().get(1);
        // matrix.m22 = scale.getValues().get(2);
        return matrix;
    }

    public static Transform3D getScaleTransform(JAXBElement<?> scale) {
        return new Transform3D(getScale(scale));
    }

    public static Matrix4d getSkew(Skew skew) {
        List<Double> values = skew.getValues();
        double theta = (values.get(0) * Math.PI) / 180.0;
        double t = Math.tan(theta);
        Vector3d v = new Vector3d(values.get(1), values.get(2), values.get(3));
        Vector3d w = new Vector3d(values.get(4), values.get(5), values.get(6));
        v.normalize();
        w.normalize();
        double x1t = v.x * t;
        double y1t = v.y * t;
        double z1t = v.z * t;
        Matrix4d matrix = new Matrix4d();
        matrix.setIdentity();
        matrix.m00 += x1t * w.x;
        matrix.m01 = x1t * w.y;
        matrix.m02 = x1t * w.z;
        matrix.m10 = y1t * w.y;
        matrix.m11 += y1t * w.y;
        matrix.m12 = y1t * w.z;
        matrix.m20 = z1t * w.y;
        matrix.m21 = z1t * w.z;
        matrix.m22 += z1t * w.z;
        return matrix;
    }

    public static Transform3D getSkewTransform(Skew skew) {
        return new Transform3D(getSkew(skew));
    }

    public static Vector3d getTranslate(JAXBElement<?> translate) {
        if (!"translate".equals(translate.getName())) {
            throw new ParsingErrorException("Expected <translate>, found <" + translate.getName() + ">");
        }
        System.out.println("Type: " + translate.getDeclaredType());
        List<Double> values = null;// translate.getValues();
        return new Vector3d(values.get(0), values.get(1), values.get(2));
    }

    public static Transform3D getTranslateTransform(JAXBElement<?> translate) {
        Matrix4d matrix = new Matrix4d();
        matrix.setIdentity();
        matrix.setTranslation(getTranslate(translate));
        return new Transform3D(matrix);
    }

}
