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

package org.reap.facility.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.reap.facility.common.Constants;
import org.reap.facility.common.Fields;
import org.reap.support.DefaultResult;
import org.reap.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author hehaiwei
 *
 */
@ConfigurationProperties(prefix = "apis")
@Component
public class AccessFilter extends ZuulFilter {

	@Value("${verify.token:Y}")
	private String verifyToken;

	@Value("${apis.token}")
	private String tokenApi;

	@Autowired
	RestTemplate restTemplate;

	private List<String> ignore = new ArrayList<String>();

	@Override
	public Object run() throws ZuulException {
		if (Constants.VERIFY_TOKEN_NO.equals(verifyToken))
			return null;
		RequestContext ctx = RequestContext.getCurrentContext();
		LinkedMultiValueMap<String, String> requestMap = new LinkedMultiValueMap<String, String>();
		requestMap.add(Fields.TOKEN, ctx.getRequest().getHeader(Fields.TOKEN));
		Result<String> result = restTemplate.exchange(tokenApi, HttpMethod.PUT,
				new HttpEntity<MultiValueMap<String, String>>(requestMap, null),
				new ParameterizedTypeReference<DefaultResult<String>>() {
				}).getBody();
		if (!result.isSuccess()) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody(new Gson().toJson(result));
			ctx.getResponse().setContentType("application/json; charset=utf-8");
		}
		ctx.getResponse().setHeader(Fields.TOKEN, result.getPayload());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
		if (req.getRequestURI().matches("^/apis/.*")) {
			for (String api : ignore) {
				if (req.getRequestURI().matches(api + ".*")) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	public List<String> getIgnore() {
		return ignore;
	}

	public void setIgnore(List<String> ignore) {
		this.ignore = ignore;
	}

}
