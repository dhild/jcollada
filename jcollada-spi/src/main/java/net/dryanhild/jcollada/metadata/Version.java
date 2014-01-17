package net.dryanhild.jcollada.metadata;

public class Version {

    public final int majorVersion;
    public final int minorVersion;
    public final int thirdVersion;
    public final String versionString;

    public Version(int major, int minor, int third, String version) {
        majorVersion = major;
        minorVersion = minor;
        thirdVersion = third;
        versionString = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (61 * hash) + this.majorVersion;
        hash = (61 * hash) + this.minorVersion;
        hash = (61 * hash) + this.thirdVersion;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Version other = (Version) obj;
        if (this.majorVersion != other.majorVersion) {
            return false;
        }
        if (this.minorVersion != other.minorVersion) {
            return false;
        }
        if (this.thirdVersion != other.thirdVersion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(20);
        builder.append("Version=[").append(majorVersion).append('.').append(minorVersion).append('.')
                .append(thirdVersion).append(", ").append(versionString).append(']');
        return builder.toString();
    }

}
