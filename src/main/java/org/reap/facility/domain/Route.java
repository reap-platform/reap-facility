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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * REAP 路由配置实体.
 * 
 * @author xyzadmin
 *
 */
@Entity
public class Route {

	@Id
	@GeneratedValue
	private String id;

	/**
	 * The router name, e.g. reap-rbac.
	 */
	private String name;

	/**
	 * The path (pattern) for the route, e.g. /foo/**.
	 */
	private String path;

	/**
	 * The service ID (if any) to map to this route. You can specify a physical URL or a service, but not both.
	 */
	private String systemCode;

	/**
	 * A full physical URL to map to the route. An alternative is to use a service ID and service discovery to find the
	 * physical address.
	 */
	private String url;

	/**
	 * Flag to determine whether the prefix for this route (the path, minus pattern patcher) should be stripped before
	 * forwarding.
	 */
	private String stripPrefix;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSystemCode() {
		return systemCode;
	}

	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStripPrefix() {
		return stripPrefix;
	}

	public void setStripPrefix(String stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public String getServiceId() {
		return getSystemCode();
	}
	
	@Override
	public String toString() {
		return "Route [id=" + id + ", name=" + name + ", path=" + path + ", systemCode=" + systemCode + ", url=" + url
				+ ", stripPrefix=" + stripPrefix + "]";
	}
}
