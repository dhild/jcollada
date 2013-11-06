/**
 * 
 */
package net.dryanhild.jcollada.schema141.metadata;

import java.net.URI;
import java.net.URISyntaxException;

import net.dryanhild.jcollada.metadata.Asset;
import net.dryanhild.jcollada.metadata.Asset.UpAxis;
import net.dryanhild.jcollada.metadata.impl.AssetImpl;
import net.dryanhild.jcollada.schema141.Handler;
import net.dryanhild.jcollada.schema141.gen.ColladaAsset;
import net.dryanhild.jcollada.schema141.gen.ColladaAsset.Contributor;

import com.sun.j3d.loaders.ParsingErrorException;

/**
 * @author dhild
 * 
 */
public final class AssetHandler implements Handler<Asset> {

    private final ColladaAsset _asset;

    public AssetHandler(ColladaAsset asset) {
        _asset = asset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Asset process() {
        try {
            Asset asset = new AssetImpl();

            for (Contributor c : _asset.getContributors()) {
                Asset.Contributor ac = new AssetImpl.ContributorImpl();
                ac.setAuthor(c.getAuthor());
                ac.setAuthoringTool(c.getAuthoringTool());
                ac.setComments(c.getComments());
                ac.setCopyright(c.getCopyright());
                ac.setSourceData(new URI(c.getSourceData()));

                asset.addContributor(ac);
            }

            asset.setCreationDate(_asset.getCreated().getTime());
            asset.setModifiedDate(_asset.getModified().getTime());
            asset.addKeywords(_asset.getKeywords());
            asset.setRevision(_asset.getRevision());
            asset.setSubject(_asset.getSubject());
            asset.setTitle(_asset.getTitle());

            ColladaAsset.Unit unit = _asset.getUnit();

            AssetImpl.UnitImpl unitImpl = new AssetImpl.UnitImpl();
            unitImpl.setMeter(unit.getMeter());
            unitImpl.setName(unit.getName());
            asset.setUnit(unitImpl);

            asset.setUpAxis(UpAxis.valueOf(_asset.getUpAxis().name()));

            return asset;
        } catch (URISyntaxException e) {
            ParsingErrorException ex = new ParsingErrorException("Unable to parse Asset data!");
            ex.addSuppressed(e);
            throw ex;
        }
    }
}
