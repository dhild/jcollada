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
import net.dryanhild.collada.data.ColladaDocument;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class WithTestDae {

    private ColladaLoader colladaLoader = new ColladaLoader();
    private URL resourceUrl;

    public WithTestDae() {
        colladaLoader.setValidating(false);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        resourceUrl = classLoader.getResource("test.dae");
    }

    @Test
    public void loaderHasVersions14() {
        assertThat(colladaLoader.getRegisteredVersions())
                .contains(ColladaLoaderService14.VERSION_1_4_0, ColladaLoaderService14.VERSION_1_4_1);
    }

    @Test(groups = {"functional"})
    public void canLoadTestDae() throws IOException {
        ColladaDocument document = colladaLoader.load(resourceUrl);
        assertThat(document).isNotNull();
    }

}
