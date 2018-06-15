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

package org.reap.facility.tracing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import brave.http.HttpTracing;
import brave.servlet.ReapTracingFilter;
import brave.servlet.TracingFilter;

/**
 * 
 * @author 7cat
 * @since 1.0
 */
public class TracingFilterPostProcessor implements BeanPostProcessor {

	public TracingFilterPostProcessor(HttpTracing httpTracing) {
		this.httpTracing = httpTracing;
	}


	private HttpTracing httpTracing;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof FilterRegistrationBean) {
			FilterRegistrationBean filterBean = (FilterRegistrationBean) bean;
			if (filterBean.getFilter() instanceof TracingFilter) {
				filterBean.setFilter(ReapTracingFilter.create(httpTracing));
			}
		}
		return bean;
	}
}
