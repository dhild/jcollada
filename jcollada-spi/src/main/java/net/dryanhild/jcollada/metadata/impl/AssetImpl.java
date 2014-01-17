/**
 * 
 */
package net.dryanhild.jcollada.metadata.impl;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import net.dryanhild.jcollada.metadata.Asset;

import com.google.common.collect.Lists;

/**
 * @author dhild
 * 
 */
public class AssetImpl implements Asset {

    private Collection<Contributor> contributors = Lists.newArrayList();
    private Date creationDate;
    private Date modificationDate;
    private Collection<String> keywords = Lists.newArrayList();
    private String revision;
    private String subject;
    private String title;
    private Unit unit;
    private UpAxis upAxis;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Contributor> getContributors() {
        return contributors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addContributor(Contributor contributor) {
        contributors.add(contributor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeContributor(Contributor contributor) {
        return contributors.remove(contributor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContributors(Collection<Contributor> contributors) {
        this.contributors = contributors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModifiedDate() {
        return modificationDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModifiedDate(Date date) {
        this.modificationDate = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getKeywords() {
        return keywords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addKeywords(String keyword) {
        keywords.add(keyword);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKeywords(String keyword) {
        return keywords.remove(keyword);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRevision() {
        return revision;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSubject() {
        return subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit getUnit() {
        return unit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpAxis getUpAxis() {
        return upAxis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUpAxis(UpAxis axis) {
        this.upAxis = axis;
    }

    public static class ContributorImpl implements Asset.Contributor {

        private String author;
        private String authoringTool;
        private String comments;
        private String copyright;
        private URI sourceData;

        /**
         * {@inheritDoc}
         */
        @Override
        public String getAuthor() {
            return author;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getAuthoringTool() {
            return authoringTool;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getComments() {
            return comments;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCopyright() {
            return copyright;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public URI getSourceData() {
            return sourceData;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setAuthor(String author) {
            this.author = author;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setAuthoringTool(String authoringTool) {
            this.authoringTool = authoringTool;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setComments(String comments) {
            this.comments = comments;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setSourceData(URI sourceData) {
            this.sourceData = sourceData;
        }

    }

    public static class UnitImpl implements Asset.Unit {
        private String name;
        private double meter;

        /**
         * {@inheritDoc}
         */
        @Override
        public String getName() {
            return name;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setName(String name) {
            this.name = name;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getMeter() {
            return meter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setMeter(double meter) {
            this.meter = meter;
        }
    }

}
