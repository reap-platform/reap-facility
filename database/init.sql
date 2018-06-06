-- 参数
INSERT INTO CONFIG VALUES('reap.prd.default.server.logging.path','reap','prd','default','logging.path','/tmp');
INSERT INTO CONFIG VALUES('reap.prd.default.server.compression.enable','reap','prd','default','server.compression.enable','true');
INSERT INTO CONFIG VALUES('reap.prd.default.server.compression.enable','reap','prd','default','server.compression.enable','true');
INSERT INTO CONFIG VALUES('reap.prd.default.server.compression.mime-types','reap','prd','default','server.compression.mime-types','pplication/json,application/xml,text/html,text/xml,text/plain,application/javascript,text/css');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.resources.static-locations', 'reap', 'prd', 'default','spring.resources.static-locations','file:apps/${spring.application.name}/static,classpath:static');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.datasource.url', 'reap', 'prd', 'default', 'spring.datasource.url', 'jdbc:mysql://119.29.92.158/reapdb');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.datasource.username', 'reap', 'prd', 'default', 'spring.datasource.username', 'reap');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.datasource.password', 'reap', 'prd', 'default', 'spring.datasource.password', 'reap');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.datasource.driver-class-name', 'reap', 'prd', 'default', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.jpa.open-in-view', 'reap', 'prd', 'default', 'spring.jpa.open-in-view', 'true');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.jpa.show-sql', 'reap', 'prd', 'default', 'spring.jpa.show-sql', 'true');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.jpa.properties.hibernate.format_sql', 'reap', 'prd', 'default', 'spring.jpa.properties.hibernate.format_sql', 'true');
INSERT INTO CONFIG VALUES('reap.prd.default.spring.messages.basename', 'reap', 'prd', 'default', 'spring.messages.basename', 'i18n/messages');
INSERT INTO CONFIG VALUES('reap.prd.default.eureka.client.initial-instance-info-replication-interval-seconds','reap','prd','default','eureka.client.initial-instance-info-replication-interval-seconds','10');
INSERT INTO CONFIG VALUES('reap.prd.default.eureka.client.instance-info-replication-interval-seconds','reap','prd','default','eureka.client.instance-info-replication-interval-seconds','10');

INSERT INTO CONFIG VALUES('reap-facility.prd.default.server.port','reap-facility','prd','default','server.port','8761');

-- 路由规则
INSERT INTO ROUTE VALUES('reap','reap','/reap/**','reap-portal',null,null); -- 统一 UI路由
INSERT INTO ROUTE VALUES('reap-facility','reap-facility','/apis/reap-facility/**','reap-facility',null,null);
INSERT INTO ROUTE VALUES('reap-facility-ui','reap-facility-ui','/ui/reap-facility/**','reap-facility',null,null);

-- 功能码
INSERT INTO FUNCTION (ID,SERVICE_ID,CODE,NAME,TYPE,ACTION) VALUES ('REAPFA0001','reap-facility','REAPFA0001','参数维护','M',null);
INSERT INTO FUNCTION (ID,SERVICE_ID,CODE,NAME,TYPE,ACTION) VALUES ('REAPFA0002','reap-facility','REAPFA0002','路由维护','M',null);
