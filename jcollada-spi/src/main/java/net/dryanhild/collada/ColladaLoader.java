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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

/**
 * Main API class for loading files. Loading is thread-safe, although the validation and charset settings are not. In
 * other words, it is perfectly valid to be loading several files concurrently where the loaders are expected to have
 * the same settings.
 * <p>
 * Validation and charset settings may be used differently by the different implementations. You should make sure
 * that these are set to sane values, or that you are comfortable with the defaults.
 *
 * @see {@link #isValidating()}
 */
public class ColladaLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ColladaLoader.class);

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
                LOG.debug("Service implementation for {} is available from class {}", version, service.getClass()
                        .getName());
            }
        }
    }

    /**
     * Returns all versions that are able to be loaded. Since the standard {@link java.util.ServiceLoader} API is
     * used to locate loading implementations, the return values for this method should not be expected to change
     * over application lifecycle.
     *
     * @return All of the known versions that are supported by the files on the classpath.
     */
    public Collection<VersionSupport> getRegisteredVersions() {
        ArrayList<VersionSupport> versions = Lists.newArrayList();

        for (ColladaLoaderService service : loaders) {
            versions.addAll(service.getColladaVersions());
        }

        return versions;
    }

    /**
     * Loads the given URL into a collada document, using the current settings for validation and charset.
     * <p>
     * Version detection is currently done by reading in the first few kilobytes of data and converting it into a
     * String for regex detection. This is subject to change in future versions, but for now, the <code>charset</code>
     * setting is used to determine the encoding to use.
     * <p>
     * Note that if the underlying XML reader implementation does not support validation, it may throw an exception if
     * you have <code>validating</code> set to true.
     *
     * @param url
     *         The {@link java.net.URL} to read from.
     *
     * @return The loaded file.
     *
     * @throws IOException
     *         If an I/O Exception occurs.
     * @throws net.dryanhild.collada.IncorrectFormatException
     *         If there is a problem detected with the format.
     * @see {@link #setValidating(boolean)}
     */
    public ColladaDocument load(URL url) throws IOException {
        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            LOG.debug("Unable to convert directly to a URI, using just the path instead", e);
            uri = URI.create(url.getPath());
        }
        return load(uri);
    }

    /**
     * Loads the given input stream into a collada document, using the current settings for validation and charset.
     *
     * @return The loaded file.
     *
     * @throws IOException
     *         If an I/O Exception occurs.
     * @throws net.dryanhild.collada.IncorrectFormatException
     *         If there is a problem detected with the format.
     * @see {@link #load(java.net.URL)}
     * @see {@link #setValidating(boolean)}
     */
    public ColladaDocument load(URI uri) throws IOException {
        return loadImpl(new DefaultParsingContext(validating, uri));
    }

    private ColladaDocument loadImpl(ParsingContext context) throws IOException {
        for (ColladaLoaderService loader : loaders) {
            if (loader.canLoad(context)) {
                return loadWithService(context, loader);
            }
        }

        LOG.debug("Registered Collada file loaders:");
        for (ColladaLoaderService loader : loaders) {
            LOG.debug(loader.getClass().getName());
        }
        throw new IncorrectFormatException("Format of input data not recognized by any loaders on the classpath.");
    }

    private ColladaDocument loadWithService(ParsingContext context, ColladaLoaderService loader) throws IOException {
        LOG.debug("Attempting load with class {}", loader.getClass().getName());

        ColladaDocument scene = loader.load(context);

        context.getMainFileInputStream().close();

        return scene;
    }

    /**
     * Returns the validation flag for this loader.
     * <p>
     * Note that this flag will not be respected by all underlying implementations, depending upon the XML parser used.
     * <p>
     * By default, this flag is set to <code>true</code>.
     *
     * @return Whether calls to {@link #load(java.net.URL)} will attempt to use a validating parser.
     */
    public boolean isValidating() {
        return validating;
    }

    /**
     * Sets the validation flag.
     *
     * @param validating
     *         Whether calls to {@link #load(java.net.URL)} should attempt to use a validating parser.
     *
     * @see {@link #isValidating()}
     */
    public void setValidating(boolean validating) {
        this.validating = validating;
    }
}
