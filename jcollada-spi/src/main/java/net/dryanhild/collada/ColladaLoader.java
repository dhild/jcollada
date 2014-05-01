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

import com.google.common.collect.Lists;
import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.spi.ColladaLoaderService;
import net.dryanhild.collada.spi.ParsingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

public class ColladaLoader {

    private static final int HEADER_LENGTH = 1024;

    private final Logger logger = LoggerFactory.getLogger(ColladaLoader.class);

    private final Collection<ColladaLoaderService> loaders;

    private boolean validating = true;

    private Charset charset = StandardCharsets.UTF_8;

    public ColladaLoader() {
        loaders = Lists.newArrayList();
        loaders.clear();
        ServiceLoader<ColladaLoaderService> serviceLoader = ServiceLoader.load(ColladaLoaderService.class);
        for (ColladaLoaderService service : serviceLoader) {
            loaders.add(service);

            for (VersionSupport version : service.getColladaVersions()) {
                logger.debug("Service implementation for {} is available from class {}", version, service.getClass()
                        .getName());
            }
        }
    }

    public Collection<VersionSupport> getRegisteredVersions() {
        ArrayList<VersionSupport> versions = Lists.newArrayList();

        for (ColladaLoaderService service : loaders) {
            versions.addAll(service.getColladaVersions());
        }

        return versions;
    }

    public ColladaDocument load(URL url) throws IOException {
        return load(url.openStream());
    }

    public ColladaDocument load(InputStream input) throws IOException {
        return loadImpl(new DefaultParsingContext(validating, input, charset));
    }

    private ColladaDocument loadImpl(ParsingContext context) throws IOException {
        for (ColladaLoaderService loader : loaders) {
            if (loader.canLoad(context.getMainFileHeader())) {
                return loadWithService(context, loader);
            }
        }

        logger.debug("Registered Collada file loaders:");
        for (ColladaLoaderService loader : loaders) {
            logger.debug(loader.getClass().getName());
        }
        throw new IncorrectFormatException("Format of input data not recognized by any loaders on the classpath.");
    }

    private ColladaDocument loadWithService(ParsingContext context, ColladaLoaderService loader) throws IOException {
        logger.debug("Attempting load with class {}", loader.getClass().getName());

        ColladaDocument scene = loader.load(context);

        context.getMainFileInputStream().close();

        return scene;
    }

    public boolean isValidating() {
        return validating;
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
