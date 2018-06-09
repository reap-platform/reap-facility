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

	/** @apiDefine Route 路由维护 */
	/** @apiDefine createRoute 创建路由 */
	/** @apiDefine deleteRoute 删除路由 */
	/** @apiDefine queryRoute 查询路由 */
	/** @apiDefine updateRoute 修改路由*/

	/**
	 * @api {post} /route 创建路由
	 * @apiName createRoute
	 * @apiGroup Route
	 * @apiParam (Body) {String} name 路由名称
	 * @apiParam (Body) {String} path 路由规则
	 * @apiParam (Body) {String} [serviceId] eureka 中注册的服务 id，如果设置了 url 不需要送值
	 * @apiParam (Body) {String} [url] 非注册在 eureka 中的服务转发的全限定 url
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 新建路由 id
	 * @apiSuccess (Success) {String} payload.name 路由名称
	 * @apiSuccess (Success) {String} payload.servieId 服务 id
	 * @apiSuccess (Success) {String} payload.url 路由转发 url
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/route", method = RequestMethod.POST)
	public Result<Route> create(@RequestBody Route route) {
		validate(route);
		return DefaultResult.newResult(routeRepository.save(route));
	}

	/**
	 * @api {delete} /route/:id 删除路由
	 * @apiName deleteRoute
	 * @apiGroup Route
	 * @apiParam (PathVariable) {String} id 参数 id
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/route/{id}", method = RequestMethod.DELETE)
	public Result<?> delete(@PathVariable String id) {
		routeRepository.deleteById(id);
		return DefaultResult.newResult();
	}

	/**
	 * @api {put} /route 更新路由
	 * @apiName updateRoute
	 * @apiGroup Route
	 * @apiParam (Body) {String} id 路由 id
	 * @apiParam (Body) {String} name 路由名称
	 * @apiParam (Body) {String} path 路由规则
	 * @apiParam (Body) {String} serviceId eureka 中注册的服务 id，如果设置了 url 不需要送值
	 * @apiParam (Body) {String} url 非注册在 eureka 中的服务转发的全限定 url
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 新建路由 id
	 * @apiSuccess (Success) {String} payload.name 路由名称
	 * @apiSuccess (Success) {String} payload.servieId 服务 id
	 * @apiSuccess (Success) {String} payload.url 路由转发 url
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
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

	/**
	 * @api {get} /routes 查询路由
	 * @apiName queryRoute
	 * @apiGroup Route
	 * @apiParam (QueryString) {Number} [page=0] 页码
	 * @apiParam (QueryString) {Number} [size=10] 每页记录数
	 * @apiParam (QueryString) {String} [name] 路由名称 
	 * @apiParam (QueryString) {String} [path] 路由规则
	 * @apiParam (QueryString) {String} [serviceId] 服务 id
	 * @apiParam (QueryString) {String} [url] 转发 url
	 * @apiSuccess (Success) {Boolean} success 成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {Number} payload.totalPages 总页数
	 * @apiSuccess (Success) {Number} payload.totalElements 总记录数
	 * @apiSuccess (Success) {Number} payload.numberOfElements 当前记录数
	 * @apiSuccess (Success) {Object[]} payload.content 路由列表
	 * @apiSuccess (Success) {String} payload.content.id 路由 id
	 * @apiSuccess (Success) {String} payload.content.name 路由名称
	 * @apiSuccess (Success) {String} payload.content.path 路由规则
	 * @apiSuccess (Success) {String} payload.content.serviceId 服务 id
	 * @apiSuccess (Success) {String} payload.content.url 转发 url
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
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
