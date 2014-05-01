/*
 * Copyright (c) 2014 D. Ryan Hild <d.ryan.hild@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.dryanhild.collada;

public class VersionSupport {

    private static final int PRIME = 31;
    public final int majorVersion;
    public final int minorVersion;
    public final int thirdVersion;

    public VersionSupport(int major, int minor, int third) {
        majorVersion = major;
        minorVersion = minor;
        thirdVersion = third;
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
                .append(thirdVersion).append(']');
        return builder.toString();
    }

}
