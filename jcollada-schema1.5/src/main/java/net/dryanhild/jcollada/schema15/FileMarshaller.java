package net.dryanhild.jcollada.schema15;

import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.dryanhild.jcollada.schema15.generated.AssetType;
import net.dryanhild.jcollada.schema15.generated.COLLADA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.j3d.loaders.IncorrectFormatException;

public class FileMarshaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMarshaller.class);

    private COLLADA collada;

    public void loadFrom(Reader reader, boolean validating) {
        Unmarshaller unmarshaller = null;

        try {
            JAXBContext context = JAXBContext.newInstance("net.dryanhild.jcollada.schema15.generated");
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        JAXBElement<COLLADA> element = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            setValidating(validating, factory);
            XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);
            element = unmarshaller.unmarshal(xmlReader, COLLADA.class);
        } catch (JAXBException | XMLStreamException e) {
            IncorrectFormatException exec =
                    new IncorrectFormatException("Unable to properly load elements from the file.");
            exec.addSuppressed(e);
            throw exec;
        } catch (FactoryConfigurationError e) {
            throw new RuntimeException(e);
        }

        collada = element.getValue();
    }

    private void setValidating(boolean validating, XMLInputFactory factory) {
        if (validating) {
            if (factory.isPropertySupported(XMLInputFactory.IS_VALIDATING)) {
                try {
                    factory.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.TRUE);
                    return;
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Exception while trying to set the XMLInputFactory to validate.", e);
                }
            }
            LOGGER.error("Validation is not supported by any of the XML parsers on the classpath.");
            LOGGER.error("Parsing will continue without validation.");
        }
    }

    public AssetType getMainAsset() {
        return collada.getAsset();
    }
}
