/**
 * 
 */
package net.dryanhild.jcollada;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.j3d.loaders.ParsingErrorException;

/**
 * @author dhild
 * 
 */
public final class ColladaContext {

    private static final Logger logger = LogManager.getLogger(ColladaContext.class);

    private ColladaContext() {
    }

    private static final ThreadLocal<ContextSchema> schemas = new ThreadLocal<ContextSchema>() {
        @Override
        protected ContextSchema initialValue() {
            return null;
        }
    };
    private static final ThreadLocal<JAXBContext> context = new ThreadLocal<JAXBContext>() {
        @Override
        protected JAXBContext initialValue() {
            return null;
        }
    };
    private static final ThreadLocal<DocumentBuilder> docBuilder = new ThreadLocal<DocumentBuilder>() {
        @Override
        protected DocumentBuilder initialValue() {
            return null;
        }
    };
    private static final ThreadLocal<URL> schemaURL = new ThreadLocal<URL>() {
        @Override
        protected URL initialValue() {
            return null;
        }
    };
    private static final ThreadLocal<Boolean> validating = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return Boolean.TRUE;
        }
    };
    private static final ThreadLocal<Integer> flags = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return Integer.valueOf(0);
        }
    };
    private static final ThreadLocal<Map<String, Document>> documents = new ThreadLocal<Map<String, Document>>() {
        @Override
        protected Map<String, Document> initialValue() {
            return Maps.newHashMap();
        }
    };
    private static final ThreadLocal<Unmarshaller> unmarshaller = new ThreadLocal<Unmarshaller>() {
        @Override
        protected Unmarshaller initialValue() {
            try {
                Unmarshaller unmarshaller = context.get().createUnmarshaller();
                if (validating.get().booleanValue()) {
                    unmarshaller.setSchema(schemas.get().getSchema());
                }
                return unmarshaller;
            } catch (JAXBException e) {
                ParsingErrorException exception = new ParsingErrorException("Creation of JAXB Unmarshaller failed.");
                exception.addSuppressed(e);
                throw exception;
            }
        }
    };

    private static final ThreadLocal<String> basePath = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return ".";
        }
    };

    private static final ThreadLocal<URL> baseURL = new ThreadLocal<URL>() {
        @Override
        protected URL initialValue() {
            return null;
        }
    };

    public static void initialize(URL baseUrl, String basePath, boolean validating, int flags) {
        ColladaContext.basePath.set(basePath);
        ColladaContext.baseURL.set(baseUrl);

        ColladaContext.validating.set(Boolean.valueOf(validating));
        ColladaContext.flags.set(Integer.valueOf(flags));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder.set(factory.newDocumentBuilder());
        } catch (ParserConfigurationException e) {
            ParsingErrorException exception = new ParsingErrorException("Creation of XML Document builder failed.");
            exception.addSuppressed(e);
            throw exception;
        }
    }

    public static void load(InputStream input, JAXBContext context, ContextSchema schema) {
        ColladaContext.context.set(context);
        ColladaContext.schemas.set(schema);
        load(input);
    }

    private static void load(InputStream input) {
        documents.get().clear();
        try {
            load(input, "");
        } catch (Exception ex) {
            ParsingErrorException exception = new ParsingErrorException("Loading failed!");
            exception.addSuppressed(ex);
            throw exception;
        }
    }

    private static void load(InputStream input, String key) throws IOException, SAXException {
        Document document = docBuilder.get().parse(input);
        documents.get().put(key, document);
    }

    public static <T> Collection<T> getElementsByTag(String tag, Class<T> type) {
        logger.entry(tag, type);
        List<T> values = Lists.newArrayList();
        try {
            for (Document doc : documents.get().values()) {
                NodeList nodes = doc.getElementsByTagName(tag);
                for (int i = 0; i < nodes.getLength(); i++) {
                    values.add(unmarshaller.get().unmarshal(nodes.item(i), type).getValue());
                }
            }
        } catch (JAXBException ex) {
            ParsingErrorException exception = new ParsingErrorException("Getting element by ID failed.");
            exception.addSuppressed(ex);
            throw exception;
        }
        return logger.exit(values);
    }

    public static <T> T getElementById(String id, Class<T> type) {
        logger.entry(id, type);
        if (!id.startsWith("#")) {
            throw new ParsingErrorException("Not yet capable of parsing complex id references!");
        }
        Element element = documents.get().get("").getElementById(id.substring(1));
        try {
            return logger.exit(unmarshaller.get().unmarshal(element, type).getValue());
        } catch (JAXBException ex) {
            ParsingErrorException exception = new ParsingErrorException("Getting element by ID failed.");
            exception.addSuppressed(ex);
            throw exception;
        }
    }

    public static interface ContextSchema {
        public Schema getSchema();
    }
}
