package net.dryanhild.jcollada.schema14.geometry.source;

import org.apache.xmlbeans.XmlException;
import org.collada.x2005.x11.colladaSchema.SourceDocument;
import org.collada.x2005.x11.colladaSchema.SourceDocument.Source;

public class SourceStreamTest {

    protected Source getSource(String source) {
        try {
            SourceDocument doc = SourceDocument.Factory.parse(source);
            return doc.getSource();
        } catch (XmlException e) {
            throw new RuntimeException(e);
        }
    }
}
