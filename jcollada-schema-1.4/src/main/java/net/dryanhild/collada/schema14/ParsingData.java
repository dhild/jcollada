/*
 * Copyright (c) 2014 D. Ryan Hild <d.ryan.hild@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.dryanhild.collada.schema14;

import com.google.common.collect.Lists;
import net.dryanhild.collada.schema14.data.ColladaDocument14;
import net.dryanhild.collada.schema14.data.geometry.MeshImpl;
import net.dryanhild.collada.schema14.data.geometry.Vertices;
import net.dryanhild.collada.schema14.data.geometry.source.FloatSource;
import net.dryanhild.collada.schema14.postprocessors.Postprocessor;
import org.glassfish.hk2.api.PerThread;
import org.jvnet.hk2.annotations.Service;
import org.xmlpull.v1.XmlPullParser;

import java.util.List;

@Service
@PerThread
public class ParsingData {

    public ColladaDocument14 document;

    public XmlPullParser parser;

    public List<FloatSource> sources = Lists.newArrayList();

    public List<Vertices> vertices = Lists.newArrayList();

    public List<MeshImpl> meshes = Lists.newArrayList();

    public List<Postprocessor> postprocessors = Lists.newArrayList();

    public void reset(XmlPullParser parser) {
        this.parser = parser;
        sources.clear();
        vertices.clear();
        meshes.clear();
        postprocessors.clear();
    }

}
