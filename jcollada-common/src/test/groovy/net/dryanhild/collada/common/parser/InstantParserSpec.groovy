/*
 * Copyright (c) 2015 D. Ryan Hild <d.ryan.hild@gmail.com>
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
 *
 */

package net.dryanhild.collada.common.parser

import java.time.Instant
import java.time.ZoneOffset

class InstantParserSpec extends ParserSpec {

    def setup() {
        parser = new InstantParser('element')
    }

    def 'can parse simple date'() {
        when:
        Instant result = runParser {
            it.element {
                mkp.yield('2002-05-30T09:02:01Z')
            }
        }

        then:
        result.atZone(ZoneOffset.UTC).year == 2002
        result.atZone(ZoneOffset.UTC).monthValue == 05
        result.atZone(ZoneOffset.UTC).dayOfMonth == 30
        result.atZone(ZoneOffset.UTC).hour == 9
        result.atZone(ZoneOffset.UTC).minute == 2
        result.atZone(ZoneOffset.UTC).second == 1
    }

}
