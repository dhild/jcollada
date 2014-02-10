package net.dryanhild.collada.data;

import java.net.URI;
import java.net.URL;
import java.util.Collection;

import org.joda.time.DateTime;

public interface Asset {

    Collection<Contributor> getContributors();

    GeographicLocation getCoverage();

    DateTime getCreated();

    Collection<String> getKeywords();

    DateTime getModified();

    String getRevision();

    String getSubject();

    String getTitle();

    Unit getUnit();

    UpAxis getUpAxis();

    public interface Contributor {
        String getAuthor();

        String getAuthorEmail();

        URL getAuthorWebsite();

        String getAuthoringTool();

        String getComments();

        String getCopyright();

        URI getSourceData();
    }

    public interface GeographicLocation {
        double getLongitude();

        double getLatitude();

        double getAltitude();

        AltitudeMode getAltitudeMode();

        public enum AltitudeMode {
            ABSOLUTE, RELATIVE_TO_GROUND
        }
    }

    public interface Unit {
        double getMeters();

        String getName();
    }

    public enum UpAxis {
        X_UP, Y_UP, Z_UP
    }

}
