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

package net.dryanhild.collada.schema15.parser.metadata
import net.dryanhild.collada.data.metadata.Asset
import net.dryanhild.collada.schema15.parser.ParserSpec

import java.time.ZoneOffset

class AssetParserSpec extends ParserSpec {

    def setup() {
        parser = new AssetParser()
    }

    def 'can parse modified dates'() {
        when:
        Asset result = runParser {
            it.asset {
                modified {
                    mkp.yield('2002-05-30T09:02:01Z')
                }
            }
        }

        then:
        result.modified.atZone(ZoneOffset.UTC).year == 2002
        result.modified.atZone(ZoneOffset.UTC).monthValue == 05
        result.modified.atZone(ZoneOffset.UTC).dayOfMonth == 30
    }

}
