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

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.spi.ParsingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DefaultParsingContext implements ParsingContext {

    private static final Logger logger = LoggerFactory.getLogger(DefaultParsingContext.class);

    public static final int BUFFER_SIZE = 4096;
    public static final String HEADER_SEARCH_LENGTH = "jcollada.parsing.header-search-length";

    private final boolean validating;
    private final Charset charset;
    private final BufferedInputStream mainFileInputStream;
    private final URI sourceUri;
    private final Map<Object, Object> variables = new HashMap<>();

    public DefaultParsingContext(boolean validating, Charset charset, URI uri, InputStream input) throws IOException {
        this.validating = validating;
        this.charset = charset;
        sourceUri = uri;

        mainFileInputStream = new BufferedInputStream(input, BUFFER_SIZE);
    }

    @Override
    public CharSequence getMainFileHeader() throws IOException {
        String searchStr = System.getProperty(HEADER_SEARCH_LENGTH, Integer.toString(BUFFER_SIZE));
        int searchSize = Integer.parseInt(searchStr);

        mainFileInputStream.mark(searchSize);
        try {
            byte[] bytes = new byte[searchSize];
            int offset = 0;
            while (offset < searchSize) {
                int read = mainFileInputStream.read(bytes, offset, searchSize - offset);
                offset += read;
                if (read == -1) {
                    break;
                }
            }
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.debug("IOException encountered, will try to reset stream", e);
            throw e;
        } finally {
            mainFileInputStream.reset();
        }
    }

    @Override
    public void store(Object key, Object value) {
        variables.put(key, value);
    }

    @Override
    public <T> T retrieve(Object key, Class<T> type) {
        Object result = variables.get(key);
        return type.cast(result);
    }
}
