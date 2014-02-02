package net.dryanhild.jcollada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

import net.dryanhild.jcollada.data.ColladaScene;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColladaLoader {

    private static final int HEADER_LENGTH = 1024;

    private final Logger logger = LoggerFactory.getLogger(ColladaLoader.class);

    private final Collection<ColladaLoaderService> loaders;

    private boolean validating = true;

    /**
     * Creates a new ColladaLoader, using the current thread's context class
     * loader to locate service implementations.
     */
    public ColladaLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ColladaLoader(ClassLoader classLoader) {
        loaders = new ArrayList<>();
        reloadServices(classLoader);
    }

    public void reloadServices(ClassLoader classLoader) {
        loaders.clear();
        ServiceLoader<ColladaLoaderService> serviceLoader = ServiceLoader.load(ColladaLoaderService.class, classLoader);
        Iterator<ColladaLoaderService> serviceImplementations = serviceLoader.iterator();
        while (serviceImplementations.hasNext()) {
            ColladaLoaderService service = serviceImplementations.next();
            loaders.add(service);

            for (VersionSupport version : service.getColladaVersions()) {
                logger.debug("Service implementation for {} is available from class {}", version, service.getClass()
                        .getName());
            }
        }
    }

    public Collection<VersionSupport> getRegisteredVersions() {
        ArrayList<VersionSupport> versions = new ArrayList<>();

        for (ColladaLoaderService service : loaders) {
            versions.addAll(service.getColladaVersions());
        }

        return versions;
    }

    public ColladaScene load(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        try (FileReader reader = new FileReader(file)) {
            return loadImpl(reader, new DefaultParsingContext());
        } catch (FileNotFoundException e) {
            // FileNotFound exceptions should be preserved.
            throw e;
        } catch (IOException e) {
            throw new ParsingException("IOException encountered while parsing!", e);
        }
    }

    public ColladaScene load(URL url) throws FileNotFoundException {
        try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
            return loadImpl(reader, new DefaultParsingContext());
        } catch (FileNotFoundException e) {
            // FileNotFound exceptions should be preserved.
            throw e;
        } catch (IOException e) {
            throw new ParsingException("Exception encountered while parsing!", e);
        }
    }

    public ColladaScene load(Reader reader) {
        return loadImpl(reader, new DefaultParsingContext());
    }

    @SuppressWarnings("resource")
    private ColladaScene loadImpl(Reader reader, DefaultParsingContext context) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        context.setMainFileReader(bufferedReader);
        context.setValidating(isValidating());

        String header = readHeader(bufferedReader);

        for (ColladaLoaderService loader : loaders) {
            if (loader.canLoad(header)) {
                return loadWithService(reader, context, loader);
            }
        }

        logger.debug("Registered Collada file loaders:");
        for (ColladaLoaderService loader : loaders) {
            logger.debug(loader.getClass().getName());
        }
        throw new IncorrectFormatException("Format of input data not recognized by any loaders on the classpath.");
    }

    private ColladaScene loadWithService(Reader reader, DefaultParsingContext context, ColladaLoaderService loader) {
        logger.debug("Attempting load with class {}", loader.getClass().getName());

        ColladaScene scene = loader.load(context);

        try {
            reader.close();
        } catch (IOException e) {
            throw new ParsingException("Error encountered while closing output stream!", e);
        }

        return scene;
    }

    private String readHeader(BufferedReader bufferedReader) {
        char[] header = new char[HEADER_LENGTH];
        int read = 0;
        try {
            bufferedReader.mark(HEADER_LENGTH);
            read = bufferedReader.read(header);
            bufferedReader.reset();
        } catch (IOException ex) {
            throw new ParsingException("IOException while reading: ", ex);
        }
        return new String(header, 0, read);
    }

    public boolean isValidating() {
        return validating;
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

}
