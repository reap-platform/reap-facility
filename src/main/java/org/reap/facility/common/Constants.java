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
 * 集中定义常量.
 * 
 * @author 7cat
 * @since 1.0
 */
public final class Constants {

	public static final String FACILITY_SCHEMA = "REAP_FACILITY";
	
	public static final String DEFAULT_PAGE_SIZE = "10";

	public static final String DEFAULT_PAGE_NUMBER = "0";

	public static final String VERIFY_TOKEN_NO = "N";

	public static final String DEFAULT_CONFIG_EXTRACT_SQL = "SELECT NAME, VALUE from REAP_FACILITY.CONFIG where SYSTEM_CODE=? and PROFILE=? and LABEL=?";

	public static final String API_DOC_URL_PREFIX = "apidoc/index.html";
}
