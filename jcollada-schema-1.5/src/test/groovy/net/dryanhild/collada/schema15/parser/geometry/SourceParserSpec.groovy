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

import net.dryanhild.collada.schema15.data.geometry.RawSource
import net.dryanhild.collada.schema15.parser.ParserSpec
import spock.lang.FailsWith

class SourceParserSpec extends ParserSpec {

    def setup() {
        parser = new SourceParser()
    }

    def 'can parse id'() {
        when:
        RawSource result = runParser { it.source(id: 'test') }

        then:
        result.id == 'test'
    }

    def 'can parse name'() {
        when:
        RawSource result = runParser { it.source(name: 'test') }

        then:
        result.name == 'test'
    }

    def 'can parse asset'() {
        when:
        RawSource result = runParser {
            it.source(name: 'test') {
                asset {
                    title {
                        mkp.yield 'test title'
                    }
                }
            }
        }

        then:
        result.asset.title == 'test title'
    }

    def 'can parse float arary'() {
        when:
        RawSource result = runParser {
            it.source(name: 'test') {
                float_array {
                    mkp.yield '1.0 2.2 3.333 4.444'
                }
            }
        }

        then:
        result.floats.floats.toArray().toList() == [1.0f, 2.2f, 3.333f, 4.444f]
    }

    def 'can parse int arary'() {
        when:
        RawSource result = runParser {
            it.source(name: 'test') {
                int_array {
                    mkp.yield '4 5 6 7 8'
                }
            }
        }

        then:
        result.ints.ints.toArray().toList() == [4, 5, 6, 7, 8]
    }

    def 'can parse common technique'() {
        when:
        RawSource result = runParser {
            it.source(name: 'test') {
                technique_common {
                    accessor {
                        param(name: 'param1')
                        param(name: 'param2')
                    }
                }
            }
        }

        then:
        result.techniqueCommon.accessor.params*.name == ['param1', 'param2']
    }

    @FailsWith(value = NullPointerException, reason = "Conditional nested elements aren't paresable yet")
    def 'can parse common technique asset'() {
        when:
        RawSource result = runParser {
            it.source(name: 'test') {
                technique_common {
                    accessor {
                        asset {
                            title {
                                mkp.yield 'test title'
                            }
                        }
                    }
                }
            }
        }

        then:
        result.techniqueCommon.asset.title == 'test title'
    }

}
