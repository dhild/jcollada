package net.dryanhild.collada.schema15.postprocess;

import net.dryanhild.collada.schema15.data.Collada15Document;
import net.dryanhild.collada.schema15.data.ColladaDocumentFragment;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColladaDocumentAssembler implements DocumentAssembler {

    private final List<ColladaDocumentFragment> fragments = new ArrayList<>();

    @Override
    public void addFragment(ColladaDocumentFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Collada15Document assemble() {
        return new Collada15Document();
    }

}
