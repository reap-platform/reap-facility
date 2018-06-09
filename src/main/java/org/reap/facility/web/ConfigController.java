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
import org.reap.facility.domain.Config;
import org.reap.facility.domain.ConfigRepository;
import org.reap.facility.vo.QueryConfigSpec;
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
 * 机构维护相关的服务.
 * 
 * @author 7cat
 * @since 1.0
 */
@RestController
public class ConfigController {

	@Autowired
	private ConfigRepository configRepository;

	/** @apiDefine Config 参数维护 */
	/** @apiDefine createConfig 创建参数 */
	/** @apiDefine deleteConfig 删除参数 */
	/** @apiDefine queryConfig 查询参数 */
	/** @apiDefine updateConfig 修改参数 */

	/**
	 * @api {post} /config 创建参数
	 * @apiName createConfig
	 * @apiGroup Config
	 * @apiParam (Body) {String} application 归属系统
	 * @apiParam (Body) {String} profile 参数生效环境
	 * @apiParam (Body) {String} label 参数标签
	 * @apiParam (Body) {String} name 参数名
	 * @apiParam (Body) {String} value 参数值
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 新建参数 id
	 * @apiSuccess (Success) {String} payload.application 归属应用
	 * @apiSuccess (Success) {String} payload.profile 参数生效环境
	 * @apiSuccess (Success) {String} payload.label 参数标签
	 * @apiSuccess (Success) {String} payload.name 参数名
	 * @apiSuccess (Success) {String} payload.value 参数值
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/config", method = RequestMethod.POST)
	public Result<Config> create(@RequestBody Config config) {
		validate(config);
		return DefaultResult.newResult(configRepository.save(config));
	}

	/**
	 * @api {delete} /config/:id 删除参数
	 * @apiName deleteConfig
	 * @apiGroup Config
	 * @apiParam (PathVariable) {String} id 参数 id
	 * @apiSuccess (Success) {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/config/{id}", method = RequestMethod.DELETE)
	public Result<?> delete(@PathVariable String id) {
		configRepository.deleteById(id);
		return DefaultResult.newResult();
	}

	/**
	 * @api {put} /config 更新参数
	 * @apiName updateConfig
	 * @apiGroup Config
	 * @apiParam (Body) {String} id 参数 id
	 * @apiParam (Body) {String} application 归属系统
	 * @apiParam (Body) {String} profile 参数生效环境
	 * @apiParam (Body) {String} label 参数标签
	 * @apiParam (Body) {String} name 参数名
	 * @apiParam (Body) {String} value 参数值
	 * @apiSuccess (Success) {Boolean} success 成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {String} payload.id 参数 id
	 * @apiSuccess (Success) {String} payload.application 归属应用
	 * @apiSuccess (Success) {String} payload.profile 参数生效环境
	 * @apiSuccess (Success) {String} payload.label 参数标签
	 * @apiSuccess (Success) {String} payload.name 参数名
	 * @apiSuccess (Success) {String} payload.value 参数值
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/config", method = RequestMethod.PUT)
	public Result<Config> update(@RequestBody Config config) {
		Config persisted = FunctionalUtils.orElseThrow(configRepository.findById(config.getId()),
				ErrorCodes.CONFIG_NOT_EXIST);
		persisted.setApplication(config.getApplication());
		persisted.setProfile(config.getProfile());
		persisted.setLabel(config.getLabel());
		persisted.setName(config.getName());
		persisted.setValue(config.getValue());
		return DefaultResult.newResult(configRepository.save(persisted));
	}

	/**
	 * @api {get} /configs 查询参数
	 * @apiName queryConfig
	 * @apiGroup Config
	 * @apiParam (QueryString) {Number} [page=0] 页码
	 * @apiParam (QueryString) {Number} [size=10] 每页记录数
	 * @apiParam (QueryString) {String} [application] 归属应用，模糊匹配
	 * @apiParam (QueryString) {String} [profile] 归属环境，模糊匹配
	 * @apiParam (QueryString) {String} [label] 参数标签，模糊匹配
	 * @apiParam (QueryString) {String} [name] 参数名，模糊匹配
	 * @apiParam (QueryString) {String} [value] 参数值，模糊匹配
	 * @apiSuccess (Success) {Boolean} success 成功标识 <code>true</code>
	 * @apiSuccess (Success) {String} responseCode 响应码 'SC0000'
	 * @apiSuccess (Success) {Object} payload 响应数据
	 * @apiSuccess (Success) {Number} payload.totalPages 总页数
	 * @apiSuccess (Success) {Number} payload.totalElements 总记录数
	 * @apiSuccess (Success) {Number} payload.numberOfElements 当前记录数
	 * @apiSuccess (Success) {Object[]} payload.content 参数标签
	 * @apiSuccess (Success) {String} payload.content.id 参数 id
	 * @apiSuccess (Success) {String} payload.content.application 归属应用
	 * @apiSuccess (Success) {String} payload.content.profile 参数生效环境
	 * @apiSuccess (Success) {String} payload.content.label 参数标签
	 * @apiSuccess (Success) {String} payload.content.name 参数名
	 * @apiSuccess (Success) {String} payload.content.value 参数值
	 * @apiError (Error) {Boolean} success 业务成功标识 <code>false</code>
	 * @apiError (Error) {String} responseCode 错误码
	 * @apiError (Error) {String} responseMessage 错误消息
	 */
	@RequestMapping(path = "/configs", method = RequestMethod.GET)
	public Result<Page<Config>> find(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE) int size, QueryConfigSpec spec) {
		return DefaultResult.newResult(configRepository.findAll(spec.toSpecification(), PageRequest.of(page, size)));
	}

	private void validate(Config config) {
		boolean exists = configRepository.existsByApplicationAndProfileAndLabelAndName(config.getApplication(),
				config.getProfile(), config.getLabel(), config.getName());
		Assert.isTrue(!exists, ErrorCodes.DUPLICATED_CONFIG);
	}
}
