package net.dryanhild.jcollada;

import net.dryanhild.jcollada.metadata.Version;

import org.testng.annotations.Test;

public class DefaultParsingContextTests {

    @Test
    public void storeThenSetReturnsOriginalObject() {
        DefaultParsingContext context = new DefaultParsingContext();

        String key = "version";
        Version value = ColladaLoaderServiceImpl.TEST_VERSION;

        context.storeElementById(key, value);

        Version found = context.getElementById(key, Version.class);

        assert value == found;
    }
}
