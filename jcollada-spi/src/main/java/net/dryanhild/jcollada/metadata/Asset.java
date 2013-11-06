/**
 * 
 */
package net.dryanhild.jcollada.metadata;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

/**
 * @author dhild
 * 
 */
public interface Asset {

    Collection<Contributor> getContributors();

    void addContributor(Contributor contributor);

    boolean removeContributor(Contributor contributor);

    void setContributors(Collection<Contributor> contributors);

    Date getCreationDate();

    void setCreationDate(Date date);

    Date getModifiedDate();

    void setModifiedDate(Date date);

    Collection<String> getKeywords();

    void addKeywords(String keyword);

    boolean removeKeywords(String keyword);

    void setKeywords(Collection<String> keywords);

    String getRevision();

    void setRevision(String revision);

    String getSubject();

    void setSubject(String subject);

    String getTitle();

    void setTitle(String title);

    Unit getUnit();

    void setUnit(Unit unit);

    UpAxis getUpAxis();

    void setUpAxis(UpAxis axis);

    public interface Contributor {
        String getAuthor();

        String getAuthoringTool();

        String getComments();

        String getCopyright();

        URI getSourceData();

        void setAuthor(String author);

        void setAuthoringTool(String authoringTool);

        void setComments(String comments);

        void setCopyright(String copyright);

        void setSourceData(URI sourceData);
    }

    public interface Unit {
        String getName();

        void setName(String name);

        double getMeter();

        void setMeter(double meter);
    }

    public enum UpAxis {
        X_UP, Y_UP, Z_UP
    }

}
