package net.dryanhild.collada.schema15.postprocess;

import net.dryanhild.collada.schema15.data.Collada15Document;
import net.dryanhild.collada.schema15.data.ColladaDocumentFragment;

import java.util.ArrayList;
import java.util.List;

public class ColladaDocumentAssembler {

    private final List<ColladaDocumentFragment> fragments = new ArrayList<>();

    public void addFragment(ColladaDocumentFragment fragment) {
        fragments.add(fragment);
    }

    public Collada15Document assemble() {
        return new Collada15Document();
    }

}
