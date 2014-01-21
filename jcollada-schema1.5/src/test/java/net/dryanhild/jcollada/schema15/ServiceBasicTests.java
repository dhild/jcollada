package net.dryanhild.jcollada.schema15;

import java.io.StringReader;
import java.util.Collection;

import net.dryanhild.jcollada.ColladaLoader;
import net.dryanhild.jcollada.DefaultParsingContext;
import net.dryanhild.jcollada.VersionSupport;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ParsingContext;

import org.testng.annotations.Test;

import com.sun.j3d.loaders.IncorrectFormatException;

public class ServiceBasicTests {

    @Test
    public void versionSupportIsCorrect() {
        ColladaLoaderService loader = new Schema15Loader();
        Collection<VersionSupport> versions = loader.getColladaVersions();

        assert versions.size() == 1;

        VersionSupport version = versions.iterator().next();

        assert version.majorVersion == 1;
        assert version.minorVersion == 5;
        assert version.thirdVersion == 0;
        assert "1.5.0".equals(version.versionString);
    }

    @Test
    public void versionIsIdentifiedByService() {
        ColladaLoader loader = new ColladaLoader();

        Collection<VersionSupport> versions = loader.getRegisteredVersions();

        assert versions.contains(new VersionSupport(1, 5, 0, "1.5.0"));
    }

    @Test
    public void versionCanBeLoaded() {
        ColladaLoaderService service = new Schema15Loader();

        String fileHeader = "<COLLADA version=\"1.5.0\">";

        assert service.canLoad(fileHeader);
    }

    @Test(expectedExceptions = IncorrectFormatException.class)
    public void incorrectInternalFormatThrowsException() {
        ColladaLoaderService service = new Schema15Loader();

        String invalidContent = "<COLLADA version=\"1.5.0\">";

        ParsingContext context = mockInput(invalidContent);

        service.load(context);
    }

    @Test
    public void bareCOLLADALoads() {
        ColladaLoaderService service = new Schema15Loader();

        String invalidContent = "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" " + //
                "version=\"1.5.0\">" + //
                "<asset>" + //
                "<created>2014-01-01</created>" + //
                "<modified>2014-01-01</modified>" + //
                "</asset></COLLADA>";

        ParsingContext context = ServiceBasicTests.mockInput(invalidContent);

        service.load(context);
    }

    public static ParsingContext mockInput(String invalidContent) {
        DefaultParsingContext context = new DefaultParsingContext();

        String headerPrepended = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + invalidContent;

        context.setMainFileReader(new StringReader(headerPrepended));

        return context;
    }

}
