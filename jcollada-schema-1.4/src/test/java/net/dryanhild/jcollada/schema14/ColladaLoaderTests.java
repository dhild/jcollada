package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import net.dryanhild.collada.ColladaLoader;
import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.schema14.ColladaLoaderService14;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ColladaLoaderTests {

    ColladaLoader loader;

    @BeforeMethod
    public void resetLoader() {
        loader = new ColladaLoader();
    }

    public void schmema14Found() {
        Collection<VersionSupport> versions = loader.getRegisteredVersions();
        assertThat(versions).contains(ColladaLoaderService14.VERSION_1_4_0, ColladaLoaderService14.VERSION_1_4_1);
    }

}
