package net.dryanhild.collada.schema15.data;

import lombok.Getter;
import lombok.Setter;
import net.dryanhild.collada.common.metadata.MutableAsset;

import java.net.URI;

@Getter
@Setter
public class ColladaDocumentFragment {

    private URI uri;

    private MutableAsset asset;

}
