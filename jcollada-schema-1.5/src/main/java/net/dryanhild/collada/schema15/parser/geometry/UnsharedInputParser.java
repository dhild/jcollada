package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.UnsharedInput;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class UnsharedInputParser implements Function<XmlPullParser, UnsharedInput> {

    private final XmlParser<UnsharedInput> parser = new XmlParser<>("input", UnsharedInput.class);

    public UnsharedInputParser() {
        parser.addAttributeConsumer("semantic", UnsharedInput::setSemantic);
        parser.addURIConsumer("source", UnsharedInput::setSource);
    }

    @Override
    public UnsharedInput apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new UnsharedInput());
    }
}
