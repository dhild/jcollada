/**
 * 
 */
package net.dryanhild.jcollada.schema141;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;
import com.sun.j3d.loaders.ParsingErrorException;

/**
 * @author dhild
 * 
 */
public final class ColladaContext {

    final Logger logger = LogManager.getLogger(ColladaContext.class);

    private static final URL schemaURL = ClassLoader.getSystemResource("collada_schema_1_4_1.xsd");
    private static Schema schema = null;
    private static JAXBContext context = null;

    private final Map<String, Document> documents = Maps.newHashMap();

    private final DocumentBuilder docBuilder;

    private final String basePath;
    private final URL baseUrl;

    public ColladaContext(URL baseUrl, String basePath) {
        this.basePath = basePath;
        this.baseUrl = baseUrl;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void loadContext() {
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

    public void load(InputStream input) {
        try {
            load(input, "");
        } catch (Exception e) {
            throw new ParsingErrorException("Exception while loading: " + e.getLocalizedMessage());
        }
    }

    private void load(InputStream input, String path) throws IOException, SAXException {
        Document document = docBuilder.parse(input);
        documents.put(path, document);
    }

    public <T> T getNode(Class<T> type) {
        return null;
    }

}
