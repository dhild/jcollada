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
