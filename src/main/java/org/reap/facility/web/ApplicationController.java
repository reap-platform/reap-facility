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

import java.util.List;

import org.reap.facility.common.Constants;
import org.reap.facility.common.ErrorCodes;
import org.reap.facility.common.Fields;
import org.reap.facility.domain.Application;
import org.reap.facility.domain.ApplicationRepository;
import org.reap.facility.vo.RuntimeInformation;
import org.reap.support.Result;
import org.reap.util.Assert;
import org.reap.util.FunctionalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;

/**
 * @author 7cat
 * @since 1.0
 */
@RestController
public class ApplicationController {

	@Autowired(required = false)
	private EurekaClient discoveryClient;

	@Autowired
	private ApplicationRepository applicationRepository;

	/** @apiDefine Application 应用维护 */

	/**
	 * @api {post} /application 创建应用
	 * @apiName createApplication
	 * @apiGroup Application
	 * @apiParam (Body) {String} name 应用名称
	 * @apiParam (Body) {String} systemCode 应用代码（spring.application.name）
	 * @apiParam (Body) {String} owner 负责人的 username
	 * @apiParam (Body) {String} remark 备注
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 应用的唯一标识（UUID 无业务语义）
	 * @apiSuccess (Success) {String} payload.systemCode 应用代码（spring.application.name）
	 * @apiSuccess (Success) {String} payload.createTime 创建时间
	 * @apiSuccess (Success) {String} payload.owner 负责人的 username
	 * @apiSuccess (Success) {String} payload.remark 备注
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/application", method = RequestMethod.POST)
	public Result<Application> create(@RequestBody Application application) {
		validate(application);
		return Result.newResult(applicationRepository.save(application));
	}

	/**
	 * @api {delete} /application/{id} 删除应用
	 * @apiName deleteApplication
	 * @apiGroup Application
	 * @apiParam (PathVariable) {String} id 参数 id
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/application/{id}", method = RequestMethod.DELETE)
	public Result<?> delete(@PathVariable String id) {
		applicationRepository.deleteById(id);
		return Result.newResult();
	}

	/**
	 * @api {put} /application 更新应用
	 * @apiName updateApplication
	 * @apiGroup Application
	 * @apiParam (Body) {String} id 应用的唯一标识（UUID 无业务语义）
	 * @apiParam (Body) {String} name 应用名称
	 * @apiParam (Body) {String} systemCode 应用代码（spring.application.name）
	 * @apiParam (Body) {String} owner 负责人的 username
	 * @apiParam (Body) {String} remark 备注
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 应用的唯一标识（UUID 无业务语义）
	 * @apiSuccess (Success) {String} payload.systemCode 应用代码（spring.application.name）
	 * @apiSuccess (Success) {String} payload.createTime 创建时间
	 * @apiSuccess (Success) {String} payload.owner 负责人的 username
	 * @apiSuccess (Success) {String} payload.remark 备注
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/application", method = RequestMethod.PUT)
	public Result<Application> update(@RequestBody Application application) {
		Application persisted = FunctionalUtils.orElseThrow(applicationRepository.findById(application.getId()),
				ErrorCodes.CONFIG_NOT_EXIST);
		persisted.setName(application.getName());
		persisted.setOwner(application.getOwner());
		persisted.setRemark(application.getRemark());
		persisted.setSystemCode(application.getSystemCode());
		return Result.newResult(applicationRepository.save(persisted));
	}

	/**
	 * @api {get} /applications 查询应用
	 * @apiName queryApplications
	 * @apiGroup Application
	 * @apiParam (QueryString) {Number} [page=0] 页码
	 * @apiParam (QueryString) {Number} [size=10] 每页记录数
	 * @apiParam (QueryString) {String} [name] 应用名称，模糊匹配
	 * @apiParam (QueryString) {String} [owner] 负责人，
	 * @apiParam (QueryString) {String} [systemCode] 系统代码，模糊匹配
	 * @apiSuccess (Success) {Boolean} success 成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {Number} payload.totalPages 总页数
	 * @apiSuccess (Success) {Number} payload.totalElements 总记录数
	 * @apiSuccess (Success) {Number} payload.numberOfElements 当前记录数
	 * @apiSuccess (Success) {Object[]} payload.content 应用列表
	 * @apiSuccess (Success) {String} payload.content.id 应用的唯一标识（UUID 无业务语义）
	 * @apiSuccess (Success) {String} payload.content.systemCode 应用代码（spring.application.name）
	 * @apiSuccess (Success) {String} payload.content.createTime 创建时间
	 * @apiSuccess (Success) {String} payload.content.owner 负责人的 username
	 * @apiSuccess (Success) {String} payload.content.remark 备注
	 * @apiSuccess (Success) {Object} payload.content.information 运行信息
	 * @apiSuccess (Success) {String} payload.content.information.status 运行状态: 'UNKNOW' 未知 'UP' 运行 'WARNING' 警告
	 * @apiSuccess (Success) {String} payload.content.information.apiDocUrl 接口文档 Url
	 * @apiSuccess (Success) {Object[]} payload.content.information.instants 节点信息
	 * @apiSuccess (Success) {String} payload.content.information.instants.status 运行状态: 'UP' 活动 'DOWN' 关闭 'STARTING' 启动中
	 *             'UKNOW' 未知
	 * @apiSuccess (Success) {String} payload.content.information.instants.homePageUrl 主页地址
	 * @apiSuccess (Success) {String} payload.content.information.instants.ipAddr IP 地址
	 * @apiSuccess (Success) {String} payload.content.information.instants.lastUpdatedTimestamp 最后一次状态更新时间
	 * @apiSuccess (Success) {String} payload.content.information.instants.lastDirtyTimestamp TODO 最后一次XX时间?
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/applications", method = RequestMethod.GET)
	public Result<Page<Application>> find(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int size, Application application) {
		Example<Application> example = Example.of(application,
				ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(StringMatcher.CONTAINING));
		PageRequest  pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, Fields.SYSTEM_CODE));
		Page<Application> applicationPage = applicationRepository.findAll(example,pageRequest);

		if (discoveryClient != null) {
			Applications applications = discoveryClient.getApplications();
			applicationPage.getContent().stream().forEach(app -> {
				app.setInformation(new RuntimeInformation(applications.getRegisteredApplications(app.getSystemCode())));
			});
		}
		return Result.newResult(applicationPage);
	}

	/**
	 * @api {get} /applications/all 查询所有应用
	 * @apiName queryAllApplications
	 * @apiGroup Application
	 * @apiSuccess (Success) {Boolean} success 成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object[]} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 应用的唯一标识（UUID 无业务语义）
	 * @apiSuccess (Success) {String} payload.systemCode 应用代码（spring.application.name）
	 * @apiSuccess (Success) {String} payload.createTime 创建时间
	 * @apiSuccess (Success) {String} payload.owner 负责人的 username
	 * @apiSuccess (Success) {String} payload.remark 备注
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/applications/all", method = RequestMethod.GET)
	public Result<List<Application>> findAll() {
		return Result.newResult(applicationRepository.findAll());
	}

	private void validate(Application application) {
		boolean exists = applicationRepository.existsByNameOrSystemCode(application.getName(),
				application.getSystemCode());
		Assert.isTrue(!exists, ErrorCodes.DUPLICATED_CONFIG);
	}
}
