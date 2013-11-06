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
package net.dryanhild.jcollada.schema141;

import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaContext;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ColladaContext.ContextSchema;

import org.apache.commons.io.input.ReaderInputStream;
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

    public static final Version version140 = new Version(1, 4, 0, "1.4.0");
    public static final Version version141 = new Version(1, 4, 1, "1.4.1");

    private static final URL schemaURL = ClassLoader.getSystemResource("collada_schema_1_4_1.xsd");
    private static Schema schema = null;
    private static JAXBContext context = null;

    @Override
    public List<Version> getColladaVersions() {
        List<Version> versions = new ArrayList<>();
        versions.add(version140);
        versions.add(version141);
        return versions;
    }

    private synchronized JAXBContext loadContext() {
        if (context == null) {
            try {
                context = JAXBContext.newInstance("net.dryanhild.jcollada.schema141.gen");
            } catch (JAXBException ex) {
                logger.error("Unable to create JAXBContext!\n{}", ex.getLocalizedMessage());
                ParsingErrorException exception = new ParsingErrorException("Creation of JAXBContext failed.");
                exception.addSuppressed(ex);
                throw exception;
            }
        }
        return context;
    }

    private synchronized Schema loadSchema() {
        if (schema == null) {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                schema = sf.newSchema(schemaURL);
            } catch (SAXException e) {
                logger.error("Can't load the schema from URL: {}. " + "Parsing will continue without validation!\n{}",
                        schemaURL.toString(), e.getLocalizedMessage());
            }
        }
        return schema;
    }

    @Override
    public Scene load(Reader in) {
        ColladaContext.load(new ReaderInputStream(in), loadContext(), new ContextSchema() {
            @Override
            public Schema getSchema() {
                return loadSchema();
            }
        });

        COLLADAContextHandler handler = new COLLADAContextHandler();

        return handler.process();
    }

    private final Pattern versionPattern = Pattern.compile("COLLADA[^>]+version\\s?=\\s?\\\"1\\.4\\.[01]\\\"");

    @Override
    public boolean canLoad(CharSequence header) {
        Matcher matcher = versionPattern.matcher(header);
        return matcher.find();
    }

}
