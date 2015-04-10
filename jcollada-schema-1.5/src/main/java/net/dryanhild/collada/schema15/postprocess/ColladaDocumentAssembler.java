package net.dryanhild.collada.schema15.postprocess;

import net.dryanhild.collada.schema15.PerParse;
import net.dryanhild.collada.schema15.data.Collada15Document;
import org.collada.schema15.COLLADA;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@PerParse
public class ColladaDocumentAssembler implements DocumentAssembler {

    private final List<COLLADA> fragments = new ArrayList<>();

    @Override
    public void addFragment(COLLADA fragment) {
        fragments.add(fragment);
    }

    @Override
    public Collada15Document assemble() {
        return new Collada15Document();
    }

}
