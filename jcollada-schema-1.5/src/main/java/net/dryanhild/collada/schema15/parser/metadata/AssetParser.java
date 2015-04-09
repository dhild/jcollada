package net.dryanhild.collada.schema15.parser.metadata;

import net.dryanhild.collada.common.metadata.MutableAsset;
import net.dryanhild.collada.common.parser.InstantParser;
import net.dryanhild.collada.common.parser.XmlParser;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class AssetParser implements Function<XmlPullParser, MutableAsset> {

    private final XmlParser<MutableAsset> parser = new XmlParser<>("asset", MutableAsset.class);

    public AssetParser() {
        parser.addElementConsumer("created", MutableAsset::setCreated, new InstantParser("created"));
        parser.addElementConsumer("modified", MutableAsset::setModified, new InstantParser("modified"));
        parser.addStringElementConsumer("subject", MutableAsset::setSubject);
        parser.addStringElementConsumer("title", MutableAsset::setTitle);
        parser.addStringElementConsumer("revision", MutableAsset::setRevision);
    }

    @Override
    public MutableAsset apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new MutableAsset());
    }

}
