package net.dryanhild.jcollada.schema15;

import java.util.Calendar;

import net.dryanhild.jcollada.data.AssetDescription;

import org.collada.x2008.x03.colladaSchema.AssetType;
import org.joda.time.DateTime;

class AssetHandler {

    public AssetDescription loadAssetDescription(AssetType rawAsset) {
        AssetImpl outputAsset = new AssetImpl();

        Calendar created = rawAsset.getCreated();
        Calendar modified = rawAsset.getModified();

        outputAsset.setCreated(new DateTime(created.getTime()));
        outputAsset.setModified(new DateTime(modified.getTime()));

        outputAsset.setRevision(rawAsset.getRevision());

        return outputAsset;
    }
}
