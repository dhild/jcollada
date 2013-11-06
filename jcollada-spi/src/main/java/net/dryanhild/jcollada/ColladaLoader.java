/*
 * Copyright (c) 2013, D. Ryan Hild <d.ryan.hild@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.dryanhild.jcollada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ServiceLoader;

import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaContext;
import net.dryanhild.jcollada.spi.ColladaLoaderService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.LoaderBase;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class ColladaLoader extends LoaderBase {

    final Logger logger = LogManager.getLogger(ColladaLoader.class);

    private static ServiceLoader<ColladaLoaderService> loaders = ServiceLoader.load(ColladaLoaderService.class);

    public boolean validating = true;

    public static int headerLength = 1024;
    private static final int minBufferSize = 512;

    public ColladaLoader() {
        logger.trace("Creating new ColladaLoader instance");
        for (ColladaLoaderService service : loaders) {
            for (Version version : service.getColladaVersions()) {
                logger.debug("Service implementation for {} is available at {}", version, service.getClass().getName());
            }
        }
    }

    @Override
    public Scene load(String fileName) throws FileNotFoundException, IncorrectFormatException, ParsingErrorException {
        File file = new File(fileName);
        try {
            return loadImpl(new FileReader(file), file.toURI().toURL());
        } catch (MalformedURLException e) {
            ParsingErrorException exec = new ParsingErrorException();
            exec.addSuppressed(e);
            throw exec;
        }
    }

    @Override
    public Scene load(URL url) throws FileNotFoundException, IncorrectFormatException, ParsingErrorException {
        try {
            return loadImpl(new InputStreamReader(url.openStream()), url);
        } catch (IOException e) {
            throw new ParsingErrorException("IOException encountered while parsing: " + e.getLocalizedMessage());
        }
    }

    @Override
    public Scene load(Reader reader) throws IncorrectFormatException, ParsingErrorException {
        return loadImpl(reader, null);
    }

    private Scene loadImpl(Reader reader, URL mainFile) throws IncorrectFormatException {
        char[] header = new char[headerLength];
        int bufferSize = Math.max(headerLength, minBufferSize);
        BufferedReader bufferedReader = new BufferedReader(reader, bufferSize);
        try {
            bufferedReader.mark(headerLength);
            bufferedReader.read(header);
            bufferedReader.reset();
        } catch (IOException ex) {
            throw new ParsingErrorException("IOException while reading: " + ex.getLocalizedMessage());
        }
        String headerString = new String(header);
        try {
            for (ColladaLoaderService loader : loaders) {
                if (loader.canLoad(headerString)) {
                    logger.debug("Attempting load with class {}", loader.getClass().getName());
                    ColladaContext.initialize(getBaseUrl(), getBasePath(), isValidating(), getFlags());
                    Scene s = loader.load(bufferedReader);
                    if (s != null) {
                        return s;
                    }
                }
            }
        } catch (Exception e) {
            ParsingErrorException ex = new ParsingErrorException("Exception while parsing!");
            ex.addSuppressed(e);
            throw ex;
        }
        logger.debug("Registered Collada file loaders:");
        for (ColladaLoaderService loader : loaders) {
            logger.debug(loader.getClass().getName());
        }
        throw new IncorrectFormatException("Format of input data not recognized by any loaders on the classpath.");
    }

    public boolean isValidating() {
        return validating;
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

}
