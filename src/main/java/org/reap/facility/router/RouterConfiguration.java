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

package org.reap.facility.router;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.reap.facility.domain.RouteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过数据库表来进行 Zuul 的初始化工作，并且支持动态刷新.
 */
@Configuration
public class RouterConfiguration {

	@Autowired
	private ZuulProperties zuulProperties;

	@Autowired
	private ServerProperties server;

	@Autowired
	private RouteRepository routeRepository;

	@Bean
	public CustomRouteLocator routeLocator() {
		CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServlet().getServletPrefix(),
				this.zuulProperties);
		routeLocator.setRouteRepository(routeRepository);
		return routeLocator;
	}

	class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

		private RouteRepository routeRepository;

		public CustomRouteLocator(String servletPath, ZuulProperties properties) {
			super(servletPath, properties);
		}

		@Override
		public void refresh() {
			doRefresh();
		}

		@Override
		protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
			List<org.reap.facility.domain.Route> routers = routeRepository.findAll();
			LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
			routesMap.putAll(super.locateRoutes());
			routesMap.putAll(routers.stream().collect(
					Collectors.toMap(org.reap.facility.domain.Route::getPath, r -> buildRoute(r))));
			return routesMap;
		}

		private ZuulRoute buildRoute(org.reap.facility.domain.Route router) {
			ZuulRoute zuulRoute = new ZuulRoute();
			BeanUtils.copyProperties(router, zuulRoute);
			return zuulRoute;
		}

		public void setRouteRepository(RouteRepository routeRepository) {
			this.routeRepository = routeRepository;
		}

	}
}
