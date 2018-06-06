# reap-facility 

*reap-facility* 提供了*REAP* (Reactive Enterprise Application Platform) 的基础设施服务，分别包括*服务注册中心*、*服务参数中心*、*服务路由网关*。REAP 其他子系统通过连接*reap-facility*获取基础设施服务。

## 服务注册中心

REAP 所有子系统（*含自身*）作为一个服务实例注册在 **服务注册中心** 中，用于实现所有微服务之间的自动化注册与发现。

## 分布式配置中心

为了方便服务配置文件统一管理，*REAP*提供了**分布式配置中心**，将散乱，无需的配置文件统一管理起来（*REAP使用库表进行进行配置文件存储*）。

## 服务路由网关

微服务架构在角色上一般分为内部服务与外部服务，外部服务暴露出去，供其他系统调用（*前端页面*），内部服务一般供内部架构调用，路由是微服务架构中不可或缺的一部分，*REAP* 实现了路由网关功能，通过配置化的前端页面方便用户动态配置路由信息。

## 服务鉴权中心

**reap-facility**作为系统统一鉴权中心，用于针对所有的外部请求进行处理，解决鉴权重复的问题，使业务结点本身只关心实现自己的业务，将对权限的处理抽离到上层。

## 接口文档

### API

#### 参数新增
* **URL**

    /config

* **Method:**
  
    POST 
  
*  **URL Params**

    None

* **Data Params**

```javascript
{
  application: '归属服务',
  profile: '配置文件',//prd、dev、test
  label: '标签'，//default
  name:  '参数名称'
  value: '参数内容'
}
```

* **Success Response:**
 
 创建成功后，返回新创建的参数实体。 
 
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000',
    payload: {
      application: '归属服务',
  	  profile: '配置文件',//prd、dev、test
      label: '标签'，//default
      name:  '参数名称'
      value: '参数内容'    
      }
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
#### 参数删除
* **URL**

    /config

* **Method:**
  
    DELETE 
  
*  **URL Params**

    id

* **Data Params**

	none	

* **Success Response:**
  
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000'
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
#### 参数更新
* **URL**

    /config

* **Method:**
  
    PUT 
  
*  **URL Params**

    None

* **Data Params**

```javascript
{
  id: '当前参数Id',//必输
  //如下为可修改的信息
  application: '归属服务',
  profile: '配置文件',//prd、dev、test
  label: '标签'，//default
  name:  '参数名称'
  value: '参数内容'
}
```

* **Success Response:**
 
 更新成功后，返回更新后的参数实体。 
 
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000',
    payload: {
      application: '归属服务',
  	   profile: '配置文件',//prd、dev、test
      label: '标签'，//default
      name:  '参数名称'
      value: '参数内容'    
      }
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
#### 查找参数


* **URL**

    /configs?page=:page&size=:size&name=:name&code=:code

* **Method:**
  
    GET
  
*  **URL Params**

    - page 页码  Integer  Required    从0开始
    - size 每页条数   Integer Required 
    - application 系统名称  String Optional 模糊匹配
    - profile 参数级别 String Optional 模糊匹配
    - label 标签 String Optional 模糊匹配
    - name 参数名 String Optional 模糊匹配
    - value 参数值 String Optional 模糊匹配
 
* **Data Params**

    None

* **Success Response:**
  
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000',
    payload: {
      // 机构记录
      content: [
        {
          "id": "id值",
          "application":"系统名称",
          "profile":"参数profile",
          "label": "标签",
          "name": "参数名",
          "value": "参数值",
        }
      ],
    // 总页数
    totalPages: 1,
    // 总记录数
    totalElements: 1,
    // 当前页码
    number: 0,
    // 每页条数
    size: 10,
    // 当前查询记录数
    numberOfElements: 1
  },
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
#### 路由新增
* **URL**

    /route

* **Method:**
  
    POST 
  
*  **URL Params**

    None

* **Data Params**

```javascript
{
  name: '路由名称',
  path: '路由路径',//支持统配
  serviceId: 'serviceId'，
  url:  '完整url路径',//默认无需输入
  stripPrefix: '移除路由前缀标示',//可无需输入，默认为true
}
```

* **Success Response:**
 
 创建成功后，返回新创建的参数实体。 
 
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000',
    payload: {
      name: '路由名称',
      path: '路由路径',//支持统配
      serviceId: 'serviceId'，
      url:  '完整url路径',//无需输入
      stripPrefix: '移除路由前缀标示',//无需输入，默认为true   
      }
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
#### 路由删除
* **URL**

    /route

* **Method:**
  
    DELETE 
  
*  **URL Params**

    id

* **Data Params**

	none	

* **Success Response:**
  
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000'
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```

#### 路由更新
* **URL**

    /route

* **Method:**
  
    PUT 
  
*  **URL Params**

    None

* **Data Params**

```javascript
{
  id: '当前路由Id',//必输
  //如下为可修改的信息
  name: '路由名称',
  path: '路由路径',//支持统配
  serviceId: 'serviceId'，
  url:  '完整url路径',//默认无需输入
  stripPrefix: '移除路由前缀标示',//可无需输入，默认为true 
}
```

* **Success Response:**
 
 更新成功后，返回新创建的参数实体。 
 
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000',
    payload: {
      id: '当前路由Id',//必输
      //如下为可修改的信息
      name: '路由名称',
      path: '路由路径',//支持统配
      serviceId: 'serviceId'
      url:  '完整url路径',//默认无需输入
      stripPrefix: '移除路由前缀标示',
   				}
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
#### 路由查找


* **URL**

    /routes?page=:page&size=:size&name=:name&code=:code

* **Method:**
  
    GET
  
*  **URL Params**

    - page 页码  Integer  Required    从0开始
    - size 每页条数   Integer Required 
    - name 路由名称  String Optional 模糊匹配
    - path 路由路径 String Optional 模糊匹配
    - serviceId 服务ID String Optional 模糊匹配
 
* **Data Params**

    None

* **Success Response:**
  
```javascript
  {
    status: 1,
    success: true,
    responseCode: 'SC0000',
    payload: {
      // 机构记录
      content: [
        {
          id: '当前路由Id',//必输
          //如下为可修改的信息
          name: '路由名称',
          path: '路由路径',//支持统配
          serviceId: 'serviceId',
          url:  '完整url路径',//默认无需输入
          stripPrefix: '移除路由前缀标示',
        }
      ],
    // 总页数
    totalPages: 1,
    // 总记录数
    totalElements: 1,
    // 当前页码
    number: 0,
    // 每页条数
    size: 10,
    // 当前查询记录数
    numberOfElements: 1
  },
  }
```
 
* **Error Response:**

```javascript
  {
    status: 0,
    success: false,
    responseCode: '错误码',
    responseMessage: '具体错误消息',
  }
```
