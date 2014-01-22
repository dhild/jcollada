package net.dryanhild.jcollada.schema15;

public interface TypeProcessor<InputType, OutputType> {

    OutputType process(InputType input);

}
