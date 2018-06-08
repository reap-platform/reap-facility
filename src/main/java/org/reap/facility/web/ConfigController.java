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

	/**
	 * @api {post} /config 创建参数
	 * @apiName 创建参数
	 * @apiGroup config
	 * @apiParam {String} application 归属系统
	 * @apiParam {String} profile 参数生效环境
	 * @apiParam {String} label 参数标签
	 * @apiParam {String} name 参数名
	 * @apiParam {String} value 参数值
	 * @apiSuccess {Boolean} success 业务成功标识 <code>true</code>
	 * @apiSuccess {Object} responseCode 响应码 'SC0000'
	 * @apiSuccess {Object} payload 响应数据
	 * @apiSuccess {String} payload.id 新建参数 ID
	 * @apiSuccess {String} payload.application 归属应用
	 * @apiSuccess {String} payload.profile 参数生效环境
	 * @apiSuccess {String} payload.label 参数标签
	 * @apiSuccess {String} payload.name 参数名
	 * @apiSuccess {String} payload.value 参数值
	 * @apiErrorExample {json} Error-Response: 
	 * 
	 *    { 
	 *      "success": false, 
	 *      "responseCode": "具体错误码", 
	 *      "responseMessage": "具体错误消息" 
	 *    }
	 */
	@RequestMapping(path = "/config", method = RequestMethod.POST)
	public Result<Config> create(@RequestBody Config config) {
		validate(config);
		return DefaultResult.newResult(configRepository.save(config));
	}

	@RequestMapping(path = "/config/{id}", method = RequestMethod.DELETE)
	public Result<?> delete(@PathVariable String id) {
		configRepository.deleteById(id);
		return DefaultResult.newResult();
	}

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
