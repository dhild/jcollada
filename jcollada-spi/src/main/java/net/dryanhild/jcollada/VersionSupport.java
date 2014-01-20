package net.dryanhild.jcollada;

public class VersionSupport {

    private static final int PRIME = 31;
    public final int majorVersion;
    public final int minorVersion;
    public final int thirdVersion;
    public final String versionString;

    public VersionSupport(int major, int minor, int third, String version) {
        majorVersion = major;
        minorVersion = minor;
        thirdVersion = third;
        versionString = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (PRIME * hash) + this.majorVersion;
        hash = (PRIME * hash) + this.minorVersion;
        hash = (PRIME * hash) + this.thirdVersion;
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
        final VersionSupport other = (VersionSupport) obj;
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
        StringBuilder builder = new StringBuilder();
        builder.append("Version=[").append(majorVersion).append('.').append(minorVersion).append('.')
                .append(thirdVersion).append(", ").append(versionString).append(']');
        return builder.toString();
    }

}
