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

package org.reap.facility.vo.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.reap.facility.common.Constants;
import org.springframework.util.CollectionUtils;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;

/**
 * 应用运行状态.
 * 
 * @author 7cat
 * @since 1.0
 */
public class RuntimeInformation {

	private List<Instance> instants = new ArrayList<>();

	public RuntimeInformation(Application application) {
		if (null == application) {
			return;
		}
		if (null != application.getInstances()) {
			instants = application.getInstances().stream().map(i -> {
				Instance instance = new Instance();
				instance.setHomePageUrl(i.getHomePageUrl());
				instance.setIpAddr(i.getIPAddr());
				instance.setLastUpdatedTimestamp(new Date(i.getLastUpdatedTimestamp()));
				instance.setLastDirtyTimestamp(new Date(i.getLastDirtyTimestamp()));
				instance.setStatus(i.getStatus());
				return instance;
			}).collect(Collectors.toList());
		}
	}

	public List<Instance> getInstants() {
		return instants;
	}

	/**
	 * 返回应用的状态
	 */
	public String getStatus() {
		if (instants.size() == 0) {
			return InstanceInfo.InstanceStatus.UNKNOWN.name();
		}
		else {
			boolean notHealth = instants.stream().anyMatch(i -> {
				return i.getStatus() != InstanceInfo.InstanceStatus.UP;
			});
			if (notHealth) {
				return "WARNING";
			}
			else {
				return InstanceInfo.InstanceStatus.UP.name();
			}
		}
	}

	public String getApiDocUrl() {
		if (!CollectionUtils.isEmpty(instants)) {
			return instants.get(0).getHomePageUrl() + Constants.API_DOC_URL_PREFIX;
		}
		return null;
	}

	public void setInstants(List<Instance> instants) {
		this.instants = instants;
	}
}
