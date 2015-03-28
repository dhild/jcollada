package net.dryanhild.collada;

import net.dryanhild.collada.data.ColladaDocument;
import net.dryanhild.collada.schema14.ColladaLoaderService14;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic tests to verify that the schema 1.4 loader can be found and used.
 */
public class Schema14LoaderTest {

    @Test
    public void versionAccessible() {
        ColladaLoader loader = new ColladaLoader();

        Collection<VersionSupport> versions = loader.getRegisteredVersions();

        assertThat(versions).contains(ColladaLoaderService14.VERSION_1_4_0);
        assertThat(versions).contains(ColladaLoaderService14.VERSION_1_4_1);
    }

    @Test
    public void loadFile14() throws IOException {
        ColladaLoader loader = new ColladaLoader();
        loader.setValidating(false);

        ColladaDocument document = loader.load(Thread.currentThread().getContextClassLoader().getResource("test-schema14.dae"));

        assertThat(document.getVersion()).isEqualTo(ColladaLoaderService14.VERSION_1_4_1);
        assertThat(document.getGeometries()).extracting("name").contains("Model_E0_MESH_0_REF_1");
    }
}
