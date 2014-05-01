package net.dryanhild.collada.schema15.parser;

import java.util.List;

import com.google.common.collect.Lists;

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
