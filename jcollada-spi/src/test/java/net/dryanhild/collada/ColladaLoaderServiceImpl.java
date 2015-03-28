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

import com.google.common.collect.ImmutableList;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collection;

public class ColladaLoaderServiceImpl implements ColladaLoaderService {

    public static final VersionSupport TEST_VERSION = new VersionSupport(0, 0, 0);

    public static final String TEST_BASIC_FILE = "This is a test file for 0.0.0.";

    public ParsingContext lastContext;

    @Override
    public Collection<VersionSupport> getColladaVersions() {
        return ImmutableList.of(TEST_VERSION);
    }

    @Override
    public boolean canLoad(ParsingContext context) throws IOException {
        return context.getMainFileHeader().toString().contains(TEST_BASIC_FILE);
    }

    @Override
    public ColladaDocument load(ParsingContext context) {
        lastContext = context;
        return Mockito.mock(ColladaDocument.class);
    }

}
