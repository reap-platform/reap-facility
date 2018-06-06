/*
 * Copyright (c) 2018-present the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.reap.facility.common;

/**
 * 集中定义错误码格式如下: 'REAP【FA】【2位子模块】【2位错误码】'
 * 
 * <pre>
 * REAPFA0001
 * </pre>
 * 
 * @author 7cat
 * @since 1.0
 */
public final class ErrorCodes {

	/** 配置不存在. */
	public static final String CONFIG_NOT_EXIST = "REAPFA0001";

	/** 配置项重复. */
	public static final String DUPLICATED_CONFIG = "REAPFA0002";

	/** 路由不存在. */
	public static final String ROUTE_NOT_EXIST = "REAPFA0003";

	/** 路由配置重复. */
	public static final String DUPLICATED_ROUTE = "REAPFA0004";

}
