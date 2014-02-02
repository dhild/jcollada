package net.dryanhild.jcollada;

public class IncorrectFormatException extends ParsingException {

    public IncorrectFormatException(String message) {
        super(message);
    }

    public IncorrectFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
