package net.dryanhild.collada.schema14.parser;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Schema15Parser<SourceType, ResultType> {

    ResultType parse(SourceType source);

    Iterable<ResultType> parse(SourceType[] source);

}
