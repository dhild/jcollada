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

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.xml.bind.JAXBElement;

import net.dryanhild.jcollada.schema141.gen.InstanceGeometry;
import net.dryanhild.jcollada.schema141.gen.InstanceWithExtra;
import net.dryanhild.jcollada.schema141.gen.Lookat;
import net.dryanhild.jcollada.schema141.gen.Matrix;
import net.dryanhild.jcollada.schema141.gen.Node;
import net.dryanhild.jcollada.schema141.gen.Rotate;
import net.dryanhild.jcollada.schema141.gen.Skew;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class NodeElement {

    final Logger logger = LogManager.getLogger(NodeElement.class);

    String id;
    String name;
    String sid;
    Node nodeType;
    List<NodeElement> childNodes = new ArrayList<>();
    Group instance;

    public static void createNode(Node node) {
        NodeElement element = new NodeElement(node, context);
        if (element.id != null) {
            context.addObjectById(element.id, element);
        }
    }

    public NodeElement(Node node, LoaderContext context) {
        this.context = context;
        id = node.getId();
        name = node.getName();
        sid = node.getSid();
        nodeType = node;

        for (Node n : node.getNodes()) {
            NodeElement element = new NodeElement(n, context);
            if (element.id != null) {
                context.addObjectById(element.id, element);
            }
        }
    }

    public Group getInstance() {
        if (instance == null) {
            instance = constructInstance();
        }
        return instance;
    }

    public Group constructInstance() {
        List<Group> children = new ArrayList<>();
        for (NodeElement n : childNodes) {
            children.add(n.constructInstance());
        }

        if (nodeType.getInstanceNodes() != null) {
            for (InstanceWithExtra inst : nodeType.getInstanceNodes()) {
                NodeElement element = context.getObjectById(id, NodeElement.class);
                Group node = element.constructInstance();
                if (inst.getName() != null) {
                    node.setName(inst.getName());
                }
                children.add(node);
            }
        }

        List<Shape3D> shapes = new ArrayList<>();
        if (nodeType.getInstanceGeometries() != null) {
            for (InstanceGeometry inst : nodeType.getInstanceGeometries()) {
                Shape3D shape = context.getObjectById(inst.getUrl(), GeometryUtil.class).getShape3D();
                if (inst.getName() != null) {
                    shape.setName(inst.getName());
                }
                shapes.add(shape);
            }
        }

        List<Transform3D> transforms = new ArrayList<>();
        if (nodeType.getLookatsAndMatrixesAndRotates() != null) {
            for (Object obj : nodeType.getLookatsAndMatrixesAndRotates()) {
                if (obj instanceof Lookat) {
                    transforms.add(Transforms.getLookatTransform((Lookat) obj));
                } else if (obj instanceof Matrix) {
                    transforms.add(Transforms.getMatrixTransform((Matrix) obj));
                } else if (obj instanceof Rotate) {
                    transforms.add(Transforms.getRotateTransform((Rotate) obj));
                } else if (obj instanceof JAXBElement<?>) {
                    transforms.add(Transforms.getScaleTransform((JAXBElement<?>) obj));
                } else if (obj instanceof Skew) {
                    transforms.add(Transforms.getSkewTransform((Skew) obj));
                } else if (obj instanceof JAXBElement<?>) {
                    transforms.add(Transforms.getTranslateTransform((JAXBElement<?>) obj));
                } else {
                    logger.debug("Can't process transform type " + obj.getClass().getName());
                }
            }
        }

        Group group;
        if (!transforms.isEmpty()) {
            Transform3D transform = new Transform3D();
            for (Transform3D t : transforms) {
                transform.mul(t);
            }
            group = new TransformGroup(transform);
        } else {
            group = new Group();
        }

        for (javax.media.j3d.Node n : children) {
            group.addChild(n);
        }

        for (Shape3D s : shapes) {
            group.addChild(s);
        }

        group.setName(nodeType.getName());

        return group;
    }

}
