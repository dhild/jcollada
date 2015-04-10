package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.RawSource;
import net.dryanhild.collada.schema15.data.geometry.SourceTechniqueCommon;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class SourceParser implements Function<XmlPullParser, RawSource> {

    private final XmlParser<RawSource> parser = new XmlParser<>("source", RawSource.class);

    public SourceParser() {
        parser.addElementConsumer("asset", RawSource::setAsset, new AssetParser());
        parser.addElementConsumer("float_array", RawSource::setFloats, new FloatArrayParser());
        parser.addElementConsumer("int_array", RawSource::setInts, new IntArrayParser());
        parser.addElementConsumer("technique_common", RawSource::setTechniqueCommon, new TechniqueCommonParser());
    }

    @Override
    public RawSource apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new RawSource());
    }

    private static class TechniqueCommonParser implements Function<XmlPullParser, SourceTechniqueCommon> {

        private final XmlParser<SourceTechniqueCommon> parser =
                new XmlParser<>("technique_common", SourceTechniqueCommon.class);

        public TechniqueCommonParser() {
            parser.addElementConsumer("accessor", SourceTechniqueCommon::setAccessor, new AcessorParser());
        }

        @Override
        public SourceTechniqueCommon apply(XmlPullParser pullParser) {
            return parser.apply(pullParser, new SourceTechniqueCommon());
        }
    }
}
