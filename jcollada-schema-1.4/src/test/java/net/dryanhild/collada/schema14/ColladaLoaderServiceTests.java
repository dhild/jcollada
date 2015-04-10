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

import net.dryanhild.collada.ColladaLoader;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ColladaLoaderServiceTests {

    private static final String FRAGMENT_141 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                               "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" " +
                                               "version=\"1.4.1\">\n" +
                                               "</COLLADA>";

    private static final String FRAGMENT_140 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                               "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" " +
                                               "version=\"1.4.0\">\n" +
                                               "</COLLADA>";

    private ColladaLoader colladaLoader = new ColladaLoader();

    public ColladaLoaderServiceTests() {
        colladaLoader.setValidating(false);
    }

    @Test
    public void loaderHasVersions14() {
        assertThat(colladaLoader.getRegisteredVersions())
                .contains(ColladaLoaderService14.VERSION_1_4_0, ColladaLoaderService14.VERSION_1_4_1);
    }

//    @Test
//    public void loaderCanRecognize140() throws IOException {
//        byte[] bytes = FRAGMENT_140.getBytes(StandardCharsets.UTF_8);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        assertThat(colladaLoader.load(URI.create("test.dae"), inputStream)).isNotNull();
//        inputStream.close();
//    }
//
//    @Test
//    public void loaderCanRecognize141() throws IOException {
//        byte[] bytes = FRAGMENT_141.getBytes(StandardCharsets.UTF_8);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        assertThat(colladaLoader.load(URI.create("test.dae"), inputStream)).isNotNull();
//        inputStream.close();
//    }
}
