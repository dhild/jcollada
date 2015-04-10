package net.dryanhild.collada.schema15.parser;

import org.codehaus.stax2.XMLInputFactory2;
import org.collada.schema15.COLLADA;
import org.collada.schema15.ObjectFactory;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

@Service
public class ColladaFragmentXmlParser implements ColladaFragmentParser {

    private static final Logger logger = LoggerFactory.getLogger(ColladaFragmentXmlParser.class);

    public static final String SCHEMA_EXTERNAL_LOCATION = "https://www.khronos.org/files/collada_schema_1_5";
    private static XMLInputFactory inputFactory;
    private static JAXBContext jaxbContext;
    private static Schema schema;

    private synchronized static XMLStreamReader createXMLStreamReader(InputStream inputStream)
            throws XMLStreamException {
        if (inputFactory == null) {
            inputFactory = XMLInputFactory2.newInstance();
            inputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
        }
        return inputFactory.createXMLStreamReader(inputStream);
    }

    private synchronized static JAXBContext getContext() throws JAXBException {
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        }
        return jaxbContext;
    }

    private synchronized static Schema getSchema() {
        if (schema == null) {
            try {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                schema = factory.newSchema(new URL(SCHEMA_EXTERNAL_LOCATION));
            } catch (Exception e) {
                logger.warn("Unable to create schema for validating!");
            }
        }
        return schema;
    }

    @Override
    public COLLADA parse(boolean validate, InputStream inputStream) {
        try {
            JAXBContext jaxbContext = getContext();
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            if (validate) {
                unmarshaller.setSchema(getSchema());
            }

            XMLStreamReader reader = createXMLStreamReader(inputStream);
            JAXBElement<COLLADA> element = unmarshaller.unmarshal(reader, COLLADA.class);
            return element.getValue();
        } catch (XMLStreamException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
