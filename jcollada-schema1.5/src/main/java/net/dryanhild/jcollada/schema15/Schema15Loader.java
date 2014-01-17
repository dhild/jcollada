package net.dryanhild.jcollada.schema15;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dryanhild.jcollada.metadata.Version;
import net.dryanhild.jcollada.spi.ColladaLoaderService;
import net.dryanhild.jcollada.spi.ParsingContext;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.sun.j3d.loaders.Scene;

@Service
public class Schema15Loader implements ColladaLoaderService {

    private static final Version VERSION = new Version(1, 5, 0, "1.5.0");

    @Override
    public Collection<Version> getColladaVersions() {
        return ImmutableList.of(VERSION);
    }

    private final Pattern versionPattern = Pattern.compile("COLLADA[^>]+version\\s?=\\s?\\\"1\\.5\\.0\\\"");

    @Override
    public boolean canLoad(CharSequence header) {
        Matcher matcher = versionPattern.matcher(header);
        return matcher.find();
    }

    @Override
    public Scene load(ParsingContext context) {
        // TODO Auto-generated method stub
        return null;
    }

}
