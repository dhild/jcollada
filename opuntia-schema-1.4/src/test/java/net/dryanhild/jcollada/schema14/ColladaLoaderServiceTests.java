package net.dryanhild.jcollada.schema14;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import net.dryanhild.collada.VersionSupport;
import net.dryanhild.collada.schema14.ColladaLoaderService14;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ColladaLoaderServiceTests {

    public static final String TEST_HEADER_140 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.4.0\">";
    public static final String TEST_HEADER_141 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.4.1\">";
    public static final String TEST_HEADER_150 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.5.0\">";

    ColladaLoaderService14 service;

    @BeforeMethod
    public void resetService() {
        service = new ColladaLoaderService14();
    }

    public void versionsAreSupported() {
        Collection<VersionSupport> versions = service.getColladaVersions();

        assertThat(versions)
                .containsExactly(ColladaLoaderService14.VERSION_1_4_0, ColladaLoaderService14.VERSION_1_4_1);
    }

    public void canLoadVersion140() {
        assert service.canLoad(TEST_HEADER_140);
    }

    public void canLoadVersion141() {
        assert service.canLoad(TEST_HEADER_141);
    }

    public void cannotLoadVersion150() {
        assert !service.canLoad(TEST_HEADER_150);
    }

}
