package net.dryanhild.collada.schema14.parser;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class AbstractParser<SourceType, ResultType> implements Schema15Parser<SourceType, ResultType> {

    @Override
    public Iterable<ResultType> parse(SourceType[] sources) {
        List<ResultType> results = Lists.newArrayList();
        for (SourceType source : sources) {
            results.add(parse(source));
        }
        return results;
    }

}
