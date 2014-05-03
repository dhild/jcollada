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

package net.dryanhild.collada;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ColladaLoaderTests {

    @Test
    public void serviceProviderImplementationFound() {
        ColladaLoader loader = new ColladaLoader();

        Collection<VersionSupport> versions = loader.getRegisteredVersions();

        assert versions.contains(ColladaLoaderServiceImpl.TEST_VERSION);
    }

    @DataProvider
    public Object[][] flagConfigurations() {
        return new Object[][]{ //
                //
                {Boolean.FALSE}, //
                {Boolean.TRUE}, //
        };
    }

    @Test(dataProvider = "flagConfigurations")
    public void loadReader(boolean validate) throws IOException {
        ColladaLoader loader = createLoader(validate);

        InputStream reader = new ByteArrayInputStream(ColladaLoaderServiceImpl.TEST_BASIC_FILE.getBytes());
        loader.load(reader);

        checkLoadContext(validate);
    }

    @Test(expectedExceptions = IncorrectFormatException.class)
    public void loadNonImplementedLoader() throws IOException {
        ColladaLoader loader = new ColladaLoader();

        InputStream reader = new ByteArrayInputStream("Bad input.".getBytes());
        loader.load(reader);
    }

    @Test(expectedExceptions = IOException.class)
    public void ioExceptionInHeaderRead() throws IOException {
        InputStream reader = new ErrorOnReadReader();
        ColladaLoader loader = new ColladaLoader();
        loader.load(reader);
    }

    @Test(expectedExceptions = IOException.class)
    public void ioExceptionInClose() throws IOException {
        InputStream reader = new ErrorOnCloseReader();
        ColladaLoader loader = new ColladaLoader();
        loader.load(reader);
    }

    private class ErrorOnReadReader extends InputStream {
        @Override
        public int read() throws IOException {
            throw new IOException();
        }
    }

    private class ErrorOnCloseReader extends InputStream {
        private final InputStream reader =
                new ByteArrayInputStream(ColladaLoaderServiceImpl.TEST_BASIC_FILE.getBytes());

        @Override
        public void close() throws IOException {
            throw new IOException();
        }

        @Override
        public int read() throws IOException {
            return reader.read();
        }

    }

    private ColladaLoader createLoader(boolean validate) {
        ColladaLoader loader = new ColladaLoader();
        loader.setValidating(validate);
        return loader;
    }

    private void checkLoadContext(boolean validate) {
        assert ColladaLoaderServiceImpl.lastContext.isValidating() == validate;
        assert ColladaLoaderServiceImpl.lastContext.getMainFileInputStream() != null;
    }
}
