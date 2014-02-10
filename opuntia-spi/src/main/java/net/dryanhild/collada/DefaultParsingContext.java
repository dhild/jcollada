package net.dryanhild.collada;

import java.io.Reader;

import net.dryanhild.collada.spi.ParsingContext;

public class DefaultParsingContext implements ParsingContext {

    private boolean validating;
    private Reader mainFileReader;

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    public void setMainFileReader(Reader mainFileReader) {
        this.mainFileReader = mainFileReader;
    }

    @Override
    public boolean isValidating() {
        return validating;
    }

    @Override
    public Reader getMainFileReader() {
        return mainFileReader;
    }

}
