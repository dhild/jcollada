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

import net.dryanhild.collada.schema15.data.geometry.SharedInput
import net.dryanhild.collada.schema15.parser.ParserSpec

class SharedInputParserSpec extends ParserSpec {

    def setup() {
        parser = new SharedInputParser()
    }

    def 'can parse semantic'() {
        when:
        SharedInput result = runParser { it.input(semantic: 'test') }

        then:
        result.semantic == 'test'
    }

    def 'can parse source'() {
        when:
        SharedInput result = runParser { it.input(source: '#test') }

        then:
        result.source.fragment == 'test'
        result.source.toString() == '#test'
    }

    def 'can parse offset'() {
        when:
        SharedInput result = runParser { it.input(offset: '3') }

        then:
        result.offset == 3
    }

    def 'can parse set'() {
        when:
        SharedInput result = runParser { it.input(set: '3') }

        then:
        result.set == 3
    }

    def 'no set is null'() {
        when:
        SharedInput result = runParser { it.input(source: '#test') }

        then:
        result.set == null
    }

}
