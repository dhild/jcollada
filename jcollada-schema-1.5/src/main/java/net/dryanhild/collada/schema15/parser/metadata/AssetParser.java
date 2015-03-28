package net.dryanhild.collada.schema15.parser.metadata;

import net.dryanhild.collada.common.metadata.MutableAsset;
import net.dryanhild.collada.common.parser.SimpleTextParser;
import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.common.parser.ZonedDateTimeParser;
import org.xmlpull.v1.XmlPullParser;

public class AssetParser {

    private final XmlParser<MutableAsset> parser = new XmlParser<>("asset", MutableAsset.class);

    private final SimpleTextParser subjectParser = new SimpleTextParser("subject");
    private final SimpleTextParser titleParser = new SimpleTextParser("title");
    private final SimpleTextParser revisionParser = new SimpleTextParser("revision");
    private final ZonedDateTimeParser createdParser = new ZonedDateTimeParser("created");
    private final ZonedDateTimeParser modifiedParser = new ZonedDateTimeParser("modified");

    public AssetParser() {
        parser.addElementConsumer("created",
                ((asset, pullParser) -> asset.setCreated(createdParser.apply(pullParser))));
        parser.addElementConsumer("modified",
                ((asset, pullParser) -> asset.setModified(modifiedParser.apply(pullParser))));
        parser.addElementConsumer("subject",
                ((asset, pullParser) -> asset.setSubject(subjectParser.apply(pullParser).toString())));
        parser.addElementConsumer("title",
                ((asset, pullParser) -> asset.setTitle(titleParser.apply(pullParser).toString())));
        parser.addElementConsumer("revision",
                ((asset, pullParser) -> asset.setTitle(revisionParser.apply(pullParser).toString())));
    }

    public MutableAsset parse(XmlPullParser pullParser) {
        return parser.parse(pullParser, new MutableAsset());
    }

}
