package net.dryanhild.collada;

@SuppressWarnings("serial")
public class IncorrectFormatException extends ParsingException {

    public IncorrectFormatException(String message) {
        super(message);
    }

    public IncorrectFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
