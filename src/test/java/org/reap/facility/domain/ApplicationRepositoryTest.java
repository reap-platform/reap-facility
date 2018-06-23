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

package org.reap.facility.domain;

import org.junit.Test;
import org.reap.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


/**
 * 
 * @author 7cat
 * @since 1.0
 */
public class ApplicationRepositoryTest extends BaseTest{

	@Autowired
	private ApplicationRepository applicationRepository;
	
	/**
	 * Test method for {@link org.reap.facility.domain.ApplicationRepository#existsByNameOrSystemCode(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testExistsByNameOrSystemCode() {
		assertTrue(applicationRepository.existsByNameOrSystemCode("用户管理服务",null));
	}
}
