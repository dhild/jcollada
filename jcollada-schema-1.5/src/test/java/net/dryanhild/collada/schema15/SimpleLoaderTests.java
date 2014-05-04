/*
 * The MIT License
 *
 * Copyright 2014 D. Ryan Hild <d.ryan.hild@gmail.com>.
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

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.hk2spi.ColladaLoader;
import net.dryanhild.collada.hk2spi.ParsingContext;
import org.apache.xmlbeans.impl.common.ReaderInputStream;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@Test
public class SimpleLoaderTests {

    private static final String BARE_COLLADA_FILE = "<?xml version=\"1.0\"?>\n"
                                                    +
                                                    "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" version=\"1.5.0\">\n" +
                                                    " <asset>\n"
                                                    + "   <created>2007-12-11T14:24:00Z</created>\n" +
                                                    "   <modified>2007-12-11T14:24:00Z</modified>\n"
                                                    + " </asset>\n" + "</COLLADA>";

    private ColladaLoader loader;

    @BeforeMethod
    public void resetLoader() {
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        loader = locator.getService(ColladaLoaderSchema15.class);
    }

    public void versionHas150SupportOnly() {
        Iterable<VersionSupport> support = loader.getColladaVersions();

        assertThat(support).containsOnly(new VersionSupport(1, 5, 0));
    }

    private ParsingContext createContext() {
        return new ParsingContext() {
            @Override
            public boolean isValidating() {
                return false;
            }

            @Override
            public byte[] getMainFileHeader() {
                return BARE_COLLADA_FILE.getBytes();
            }

            @Override
            public InputStream getMainFileInputStream() {
                try {
                    return new ReaderInputStream(new StringReader(BARE_COLLADA_FILE), "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public URL getMainFileURL() {
                try {
                    return new URL("http://localhost/dummy/url");
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public void canLoadBareFile() {
        ParsingContext context = createContext();
        assertThat(loader.canLoad(context));
    }

    public void bareFileHasBareResults() {
        ParsingContext context = createContext();

        ColladaDocument document = loader.load(context);
        assertThat(document.getGeometries()).isEmpty();
        assertThat(document.getNodes()).isEmpty();
        assertThat(document.getVisualScenes()).isEmpty();
    }

}
