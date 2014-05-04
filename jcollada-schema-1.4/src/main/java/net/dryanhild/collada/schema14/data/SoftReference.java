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

package net.dryanhild.collada.schema14.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SoftReference<ReferenceType> implements InvocationHandler {

    public static <ReferenceType> ReferenceType createSoftReference(String url, Class<ReferenceType> typeClass) {
        return createSoftReferenceImpl(typeClass, new SoftReference<ReferenceType>(url));
    }

    protected static <ReferenceType> ReferenceType createSoftReferenceImpl(Class<ReferenceType> typeClass,
                                                                           SoftReference<ReferenceType> ref) {
        InvocationHandler handler = ref;
        ClassLoader loader = typeClass.getClassLoader();
        Object proxyObject = Proxy.newProxyInstance(loader, new Class<?>[]{typeClass}, handler);
        return typeClass.cast(proxyObject);
    }

    protected String url;

    protected SoftReference(String url) {
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: Translate the url into a hard reference.
        throw new UnsupportedOperationException("Reference resolutions are not yet supported!");
    }
}
