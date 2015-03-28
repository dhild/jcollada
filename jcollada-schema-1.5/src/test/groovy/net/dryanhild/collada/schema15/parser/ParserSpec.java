package net.dryanhild.collada.schema15.parser;

import lombok.SneakyThrows;
import net.dryanhild.collada.common.parser.XmlParser;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public interface ParserSpec {

    @SneakyThrows(IOException.class)
    default XmlPullParser createDocumentParser(String data) {
        InputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        return XmlParser.createPullParser(false, input, StandardCharsets.UTF_8);
    }

}
