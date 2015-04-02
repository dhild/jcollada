package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.SharedInput;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class SharedInputParser implements Function<XmlPullParser, SharedInput> {

    private final XmlParser<SharedInput> parser = new XmlParser<>("input", SharedInput.class);

    public SharedInputParser() {
        parser.addAttributeConsumer("semantic", SharedInput::setSemantic);
        parser.addURIConsumer("source", SharedInput::setSource);
        parser.addIntConsumer("offset", SharedInput::setOffset);
        parser.addIntConsumer("set", SharedInput::setSet);
    }

    @Override
    public SharedInput apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new SharedInput());
    }
}
