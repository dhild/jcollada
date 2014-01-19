package net.dryanhild.jcollada;

import net.dryanhild.jcollada.metadata.Version;

import org.testng.annotations.Test;

public class VersionTests {

    @Test
    public void sameVersionsAreEqual() {
        Version versionA = new Version(1, 2, 3, "1.2.3");
        Version versionB = new Version(1, 2, 3, "1.2.3");

        assertVersionsAreSame(versionA, versionB);
    }

    @Test
    public void differentMajorVersionsAreDifferent() {
        Version versionA = new Version(1, 2, 3, "1.2.3");
        Version versionB = new Version(2, 2, 3, "2.2.3");

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void differentMinorVersionsAreDifferent() {
        Version versionA = new Version(1, 2, 3, "1.2.3");
        Version versionB = new Version(1, 3, 3, "1.3.3");

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void differentThirdVersionsAreDifferent() {
        Version versionA = new Version(1, 2, 3, "1.2.3");
        Version versionB = new Version(1, 2, 2, "1.2.2");

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void differentStringVersionsAreSame() {
        Version versionA = new Version(1, 2, 3, "test-1");
        Version versionB = new Version(1, 2, 3, "test-2");

        assertVersionsAreSame(versionA, versionB);
    }

    @Test
    public void differentObjectsAreNotEqual() {
        Version versionA = new Version(1, 2, 3, "1.2.3");
        Object versionB = new Object();

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void toStringContainsVersions() {
        Version versionA = new Version(1, 2, 3, "test");

        String versionString = versionA.toString();

        assert versionString.contains(Integer.toString(1));
        assert versionString.contains(Integer.toString(2));
        assert versionString.contains(Integer.toString(3));
    }

    private void assertVersionsAreDifferent(Object versionA, Object versionB) {
        assert versionA.hashCode() != versionB.hashCode();
        assert !versionA.equals(versionB);
        assert !versionB.equals(versionA);
    }

    private void assertVersionsAreSame(Object versionA, Object versionB) {
        assert versionA.hashCode() == versionB.hashCode();
        assert versionA.equals(versionB);
        assert versionB.equals(versionA);
    }

}
