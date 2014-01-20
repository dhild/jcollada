package net.dryanhild.jcollada;

import org.testng.annotations.Test;

public class VersionTests {

    @Test
    public void sameVersionsAreEqual() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "1.2.3");
        VersionSupport versionB = new VersionSupport(1, 2, 3, "1.2.3");

        assertVersionsAreSame(versionA, versionB);
    }

    @Test
    public void differentMajorVersionsAreDifferent() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "1.2.3");
        VersionSupport versionB = new VersionSupport(2, 2, 3, "2.2.3");

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void differentMinorVersionsAreDifferent() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "1.2.3");
        VersionSupport versionB = new VersionSupport(1, 3, 3, "1.3.3");

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void differentThirdVersionsAreDifferent() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "1.2.3");
        VersionSupport versionB = new VersionSupport(1, 2, 2, "1.2.2");

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void differentStringVersionsAreSame() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "test-1");
        VersionSupport versionB = new VersionSupport(1, 2, 3, "test-2");

        assertVersionsAreSame(versionA, versionB);
    }

    @Test
    public void differentObjectsAreNotEqual() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "1.2.3");
        Object versionB = new Object();

        assertVersionsAreDifferent(versionA, versionB);
    }

    @Test
    public void toStringContainsVersions() {
        VersionSupport versionA = new VersionSupport(1, 2, 3, "test");

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
