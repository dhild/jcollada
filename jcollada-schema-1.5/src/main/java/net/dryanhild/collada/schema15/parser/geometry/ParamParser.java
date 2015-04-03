package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.Param;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class ParamParser implements Function<XmlPullParser, Param> {

    private final XmlParser<Param> parser = new XmlParser<>("param", Param.class);

    public ParamParser() {
        parser.addAttributeConsumer("name", Param::setName);
        parser.addAttributeConsumer("sid", Param::setSid);
        parser.addAttributeConsumer("type", Param::setType);
        parser.addAttributeConsumer("semantic", Param::setSemantic);
    }

    @Override
    public Param apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new Param());
    }
}
