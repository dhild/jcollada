package net.dryanhild.collada.common.parser;

import com.carrotsearch.hppc.FloatArrayList;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatParser implements Function<CharSequence, FloatArrayList> {

    private static final Pattern pattern = Pattern.compile("\\S+");

    @Override
    public FloatArrayList apply(CharSequence sequence) {
        FloatArrayList floats = new FloatArrayList();

        Matcher matcher = pattern.matcher(sequence);
        while (matcher.find()) {
            floats.add(Float.parseFloat(matcher.group()));
        }
        return floats;
    }
}
