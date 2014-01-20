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

import net.dryanhild.jcollada.data.AssetDescription;
import net.dryanhild.jcollada.schema15.generated.AssetType;
import net.dryanhild.jcollada.schema15.generated.COLLADA;

import com.sun.j3d.loaders.IncorrectFormatException;

public class FileMarshaller {

    private COLLADA collada;

    public void loadFrom(Reader reader) {
        Unmarshaller unmarshaller = null;

        try {
            JAXBContext context = JAXBContext.newInstance("net.dryanhild.jcollada.schema15.generated");
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        JAXBElement<COLLADA> element = null;

        try {
            XMLStreamReader xmlReader = XMLInputFactory.newFactory().createXMLStreamReader(reader);
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

    public AssetDescription getMainAsset() {
        AssetHandler handler = new AssetHandler();

        AssetType type = collada.getAsset();

        return handler.loadAssetDescription(type);
    }
}
