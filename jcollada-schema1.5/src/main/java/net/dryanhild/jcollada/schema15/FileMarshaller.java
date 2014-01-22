package net.dryanhild.jcollada.schema15;

import java.io.IOException;
import java.io.Reader;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.collada.x2008.x03.colladaSchema.AssetType;
import org.collada.x2008.x03.colladaSchema.COLLADADocument;
import org.collada.x2008.x03.colladaSchema.COLLADADocument.COLLADA;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;

public class FileMarshaller {

    private COLLADA collada;

    public void loadFrom(Reader reader, boolean validating) {
        XmlOptions options = new XmlOptions();
        if (validating) {
            options.setValidateOnSet();
        }
        try {
            COLLADADocument document = COLLADADocument.Factory.parse(reader, options);
            collada = document.getCOLLADA();
        } catch (XmlException e) {
            IncorrectFormatException exec =
                    new IncorrectFormatException("Unable to properly load elements from the file.");
            exec.addSuppressed(e);
            throw exec;
        } catch (IOException e) {
            ParsingErrorException exec = new ParsingErrorException("Exception while loading COLLADA document.");
            exec.addSuppressed(e);
            throw exec;
        }
    }

    public AssetType getMainAsset() {
        return collada.getAsset();
    }
}
