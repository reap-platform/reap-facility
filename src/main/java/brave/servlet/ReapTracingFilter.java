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

package brave.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brave.Span;
import brave.SpanCustomizer;
import brave.Tracer;
import brave.Tracing;
import brave.http.HttpServerHandler;
import brave.http.HttpTracing;
import brave.propagation.Propagation.Getter;
import brave.propagation.TraceContext;

/**
 * TODO 利用 hack 的方式来实现了屏蔽掉 Zuul&Eureka 的信息，后续如果有更好的方法进行重构
 * 
 * @author 7cat
 * @since 1.0
 */
public class ReapTracingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ReapTracingFilter.class);
	
	static final Getter<HttpServletRequest, String> GETTER = new Getter<HttpServletRequest, String>() {

		@Override
		public String get(HttpServletRequest carrier, String key) {
			return carrier.getHeader(key);
		}

		@Override
		public String toString() {
			return "HttpServletRequest::getHeader";
		}
	};

	static final HttpServletAdapter ADAPTER = new HttpServletAdapter();

	public static Filter create(Tracing tracing) {
		return new ReapTracingFilter(HttpTracing.create(tracing));
	}

	public static Filter create(HttpTracing httpTracing) {
		return new ReapTracingFilter(httpTracing);
	}

	final ServletRuntime servlet = ServletRuntime.get();

	final Tracer tracer;

	final HttpServerHandler<HttpServletRequest, HttpServletResponse> handler;

	final TraceContext.Extractor<HttpServletRequest> extractor;

	ReapTracingFilter(HttpTracing httpTracing) {
		tracer = httpTracing.tracing().tracer();
		handler = HttpServerHandler.create(httpTracing, ADAPTER);
		extractor = httpTracing.tracing().propagation().extractor(GETTER);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = servlet.httpResponse(response);

		String uri = httpRequest.getRequestURI();

		logger.info("Current Request Uri is:"+httpRequest.getRequestURI());
		// zuul 转发以及 eureka 信息不需要登记 span
		if (uri.startsWith("/apis") || uri.startsWith("/ui") || uri.startsWith("/eureka")) {
			chain.doFilter(request, response);
			return;
		}

		// Prevent duplicate spans for the same request
		if (request.getAttribute("TracingFilter") != null) {
			chain.doFilter(request, response);
			return;
		}

		request.setAttribute("TracingFilter", "true");

		Span span = handler.handleReceive(extractor, httpRequest);

		// Add attributes for explicit access to customization or span context
		request.setAttribute(SpanCustomizer.class.getName(), span.customizer());
		request.setAttribute(TraceContext.class.getName(), span.context());

		Throwable error = null;
		try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
			// any downstream code can see Tracer.currentSpan() or use Tracer.currentSpanCustomizer()
			chain.doFilter(httpRequest, httpResponse);
		}
		catch (IOException | ServletException | RuntimeException | Error e) {
			error = e;
			throw e;
		}
		finally {
			if (servlet.isAsync(httpRequest)) { // we don't have the actual response, handle later
				servlet.handleAsync(handler, httpRequest, span);
			}
			else { // we have a synchronous response, so we can finish the span
				handler.handleSend(ADAPTER.adaptResponse(httpRequest, httpResponse), error, span);
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

}
