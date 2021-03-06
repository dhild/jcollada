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

import net.dryanhild.collada.spi.ParsingContext;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DefaultParsingContext implements ParsingContext {

    public static final int BUFFER_SIZE = 4096;
    public static final String BUFFER_SIZE_PROPERTY = "jcollada.parsing.buffersize";

    private final boolean validating;
    private final BufferedInputStream inputStream;
    private final Charset charset;
    private final String header;

    public DefaultParsingContext(boolean validating, InputStream input, Charset charset) throws IOException {
        this.validating = validating;
        this.charset = charset;

        String bufferSizeStr = System.getProperty(BUFFER_SIZE_PROPERTY, Integer.toString(BUFFER_SIZE));
        int bufferSize = Integer.parseInt(bufferSizeStr);

        inputStream = new BufferedInputStream(input, bufferSize);

        inputStream.mark(bufferSize);
        byte[] bytes = new byte[bufferSize];
        int offset = 0;
        while (offset < bufferSize) {
            int read = inputStream.read(bytes, offset, bufferSize - offset);
            offset += read;
            if (read == -1) {
                break;
            }
        }
        header = new String(bytes, StandardCharsets.UTF_8);
        inputStream.reset();
    }

    @Override
    public boolean isValidating() {
        return validating;
    }

    @Override
    public CharSequence getMainFileHeader() {
        return header;
    }

    @Override
    public InputStream getMainFileInputStream() {
        return inputStream;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }
}
