package net.dryanhild.jcollada;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import net.dryanhild.jcollada.spi.ParsingContext;

class DefaultParsingContext implements ParsingContext {

    private int flags;
    private boolean validating;
    private Reader mainFileReader;
    private final Map<String, Object> elements;

    public DefaultParsingContext() {
        elements = new HashMap<>();
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    public void setMainFileReader(Reader mainFileReader) {
        this.mainFileReader = mainFileReader;
    }

    @Override
    public int getFlags() {
        return flags;
    }

    @Override
    public boolean isValidating() {
        return validating;
    }

    @Override
    public Reader getMainFileReader() {
        return mainFileReader;
    }

    @Override
    public <T> void storeElementById(String id, T element) {
        elements.put(id, element);
    }

    @Override
    public <T> T getElementById(String id, Class<T> type) {
        return type.cast(elements.get(id));
    }

}
