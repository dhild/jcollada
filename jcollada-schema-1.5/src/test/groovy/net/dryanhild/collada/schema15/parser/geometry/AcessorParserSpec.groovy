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

package net.dryanhild.collada.schema15.parser.geometry
import net.dryanhild.collada.schema15.data.geometry.Accessor
import net.dryanhild.collada.schema15.parser.ParserSpec

class AcessorParserSpec extends ParserSpec {

    def setup() {
        parser = new AcessorParser()
    }

    def 'can parse count'() {
        when:
        Accessor result = runParser { it.accessor(count: '4') }

        then:
        result.count == 4
    }

    def 'can parse offset'() {
        when:
        Accessor result = runParser { it.accessor(offset: '4') }

        then:
        result.offset == 4
    }

    def 'no offset is null'() {
        when:
        Accessor result = runParser { it.accessor(count: '4') }

        then:
        result.offset == null
    }

    def 'can parse stride'() {
        when:
        Accessor result = runParser { it.accessor(stride: '4') }

        then:
        result.stride == 4
    }

    def 'no stride is null'() {
        when:
        Accessor result = runParser { it.accessor(count: '4') }

        then:
        result.stride == null
    }

    def 'can parse source'() {
        when:
        Accessor result = runParser { it.accessor(source: '#mysource') }

        then:
        result.source.fragment == 'mysource'
        result.source.toString() == '#mysource'
    }

    def 'can parse param'() {
        when:
        Accessor result = runParser {
            it.accessor(source: '#test') {
                param(name: 'param1')
                param(name: 'param2')
            }
        }

        then:
        result.params*.name == ['param1', 'param2']
    }

}
