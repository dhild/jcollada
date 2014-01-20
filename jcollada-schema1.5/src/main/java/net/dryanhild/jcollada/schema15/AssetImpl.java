package net.dryanhild.jcollada.schema15;

import java.util.ArrayList;
import java.util.Collection;

import net.dryanhild.jcollada.data.AssetDescription;

import org.joda.time.DateTime;

class AssetImpl implements AssetDescription {

    private DateTime created;
    private DateTime modified;
    private String revision;
    private String subject;
    private String title;
    private Unit unit;
    private UpAxis upAxis;
    private final Collection<Contributor> contributors = new ArrayList<>();
    private GeographicLocation location;
    private final Collection<String> keywords = new ArrayList<>();

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public void setModified(DateTime modified) {
        this.modified = modified;
    }

    public GeographicLocation getLocation() {
        return location;
    }

    public void setLocation(GeographicLocation location) {
        this.location = location;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setUpAxis(UpAxis upAxis) {
        this.upAxis = upAxis;
    }

    @Override
    public Collection<Contributor> getContributors() {
        return contributors;
    }

    @Override
    public GeographicLocation getCoverage() {
        return location;
    }

    @Override
    public DateTime getCreated() {
        return created;
    }

    @Override
    public Collection<String> getKeywords() {
        return keywords;
    }

    @Override
    public DateTime getModified() {
        return modified;
    }

    @Override
    public String getRevision() {
        return revision;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public UpAxis getUpAxis() {
        return upAxis;
    }

}
