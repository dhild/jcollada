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

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.LoaderBase;
import com.sun.j3d.loaders.ParsingErrorException;

public class ColladaLoader extends LoaderBase {

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

    /*
     * {@inheritDoc}
     */
    @Override
    public ColladaScene load(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        return loadImpl(new FileReader(file), new DefaultParsingContext());
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public ColladaScene load(URL url) throws FileNotFoundException {
        try {
            return loadImpl(new InputStreamReader(url.openStream()), new DefaultParsingContext());
        } catch (FileNotFoundException e) {
            // FileNotFound exceptions should be preserved.
            throw e;
        } catch (IOException e) {
            ParsingErrorException exec = new ParsingErrorException("Exception encountered while parsing!");
            exec.addSuppressed(e);
            throw exec;
        }
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public ColladaScene load(Reader reader) {
        return loadImpl(reader, new DefaultParsingContext());
    }

    private ColladaScene loadImpl(Reader reader, DefaultParsingContext context) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        context.setMainFileReader(bufferedReader);
        context.setFlags(getFlags());
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
            ParsingErrorException exec = new ParsingErrorException("Error encountered while closing output stream!");
            exec.addSuppressed(e);
            throw exec;
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
            throw new ParsingErrorException("IOException while reading: " + ex.getLocalizedMessage());
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
