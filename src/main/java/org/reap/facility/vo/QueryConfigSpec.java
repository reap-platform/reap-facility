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
import org.reap.facility.domain.Config;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 * {@link Config} 实体检索条件值对象.
 * 
 * @author 7cat
 * @since 1.0
 */
public class QueryConfigSpec {

	private String application;

	private String profile;

	private String label;

	private String name;

	private String value;

	public Specification<Config> toSpecification() {

		return new Specification<Config>() {

			@Override
			public Predicate toPredicate(Root<Config> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();

				if (StringUtils.hasText(getApplication())) {
					predicate.add(cb.like(root.get(Fields.APPLICATION), "%" + getApplication() + "%"));
				}

				if (StringUtils.hasText(getLabel())) {
					predicate.add(cb.like(root.get(Fields.LABEL), "%" + getLabel() + "%"));
				}

				if (StringUtils.hasText(getProfile())) {
					predicate.add(cb.like(root.get(Fields.PROFILE), "%" + getProfile() + "%"));
				}

				if (StringUtils.hasText(getName())) {
					predicate.add(cb.like(root.get(Fields.NAME), "%" + getName() + "%"));
				}

				if (StringUtils.hasText(getValue())) {
					predicate.add(cb.like(root.get(Fields.VALUE), "%" + getValue() + "%"));
				}
				query.where(predicate.toArray(new Predicate[predicate.size()]));
				return query.getRestriction();
			}

		};

	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
