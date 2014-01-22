package net.dryanhild.jcollada.schema15;

import net.dryanhild.jcollada.schema15.FileMarshaller;
import net.dryanhild.jcollada.spi.ParsingContext;

import org.testng.annotations.Test;

public class MarshallerTests {

    @Test
    public void bareCOLLADAWithValidationLoads() {
        FileMarshaller marshaller = new FileMarshaller();

        String invalidContent = "<COLLADA xmlns=\"http://www.collada.org/2008/03/COLLADASchema\" " + //
                "version=\"1.5.0\">" + //
                "<asset>" + //
                "<created>2014-01-01</created>" + //
                "<modified>2014-01-01</modified>" + //
                "</asset></COLLADA>";

        ParsingContext context = ServiceBasicTests.mockInput(invalidContent);

        marshaller.loadFrom(context.getMainFileReader(), true);

        assert marshaller.getMainAsset() != null;
    }

}
