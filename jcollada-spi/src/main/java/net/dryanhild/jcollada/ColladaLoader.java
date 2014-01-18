package net.dryanhild.jcollada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.LoaderBase;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

@Service
public class ColladaLoader extends LoaderBase {

    private static final int HEADER_LENGTH = 1024;

    private final Logger logger = LogManager.getLogger(ColladaLoader.class);

    @Autowired
    private List<ColladaLoaderService> loaders;

    private boolean validating = true;

    public ColladaLoader() {
        for (ColladaLoaderService service : loaders) {
            for (Version version : service.getColladaVersions()) {
                logger.debug("Service implementation for {} is available at {}", version, service.getClass().getName());
            }
        }
    }

    public List<ColladaLoaderService> getRegisteredLoaders() {
        return loaders;
    }

    public void registerLoader(ColladaLoaderService loader) {
        loaders.add(loader);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Scene load(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        return loadImpl(new FileReader(file), new DefaultParsingContext());
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Scene load(URL url) throws FileNotFoundException {
        try {
            return loadImpl(new InputStreamReader(url.openStream()), new DefaultParsingContext());
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
    public Scene load(Reader reader) {
        return loadImpl(reader, new DefaultParsingContext());
    }

    private Scene loadImpl(Reader reader, DefaultParsingContext context) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        context.setMainFileReader(bufferedReader);
        context.setFlags(getFlags());
        context.setValidating(isValidating());

        String header = readHeader(bufferedReader);

        for (ColladaLoaderService loader : loaders) {
            if (loader.canLoad(header)) {
                logger.debug("Attempting load with class {}", loader.getClass().getName());
                return loader.load(context);
            }
        }

        logger.debug("Registered Collada file loaders:");
        for (ColladaLoaderService loader : loaders) {
            logger.debug(loader.getClass().getName());
        }
        throw new IncorrectFormatException("Format of input data not recognized by any loaders on the classpath.");
    }

    private String readHeader(BufferedReader bufferedReader) {
        char[] header = new char[HEADER_LENGTH];
        try {
            bufferedReader.mark(HEADER_LENGTH);
            bufferedReader.read(header);
            bufferedReader.reset();
        } catch (IOException ex) {
            throw new ParsingErrorException("IOException while reading: " + ex.getLocalizedMessage());
        }
        return new String(header);
    }

    public boolean isValidating() {
        return validating;
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

}
