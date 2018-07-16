DELETE FROM REAP_FACILITY.APPLICATION;
INSERT INTO REAP_FACILITY.APPLICATION(ID, NAME, SYSTEM_CODE,OWNER,REMARK) VALUES('reap-portal', 'UI 门户', 'reap-portal', 'admin', '');
INSERT INTO REAP_FACILITY.APPLICATION(ID, NAME, SYSTEM_CODE,OWNER,REMARK) VALUES('reap-rbac', '用户管理服务', 'reap-rbac', 'admin', '');
INSERT INTO REAP_FACILITY.APPLICATION(ID, NAME, SYSTEM_CODE,OWNER,REMARK) VALUES('reap-facility', '基础设备服务', 'reap-facility', 'admin', '');

DELETE FROM REAP_FACILITY.CONFIG;
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.l.path','reap','prd','default','logging.file','${REAP_HOME:.}/logs/${spring.application.name}/app.log');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.l.l.root','reap','prd','default','logging.level.root','INFO');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.l.l.o.s.web','reap','prd','default','logging.level.org.springframework.web','DEBUG');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.l.l.o.h.SQL','reap','prd','default','logging.level.org.hibernate.SQL','DEBUG');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.l.l.o.h.t.d.sql','reap','prd','default','logging.level.org.hibernate.type.descriptor.sql','DEBUG');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.z.baseUrl','reap','prd','default','spring.zipkin.baseUrl','http://reap-tracing');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r.p.d.s.s.w.skipPattern', 'reap', 'prd', 'default', 'spring.sleuth.web.skipPattern', '/|/api-docs.*|/autoconfig|/configprops|/dump|/health|/info|/metrics.*|/mappings|/trace|/swagger.*|.*\.png|.*\.css|.*\.js|.*\.woff|.*\.html|/favicon.ico|/hystrix.stream|/application/.*');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.s.s.probability','reap','prd','default','spring.sleuth.sampler.probability','1.0');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.c.enable','reap','prd','default','server.compression.enable','true');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.c.mime-types','reap','prd','default','server.compression.mime-types','aplication/json,application/xml,text/html,text/xml,text/plain,application/javascript,text/css');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.r.static-locations', 'reap', 'prd', 'default','spring.resources.static-locations','file:apps/${spring.application.name}/static,classpath:static');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.d.url', 'reap', 'prd', 'default', 'spring.datasource.url', 'jdbc:h2:tcp://localhost/~/REAPDB');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.d.username', 'reap', 'prd', 'default', 'spring.datasource.username', 'reap');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.d.password', 'reap', 'prd', 'default', 'spring.datasource.password', 'reap');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.d.driver-class-name', 'reap', 'prd', 'default', 'spring.datasource.driver-class-name', 'org.h2.Driver');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.j.open-in-view', 'reap', 'prd', 'default', 'spring.jpa.open-in-view', 'true');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.j.show-sql', 'reap', 'prd', 'default', 'spring.jpa.show-sql', 'true');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.j.h.ddl-auto', 'reap', 'prd', 'default', 'spring.jpa.hibernate.ddl-auto', 'none');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.s.m.basename', 'reap', 'prd', 'default', 'spring.messages.basename', 'i18n/messages');
-- 应用启动后第一次向 eureka 注册的时间（单位秒）
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.e.c.i-i-i-r-i-s','reap','prd','default','eureka.client.initial-instance-info-replication-interval-seconds','5');
-- 应用启动后向 eureka 注册的周期（单位秒）
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.e.c.i-i-r-i-s','reap','prd','default','eureka.client.instance-info-replication-interval-seconds','5');
-- 应用客户端
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.e.c.r-f-r-i-s','reap','prd','default','eureka.client.registry-fetch-interval-seconds','5');

INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.m.e.w.e.include','reap','prd','default','management.endpoints.web.exposure.include','*');
----统一参数（可选）
-- INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.r.l.l.destinations','reap','prd','default','reap.logging.logstash.destinations','47.95.243.70:5602');
-- INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r.p.d.r.l.l.b.size','reap','prd','default','reap.logging.logstash.buffer.size','0');
----应用参数
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r-f.p.d.s.p','reap-facility','prd','default','server.port','8761');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r-f.p.d.s.s.z.enabled','reap-facility','prd','default','spring.sleuth.zuul.enabled','false');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r-f.p.d.s.s.w.additionalSkipPattern', 'reap-facility', 'prd', 'default', 'spring.sleuth.web.additionalSkipPattern', '/eureka.*');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r-t.p.d.s.port', 'reap-tracing', 'prd', 'default', 'server.port', '9411');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r-t.p.d.s.s.h.enabled','reap-tracing','prd','default','spring.sleuth.http.enabled','false');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES('r-t.p.d.s.s.w.enabled','reap-tracing','prd','default','spring.sleuth.web.enabled','false');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r-r.p.d.s.port','reap-rbac','prd','default','server.port','8070');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r-r.p.d.p.m.salt','reap-rbac','prd','default','password.md5.salt', 'reap');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r-p.p.d.s.port', 'reap-portal', 'prd', 'default', 'server.port', '8081');
INSERT INTO REAP_FACILITY.CONFIG (ID, SYSTEM_CODE, PROFILE, LABEL, NAME, VALUE) VALUES ('r-p.p.d.t.key', 'reap-portal', 'prd', 'default', 'token.key', '123456');
