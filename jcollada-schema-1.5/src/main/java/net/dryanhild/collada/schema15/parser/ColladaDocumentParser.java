package net.dryanhild.collada.schema15.parser;

import net.dryanhild.collada.schema15.data.Collada15Document;
import org.xmlpull.v1.XmlPullParser;

public class ColladaDocumentParser {

    public Collada15Document parse(XmlPullParser parser) {
        return new Collada15Document();
    }

}
