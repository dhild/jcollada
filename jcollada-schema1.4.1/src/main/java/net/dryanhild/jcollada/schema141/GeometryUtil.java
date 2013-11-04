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
package net.dryanhild.jcollada.schema141;

import javax.media.j3d.Shape3D;

import net.dryanhild.jcollada.LoaderContext;
import net.dryanhild.jcollada.schema141.gen.Geometry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.j3d.loaders.ParsingErrorException;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class GeometryUtil {

    static final Logger logger = LogManager.getLogger(GeometryUtil.class);

    String id;
    String name;
    GeometryElement element;

    public static void createGeometry(Geometry geometry, LoaderContext context) {
        GeometryUtil geom = new GeometryUtil(geometry, context);
        context.addObjectById(geom.id, geom);
    }

    private GeometryUtil(Geometry geometry, LoaderContext context) {
        id = geometry.getId();
        name = geometry.getName();
        if (geometry.getMesh() != null) {
            element = new MeshUtil(geometry, context);
        } else {
            throw new ParsingErrorException("Can't handle geometries without meshes yet.");
        }
    }

    public Shape3D getShape3D() {
        return element.toShape3D();
    }

}
