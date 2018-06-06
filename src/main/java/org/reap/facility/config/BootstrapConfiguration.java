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

package org.reap.facility.config;

import javax.sql.DataSource;

import org.reap.facility.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentProperties;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentRepositoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author 7cat
 * @since 1.0
 */
@Configuration
public class BootstrapConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create().build();
		dataSource.setJdbcUrl(environment.getProperty("reap-config.datasource.url"));
		dataSource.setUsername(environment.getProperty("reap-config.datasource.username"));
		dataSource.setPassword(environment.getProperty("reap-config.datasource.password"));
		dataSource.setDriverClassName(environment.getProperty("reap-config.datasource.driver-class-name"));
		return dataSource;
	}

	@Bean
	public EnvironmentRepository defaultEnvironmentRepository(DataSource dataSource) {
		String configSql = environment.getProperty("reap-config.jdbc.sql");
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		JdbcEnvironmentProperties properties = new JdbcEnvironmentProperties();
		properties.setSql(configSql != null ? configSql : Constants.DEFAULT_CONFIG_EXTRACT_SQL);
		JdbcEnvironmentRepositoryFactory factory = new JdbcEnvironmentRepositoryFactory(jdbcTemplate);
		return factory.build(properties);
	}
}
