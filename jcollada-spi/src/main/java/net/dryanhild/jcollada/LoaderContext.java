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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.j3d.loaders.ParsingErrorException;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class LoaderContext {

    final Logger logger = LogManager.getLogger(ColladaLoader.class);

    protected final int flags;
    protected final boolean validate;

    protected final URL mainFile;
    protected final URL baseURL;
    protected final String basePath;
    protected String currentFile;

    protected Queue<String> othersToBeLoaded = new LinkedList<>();
    protected Map<String, Object> objectsById = new HashMap<>();

    public LoaderContext(URL file, int flags, boolean validate, URL baseURL, String basePath) {
        this.flags = flags;
        this.validate = validate;
        this.mainFile = file;
        this.baseURL = baseURL;
        if (basePath == null) {
            basePath = "";
        }
        if (basePath.endsWith(File.pathSeparator)) {
            this.basePath = basePath;
        } else {
            this.basePath = basePath + File.pathSeparator;
        }

        if (currentFile == null) {
            currentFile = "";
        } else {
            currentFile = file.toExternalForm();
        }
    }

    public boolean hasUnloadedURLs() {
        return othersToBeLoaded.size() > 0;
    }

    public void addOtherFile(String path) {
        othersToBeLoaded.add(path);
    }

    public Reader openNextFile() throws FileNotFoundException {
        final String name = othersToBeLoaded.remove();
        currentFile = name;
        try {
            URL url = new URL(baseURL, name);
            return new InputStreamReader(url.openStream());
        } catch (Exception e) {
        }

        try {
            File file = new File(basePath + name);
            return new FileReader(file);
        } catch (Exception e) {
        }

        try {
            File file = new File(name);
            return new FileReader(file);
        } catch (Exception e) {
        }

        logger.debug("Unable to open path {}", name);
        logger.debug("Base URL {}", baseURL);
        logger.debug("Base path {}", basePath);
        throw new FileNotFoundException("Unable to open path " + name);
    }

    public boolean isValidating() {
        return validate;
    }

    public boolean hasFlag(int flag) {
        return (flags | flag) != 0;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public <T> T getObjectById(String id, Class<T> type) {
        if (id.charAt(0) != '#') {
            id = "#" + id;
        }
        Object object = objectsById.get(id);

        if (object == null) {
            try {
                object = objectsById.get(new URL(mainFile, id.substring(1)).toExternalForm());
            } catch (MalformedURLException e) {
            }
        }

        if (object == null) {
            logger.debug("Couldn't locate source {}", id);
            throw new ParsingErrorException("Couldn't find the right source element.");
        }

        return type.cast(object);
    }

    public void addObjectById(String id, Object obj) {
        objectsById.put(currentFile + "#" + id, obj);
    }

}
