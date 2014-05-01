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

package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.schema14.ColladaLoaderService14;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ColladaLoaderServiceTests {

    public static final String TEST_HEADER_140 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.4.0\">";
    public static final String TEST_HEADER_141 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.4.1\">";
    public static final String TEST_HEADER_150 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.5.0\">";

    ColladaLoaderService14 service;

    @BeforeMethod
    public void resetService() {
        service = new ColladaLoaderService14();
    }

    public void versionsAreSupported() {
        Collection<VersionSupport> versions = service.getColladaVersions();

        assertThat(versions)
                .containsExactly(ColladaLoaderService14.VERSION_1_4_0, ColladaLoaderService14.VERSION_1_4_1);
    }

    public void canLoadVersion140() {
        assert service.canLoad(TEST_HEADER_140);
    }

    public void canLoadVersion141() {
        assert service.canLoad(TEST_HEADER_141);
    }

    public void cannotLoadVersion150() {
        assert !service.canLoad(TEST_HEADER_150);
    }

}
