package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.VersionSupport;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ColladaLoaderTests {

    ColladaLoader loader;

    @BeforeMethod
    public void resetLoader() {
        loader = new ColladaLoader();
    }

    @Test
    public void schmema14Found() {
        Collection<VersionSupport> versions = loader.getRegisteredVersions();
        assertThat(versions).contains(ColladaLoaderService14.VERSION_1_4_0, ColladaLoaderService14.VERSION_1_4_1);
    }

}
