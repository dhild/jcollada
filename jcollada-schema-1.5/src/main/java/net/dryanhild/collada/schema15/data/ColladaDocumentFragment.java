package net.dryanhild.collada.schema15.data;

import lombok.Getter;

import java.net.URI;

@Getter
public class ColladaDocumentFragment {

    private final URI uri;

    public ColladaDocumentFragment(URI uri) {
        this.uri = uri;
    }

}
