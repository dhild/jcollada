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
package net.dryanhild.jcollada.schema15;

import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.dryanhild.jcollada.LoaderContext;
import net.dryanhild.jcollada.schema15.gen.COLLADA;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ColladaVersion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

/**
 * 
 * @author D. Ryan Hild <d.ryan.hild@gmail.com>
 */
public class ColladaLoader extends ColladaLoaderService {

    final Logger logger = LogManager.getLogger(ColladaLoader.class);

    public static final ColladaVersion version150 = new ColladaVersion(1, 5, 0, "1.5.0");

    private static final URL schemaURL = ClassLoader.getSystemResource("resources/collada_schema_1_5.xsd");
    private static Schema schema = null;
    private static JAXBContext context = null;

    @Override
    public List<ColladaVersion> getColladaVersions() {
        List<ColladaVersion> versions = new ArrayList<>();
        versions.add(version150);
        return versions;
    }

    private synchronized void loadContext() {
        if (context == null) {
            try {
                context = JAXBContext.newInstance("net.dryanhild.collada.schema15.gen");
            } catch (JAXBException ex) {
                logger.error("Unable to create JAXBContext!\n{}", ex.getLocalizedMessage());
                ParsingErrorException exception = new ParsingErrorException("Creation of JAXBContext failed.");
                exception.addSuppressed(ex);
                throw exception;
            }
        }
    }

    private synchronized void loadSchema() {
        if (schema == null) {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                schema = sf.newSchema(schemaURL);
            } catch (SAXException e) {
                logger.error("Can't load the schema from URL: {}. " + "Parsing will continue without validation!\n{}",
                        schemaURL.toString(), e.getLocalizedMessage());
            }
        }
    }

    private COLLADA loadElements(Reader in, LoaderContext loaderContext) {
        loadContext();
        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            if (loaderContext.isValidating()) {
                loadSchema();
                unmarshaller.setSchema(schema);
            }
            return (COLLADA) unmarshaller.unmarshal(in);
        } catch (Exception e) {
            logger.error("Encountered exception while unmarshalling.", e);
            ParsingErrorException exception = new ParsingErrorException("Encountered exception while unmarshalling.");
            exception.addSuppressed(e);
            throw exception;
        }
    }

    @Override
    public Scene load(Reader in, LoaderContext loaderContext) {
        ColladaScene scene = new ColladaScene(loaderContext);
        COLLADA mainFile = loadElements(in, loaderContext);
        scene.load(mainFile);
        while (loaderContext.hasUnloadedURLs()) {
            scene.load(loadElements(in, loaderContext));
        }

        return scene.constructScene();
    }

    private final Pattern versionPattern = Pattern.compile("COLLADA[^>]+version\\s?=\\s?\\\"1.5.0\\\"", Pattern.DOTALL);

    @Override
    public boolean canLoad(CharSequence header) {
        Matcher matcher = versionPattern.matcher(header);
        return matcher.find();
    }

}
