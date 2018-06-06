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

package org.reap.facility.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.reap.facility.common.Fields;
import org.reap.facility.domain.Route;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 * {@link Route} 实体检索条件值对象.
 * 
 * @author 7cat
 * @since 1.0
 */
public class QueryRouteSpec {

	private String name;

	private String path;

	private String serviceId;

	private String url;

	private String stripPrefix;

	public Specification<Route> toSpecification() {

		return new Specification<Route>() {

			@Override
			public Predicate toPredicate(Root<Route> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if (StringUtils.hasText(getName())) {
					predicate.add(cb.like(root.get(Fields.APPLICATION), "%" + getName() + "%"));
				}

				if (StringUtils.hasText(getPath())) {
					predicate.add(cb.like(root.get(Fields.LABEL), "%" + getPath() + "%"));
				}

				if (StringUtils.hasText(getServiceId())) {
					predicate.add(cb.like(root.get(Fields.PROFILE), "%" + getServiceId() + "%"));
				}

				if (StringUtils.hasText(getUrl())) {
					predicate.add(cb.like(root.get(Fields.NAME), "%" + getUrl() + "%"));
				}

				if (StringUtils.hasText(getStripPrefix())) {
					predicate.add(cb.like(root.get(Fields.VALUE), "%" + getStripPrefix() + "%"));
				}
				query.where(predicate.toArray(new Predicate[predicate.size()]));
				return query.getRestriction();
			}

		};

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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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
}
