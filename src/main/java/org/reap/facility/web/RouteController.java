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

package org.reap.facility.web;

import org.reap.facility.common.Constants;
import org.reap.facility.common.ErrorCodes;
import org.reap.facility.domain.Route;
import org.reap.facility.domain.RouteRepository;
import org.reap.facility.vo.QueryRouteSpec;
import org.reap.support.DefaultResult;
import org.reap.support.Result;
import org.reap.util.Assert;
import org.reap.util.FunctionalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路由维护相关的服务.
 * 
 * @author 7cat
 * @since 1.0
 */
@RestController
public class RouteController {

	@Autowired
	private RouteRepository routeRepository;

	@RequestMapping(path = "/route", method = RequestMethod.POST)
	public Result<Route> create(@RequestBody Route route) {
		validate(route);
		return DefaultResult.newResult(routeRepository.save(route));
	}
	
	@RequestMapping(path = "/route/{id}", method = RequestMethod.DELETE)
	public Result<?> delete(@PathVariable String id) {
		routeRepository.deleteById(id);
		return DefaultResult.newResult();
	}

	@RequestMapping(path = "/route", method = RequestMethod.PUT)
	public Result<Route> update(@RequestBody Route route) {
		Route persisted = FunctionalUtils.orElseThrow(routeRepository.findById(route.getId()),
				ErrorCodes.ROUTE_NOT_EXIST);
		persisted.setName(route.getName());
		persisted.setPath(route.getPath());
		persisted.setServiceId(route.getServiceId());
		persisted.setUrl(route.getUrl());
		persisted.setStripPrefix(route.getStripPrefix());
		return DefaultResult.newResult(routeRepository.save(persisted));
	}

	@RequestMapping(path = "/routes", method = RequestMethod.GET)
	public Result<Page<Route>> find(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int size, QueryRouteSpec spec) {
		return DefaultResult.newResult(routeRepository.findAll(spec.toSpecification(), PageRequest.of(page, size)));
	}

	private void validate(Route route) {
		boolean exists = routeRepository.existsByNameAndServiceId(route.getName(), route.getServiceId());
		Assert.isTrue(!exists, ErrorCodes.DUPLICATED_ROUTE);
	}
}
