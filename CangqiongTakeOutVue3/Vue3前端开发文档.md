# 天穹外卖系统接口文档


**简介**:天穹外卖系统接口文档


**HOST**:http://localhost:8080


**联系人**:小白开发者


**Version**:v1.0


**接口路径**:/v3/api-docs


[TOC]






# 测试接口


## 测试数据库


**接口地址**:`/test/db`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>通过 ID 查询 admin 用户</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultEmployee|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||Employee|Employee|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;username||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;password||string||
|&emsp;&emsp;phone||string||
|&emsp;&emsp;sex||string||
|&emsp;&emsp;idNumber||string||
|&emsp;&emsp;status||integer(int32)||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;createUser||integer(int64)||
|&emsp;&emsp;updateUser||integer(int64)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"username": "",
		"name": "",
		"password": "",
		"phone": "",
		"sex": "",
		"idNumber": "",
		"status": 0,
		"createTime": "",
		"updateTime": "",
		"createUser": 0,
		"updateUser": 0
	}
}
```


## 欢迎接口


**接口地址**:`/hello`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>返回系统的第一声问候</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


# C端-订单接口


## 订单支付（模拟）


**接口地址**:`/user/order/payment`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "orderNumber": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ordersPaymentDTO|订单支付请求模型|body|true|OrdersPaymentDTO|OrdersPaymentDTO|
|&emsp;&emsp;orderNumber|订单号||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 用户取消订单


**接口地址**:`/user/order/cancel/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 用户下单


**接口地址**:`/user/order/submit`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "addressBookId": 0,
  "payMethod": 0,
  "remark": "",
  "amount": 0,
  "packAmount": 0,
  "tablewareNumber": 0,
  "tablewareStatus": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ordersSubmitDTO|下单请求模型|body|true|OrdersSubmitDTO|OrdersSubmitDTO|
|&emsp;&emsp;addressBookId|地址簿id||false|integer(int64)||
|&emsp;&emsp;payMethod|支付方式 1微信 2支付宝||false|integer(int32)||
|&emsp;&emsp;remark|备注||false|string||
|&emsp;&emsp;amount|总金额||false|number||
|&emsp;&emsp;packAmount|打包费||false|integer(int32)||
|&emsp;&emsp;tablewareNumber|餐具数量||false|integer(int32)||
|&emsp;&emsp;tablewareStatus|餐具状态 0:按餐具数量 1:提供餐具||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultOrderSubmitVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||OrderSubmitVO|OrderSubmitVO|
|&emsp;&emsp;id|订单id|integer(int64)||
|&emsp;&emsp;orderNumber|订单号|string||
|&emsp;&emsp;orderAmount|订单金额|number||
|&emsp;&emsp;orderTime|下单时间|string(date-time)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"orderNumber": "",
		"orderAmount": 0,
		"orderTime": ""
	}
}
```


## 再来一单


**接口地址**:`/user/order/repetition/{id}`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 查询秒杀订单状态


**接口地址**:`/user/order/status/{orderId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderId||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 用户催单


**接口地址**:`/user/order/reminder/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


# C端-地址簿接口


## 查询默认地址


**接口地址**:`/user/addressBook/default`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultAddressBook|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||AddressBook|AddressBook|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userId||integer(int64)||
|&emsp;&emsp;consignee||string||
|&emsp;&emsp;sex||string||
|&emsp;&emsp;phone||string||
|&emsp;&emsp;provinceName||string||
|&emsp;&emsp;cityName||string||
|&emsp;&emsp;districtName||string||
|&emsp;&emsp;detail||string||
|&emsp;&emsp;label||string||
|&emsp;&emsp;isDefault||integer(int32)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"userId": 0,
		"consignee": "",
		"sex": "",
		"phone": "",
		"provinceName": "",
		"cityName": "",
		"districtName": "",
		"detail": "",
		"label": "",
		"isDefault": 0
	}
}
```


## 设置默认地址


**接口地址**:`/user/addressBook/default`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "userId": 0,
  "consignee": "",
  "sex": "",
  "phone": "",
  "provinceName": "",
  "cityName": "",
  "districtName": "",
  "detail": "",
  "label": "",
  "isDefault": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|addressBook|AddressBook|body|true|AddressBook|AddressBook|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;userId|||false|integer(int64)||
|&emsp;&emsp;consignee|||false|string||
|&emsp;&emsp;sex|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;provinceName|||false|string||
|&emsp;&emsp;cityName|||false|string||
|&emsp;&emsp;districtName|||false|string||
|&emsp;&emsp;detail|||false|string||
|&emsp;&emsp;label|||false|string||
|&emsp;&emsp;isDefault|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 新增地址


**接口地址**:`/user/addressBook`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "userId": 0,
  "consignee": "",
  "sex": "",
  "phone": "",
  "provinceName": "",
  "cityName": "",
  "districtName": "",
  "detail": "",
  "label": "",
  "isDefault": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|addressBook|AddressBook|body|true|AddressBook|AddressBook|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;userId|||false|integer(int64)||
|&emsp;&emsp;consignee|||false|string||
|&emsp;&emsp;sex|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;provinceName|||false|string||
|&emsp;&emsp;cityName|||false|string||
|&emsp;&emsp;districtName|||false|string||
|&emsp;&emsp;detail|||false|string||
|&emsp;&emsp;label|||false|string||
|&emsp;&emsp;isDefault|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 查询当前登录用户的所有地址


**接口地址**:`/user/addressBook/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultListAddressBook|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||array|AddressBook|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userId||integer(int64)||
|&emsp;&emsp;consignee||string||
|&emsp;&emsp;sex||string||
|&emsp;&emsp;phone||string||
|&emsp;&emsp;provinceName||string||
|&emsp;&emsp;cityName||string||
|&emsp;&emsp;districtName||string||
|&emsp;&emsp;detail||string||
|&emsp;&emsp;label||string||
|&emsp;&emsp;isDefault||integer(int32)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": [
		{
			"id": 0,
			"userId": 0,
			"consignee": "",
			"sex": "",
			"phone": "",
			"provinceName": "",
			"cityName": "",
			"districtName": "",
			"detail": "",
			"label": "",
			"isDefault": 0
		}
	]
}
```


# 套餐相关接口


## 新增套餐


**接口地址**:`/admin/setmeal`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "categoryId": 0,
  "name": "",
  "price": 0,
  "status": 0,
  "description": "",
  "image": "",
  "setmealDishes": [
    {
      "id": 0,
      "setmealId": 0,
      "dishId": 0,
      "name": "",
      "price": 0,
      "copies": 0
    }
  ]
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|setmealDTO|新增或修改套餐时传递的数据模型|body|true|SetmealDTO|SetmealDTO|
|&emsp;&emsp;id|主键ID（修改时必填）||false|integer(int64)||
|&emsp;&emsp;categoryId|分类id||false|integer(int64)||
|&emsp;&emsp;name|套餐名称||false|string||
|&emsp;&emsp;price|套餐价格||false|number||
|&emsp;&emsp;status|状态 0:停售 1:起售||false|integer(int32)||
|&emsp;&emsp;description|描述信息||false|string||
|&emsp;&emsp;image|图片路径||false|string||
|&emsp;&emsp;setmealDishes|套餐关联的菜品列表||false|array|SetmealDish|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;setmealId|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;price|||false|number||
|&emsp;&emsp;&emsp;&emsp;copies|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 修改套餐


**接口地址**:`/admin/setmeal`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "categoryId": 0,
  "name": "",
  "price": 0,
  "status": 0,
  "description": "",
  "image": "",
  "setmealDishes": [
    {
      "id": 0,
      "setmealId": 0,
      "dishId": 0,
      "name": "",
      "price": 0,
      "copies": 0
    }
  ]
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|setmealDTO|新增或修改套餐时传递的数据模型|body|true|SetmealDTO|SetmealDTO|
|&emsp;&emsp;id|主键ID（修改时必填）||false|integer(int64)||
|&emsp;&emsp;categoryId|分类id||false|integer(int64)||
|&emsp;&emsp;name|套餐名称||false|string||
|&emsp;&emsp;price|套餐价格||false|number||
|&emsp;&emsp;status|状态 0:停售 1:起售||false|integer(int32)||
|&emsp;&emsp;description|描述信息||false|string||
|&emsp;&emsp;image|图片路径||false|string||
|&emsp;&emsp;setmealDishes|套餐关联的菜品列表||false|array|SetmealDish|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;setmealId|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;price|||false|number||
|&emsp;&emsp;&emsp;&emsp;copies|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 批量删除套餐


**接口地址**:`/admin/setmeal`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ids||query|true|array|integer|


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 套餐起售停售


**接口地址**:`/admin/setmeal/status/{status}`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|status||path|true|integer(int32)||
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 根据id查询套餐


**接口地址**:`/admin/setmeal/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultSetmealVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||SetmealVO|SetmealVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;categoryId||integer(int64)||
|&emsp;&emsp;name||string||
|&emsp;&emsp;price||number||
|&emsp;&emsp;status||integer(int32)||
|&emsp;&emsp;description||string||
|&emsp;&emsp;image||string||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;categoryName||string||
|&emsp;&emsp;setmealDishes||array|SetmealDish|
|&emsp;&emsp;&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;setmealId||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name||string||
|&emsp;&emsp;&emsp;&emsp;price||number||
|&emsp;&emsp;&emsp;&emsp;copies||integer(int32)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"categoryId": 0,
		"name": "",
		"price": 0,
		"status": 0,
		"description": "",
		"image": "",
		"updateTime": "",
		"categoryName": "",
		"setmealDishes": [
			{
				"id": 0,
				"setmealId": 0,
				"dishId": 0,
				"name": "",
				"price": 0,
				"copies": 0
			}
		]
	}
}
```


## 套餐分页查询


**接口地址**:`/admin/setmeal/page`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|setmealPageQueryDTO||query|true|SetmealPageQueryDTO|SetmealPageQueryDTO|
|&emsp;&emsp;page|||false|integer(int32)||
|&emsp;&emsp;pageSize|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;categoryId|||false|integer(int32)||
|&emsp;&emsp;status|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultPageResult|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||PageResult|PageResult|
|&emsp;&emsp;total||integer(int64)||
|&emsp;&emsp;records||array|object|


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"total": 0,
		"records": []
	}
}
```


# 订单管理接口


## 拒单


**接口地址**:`/admin/order/rejection`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "rejectionReason": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ordersRejectionDTO|拒单DTO|body|true|OrdersRejectionDTO|OrdersRejectionDTO|
|&emsp;&emsp;id|订单id||false|integer(int64)||
|&emsp;&emsp;rejectionReason|拒单原因||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 派送订单


**接口地址**:`/admin/order/delivery/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 接单


**接口地址**:`/admin/order/confirm`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ordersConfirmDTO|接单DTO|body|true|OrdersConfirmDTO|OrdersConfirmDTO|
|&emsp;&emsp;id|订单id||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 完成订单


**接口地址**:`/admin/order/complete/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 订单搜索


**接口地址**:`/admin/order/conditionSearch`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ordersPageQueryDTO|订单搜索DTO|query|true|OrdersPageQueryDTO|OrdersPageQueryDTO|
|&emsp;&emsp;page|页码||false|integer(int32)||
|&emsp;&emsp;pageSize|每页记录数||false|integer(int32)||
|&emsp;&emsp;number|订单号||false|string||
|&emsp;&emsp;phone|手机号||false|string||
|&emsp;&emsp;status|订单状态||false|integer(int32)||
|&emsp;&emsp;beginTime|开始时间||false|string(date-time)||
|&emsp;&emsp;endTime|结束时间||false|string(date-time)||
|&emsp;&emsp;userId|用户id||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultPageResult|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||PageResult|PageResult|
|&emsp;&emsp;total||integer(int64)||
|&emsp;&emsp;records||array|object|


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"total": 0,
		"records": []
	}
}
```


# 菜品相关接口


## 新增菜品


**接口地址**:`/admin/dish`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "name": "",
  "categoryId": 0,
  "price": 0,
  "image": "",
  "description": "",
  "status": 0,
  "flavors": [
    {
      "id": 0,
      "dishId": 0,
      "name": "",
      "value": ""
    }
  ]
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dishDTO|新增菜品时传递的数据模型|body|true|DishDTO|DishDTO|
|&emsp;&emsp;id|主键值||false|integer(int64)||
|&emsp;&emsp;name|菜品名称||false|string||
|&emsp;&emsp;categoryId|菜品分类ID||false|integer(int64)||
|&emsp;&emsp;price|菜品价格||false|number||
|&emsp;&emsp;image|图片路径||false|string||
|&emsp;&emsp;description|描述信息||false|string||
|&emsp;&emsp;status|状态: 0停售 1起售||false|integer(int32)||
|&emsp;&emsp;flavors|口味列表||false|array|DishFlavor|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;value|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 修改菜品


**接口地址**:`/admin/dish`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "name": "",
  "categoryId": 0,
  "price": 0,
  "image": "",
  "description": "",
  "status": 0,
  "flavors": [
    {
      "id": 0,
      "dishId": 0,
      "name": "",
      "value": ""
    }
  ]
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dishDTO|新增菜品时传递的数据模型|body|true|DishDTO|DishDTO|
|&emsp;&emsp;id|主键值||false|integer(int64)||
|&emsp;&emsp;name|菜品名称||false|string||
|&emsp;&emsp;categoryId|菜品分类ID||false|integer(int64)||
|&emsp;&emsp;price|菜品价格||false|number||
|&emsp;&emsp;image|图片路径||false|string||
|&emsp;&emsp;description|描述信息||false|string||
|&emsp;&emsp;status|状态: 0停售 1起售||false|integer(int32)||
|&emsp;&emsp;flavors|口味列表||false|array|DishFlavor|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId|||false|integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;value|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 菜品批量删除


**接口地址**:`/admin/dish`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ids||query|true|array|integer|


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 菜品起售停售


**接口地址**:`/admin/dish/status/{status}`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|status||path|true|integer(int32)||
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 根据id查询菜品


**接口地址**:`/admin/dish/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultDishVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||DishVO|DishVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;name||string||
|&emsp;&emsp;categoryId||integer(int64)||
|&emsp;&emsp;price||number||
|&emsp;&emsp;image||string||
|&emsp;&emsp;description||string||
|&emsp;&emsp;status||integer(int32)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;categoryName||string||
|&emsp;&emsp;flavors||array|DishFlavor|
|&emsp;&emsp;&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name||string||
|&emsp;&emsp;&emsp;&emsp;value||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"name": "",
		"categoryId": 0,
		"price": 0,
		"image": "",
		"description": "",
		"status": 0,
		"updateTime": "",
		"categoryName": "",
		"flavors": [
			{
				"id": 0,
				"dishId": 0,
				"name": "",
				"value": ""
			}
		]
	}
}
```


## 菜品分页查询


**接口地址**:`/admin/dish/page`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dishPageQueryDTO|菜品分页查询时传递的数据模型|query|true|DishPageQueryDTO|DishPageQueryDTO|
|&emsp;&emsp;page|页码||false|integer(int32)||
|&emsp;&emsp;pageSize|每页记录数||false|integer(int32)||
|&emsp;&emsp;name|菜品名称||false|string||
|&emsp;&emsp;categoryId|分类id||false|integer(int32)||
|&emsp;&emsp;status|状态 0停售 1起售||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultPageResult|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||PageResult|PageResult|
|&emsp;&emsp;total||integer(int64)||
|&emsp;&emsp;records||array|object|


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"total": 0,
		"records": []
	}
}
```


# 分类相关接口


## 新增分类


**接口地址**:`/admin/category`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "type": 0,
  "name": "",
  "sort": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|categoryDTO|CategoryDTO|body|true|CategoryDTO|CategoryDTO|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;type|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;sort|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 修改分类


**接口地址**:`/admin/category`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "id": 0,
  "type": 0,
  "name": "",
  "sort": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|categoryDTO|CategoryDTO|body|true|CategoryDTO|CategoryDTO|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;type|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;sort|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 删除分类


**接口地址**:`/admin/category`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 启用禁用分类


**接口地址**:`/admin/category/status/{status}`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|status||path|true|integer(int32)||
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 分类分页查询


**接口地址**:`/admin/category/page`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dto|分类分页查询传递的数据模型|query|true|CategoryPageQueryDTO|CategoryPageQueryDTO|
|&emsp;&emsp;page|页码||false|integer(int32)||
|&emsp;&emsp;pageSize|每页记录数||false|integer(int32)||
|&emsp;&emsp;name|分类名称||false|string||
|&emsp;&emsp;type|分类类型：1 菜品分类 2 套餐分类||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultPageResult|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||PageResult|PageResult|
|&emsp;&emsp;total||integer(int64)||
|&emsp;&emsp;records||array|object|


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"total": 0,
		"records": []
	}
}
```


## 根据类型查询分类


**接口地址**:`/admin/category/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|type||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultListCategory|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||array|Category|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;type||integer(int32)||
|&emsp;&emsp;name||string||
|&emsp;&emsp;sort||integer(int32)||
|&emsp;&emsp;status||integer(int32)||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;createUser||integer(int64)||
|&emsp;&emsp;updateUser||integer(int64)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": [
		{
			"id": 0,
			"type": 0,
			"name": "",
			"sort": 0,
			"status": 0,
			"createTime": "",
			"updateTime": "",
			"createUser": 0,
			"updateUser": 0
		}
	]
}
```


# C端-用户相关接口


## 微信登录


**接口地址**:`/user/user/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "code": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userLoginDTO|UserLoginDTO|body|true|UserLoginDTO|UserLoginDTO|
|&emsp;&emsp;code|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultUserLoginVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||UserLoginVO|UserLoginVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;openid||string||
|&emsp;&emsp;token||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"openid": "",
		"token": ""
	}
}
```


# C端-购物车接口


## 删除购物车中一个商品


**接口地址**:`/user/shoppingCart/sub`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "dishId": 0,
  "setmealId": 0,
  "dishFlavor": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|shoppingCartDTO|添加购物车时传递的数据模型|body|true|ShoppingCartDTO|ShoppingCartDTO|
|&emsp;&emsp;dishId|菜品id||false|integer(int64)||
|&emsp;&emsp;setmealId|套餐id||false|integer(int64)||
|&emsp;&emsp;dishFlavor|口味||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 添加购物车


**接口地址**:`/user/shoppingCart/add`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "dishId": 0,
  "setmealId": 0,
  "dishFlavor": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|shoppingCartDTO|添加购物车时传递的数据模型|body|true|ShoppingCartDTO|ShoppingCartDTO|
|&emsp;&emsp;dishId|菜品id||false|integer(int64)||
|&emsp;&emsp;setmealId|套餐id||false|integer(int64)||
|&emsp;&emsp;dishFlavor|口味||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


## 查看购物车


**接口地址**:`/user/shoppingCart/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultListShoppingCart|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||array|ShoppingCart|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;name||string||
|&emsp;&emsp;image||string||
|&emsp;&emsp;userId||integer(int64)||
|&emsp;&emsp;dishId||integer(int64)||
|&emsp;&emsp;setmealId||integer(int64)||
|&emsp;&emsp;dishFlavor||string||
|&emsp;&emsp;number||integer(int32)||
|&emsp;&emsp;amount||number||
|&emsp;&emsp;createTime||string(date-time)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": [
		{
			"id": 0,
			"name": "",
			"image": "",
			"userId": 0,
			"dishId": 0,
			"setmealId": 0,
			"dishFlavor": "",
			"number": 0,
			"amount": 0,
			"createTime": ""
		}
	]
}
```


## 清空购物车


**接口地址**:`/user/shoppingCart/clean`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


# 员工相关接口


## 新增员工


**接口地址**:`/admin/employee`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "username": "zhangsan",
  "name": "张三",
  "phone": "13800138000",
  "sex": "1",
  "idNumber": "110101199001011234"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|employeeDTO|新增员工时传递的数据模型|body|true|EmployeeDTO|EmployeeDTO|
|&emsp;&emsp;username|用户名||false|string||
|&emsp;&emsp;name|姓名||false|string||
|&emsp;&emsp;phone|手机号||false|string||
|&emsp;&emsp;sex|性别||false|string||
|&emsp;&emsp;idNumber|身份证号||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## 启用禁用员工账号


**接口地址**:`/admin/employee/status/{status}`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|status||path|true|integer(int32)||
|id||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||object||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {}
}
```


## login_1


**接口地址**:`/admin/employee/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "username": "",
  "password": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|employeeLoginDTO|员工登录时传递的数据模型|body|true|EmployeeLoginDTO|EmployeeLoginDTO|
|&emsp;&emsp;username|用户名||false|string||
|&emsp;&emsp;password|密码||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultEmployeeLoginVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||EmployeeLoginVO|EmployeeLoginVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;userName||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;token||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"id": 0,
		"userName": "",
		"name": "",
		"token": ""
	}
}
```


## 员工分页查询


**接口地址**:`/admin/employee/page`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|employeePageQueryDTO|员工分页查询模型|query|true|EmployeePageQueryDTO|EmployeePageQueryDTO|
|&emsp;&emsp;name|员工姓名||false|string||
|&emsp;&emsp;page|页码||false|integer(int32)||
|&emsp;&emsp;pageSize|每页条数||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultPageResult|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||PageResult|PageResult|
|&emsp;&emsp;total||integer(int64)||
|&emsp;&emsp;records||array|object|


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"total": 0,
		"records": []
	}
}
```


# 通用接口


## 文件上传


**接口地址**:`/admin/common/upload`


**请求方式**:`POST`


**请求数据类型**:`multipart/form-data`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file||query|true|file||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultString|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": ""
}
```


# 店铺相关接口


## 查询店铺营业状态


**接口地址**:`/user/shop/status`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultInteger|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||integer(int32)|integer(int32)|


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": 0
}
```


# C端-菜品浏览接口


## 根据分类id查询菜品


**接口地址**:`/user/dish/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|categoryId||query|true|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultListDishVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||array|DishVO|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;name||string||
|&emsp;&emsp;categoryId||integer(int64)||
|&emsp;&emsp;price||number||
|&emsp;&emsp;image||string||
|&emsp;&emsp;description||string||
|&emsp;&emsp;status||integer(int32)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;categoryName||string||
|&emsp;&emsp;flavors||array|DishFlavor|
|&emsp;&emsp;&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;dishId||integer(int64)||
|&emsp;&emsp;&emsp;&emsp;name||string||
|&emsp;&emsp;&emsp;&emsp;value||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": [
		{
			"id": 0,
			"name": "",
			"categoryId": 0,
			"price": 0,
			"image": "",
			"description": "",
			"status": 0,
			"updateTime": "",
			"categoryName": "",
			"flavors": [
				{
					"id": 0,
					"dishId": 0,
					"name": "",
					"value": ""
				}
			]
		}
	]
}
```


# C端-分类接口


## 查询分类


**接口地址**:`/user/category/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|type||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultListCategory|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||array|Category|
|&emsp;&emsp;id||integer(int64)||
|&emsp;&emsp;type||integer(int32)||
|&emsp;&emsp;name||string||
|&emsp;&emsp;sort||integer(int32)||
|&emsp;&emsp;status||integer(int32)||
|&emsp;&emsp;createTime||string(date-time)||
|&emsp;&emsp;updateTime||string(date-time)||
|&emsp;&emsp;createUser||integer(int64)||
|&emsp;&emsp;updateUser||integer(int64)||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": [
		{
			"id": 0,
			"type": 0,
			"name": "",
			"sort": 0,
			"status": 0,
			"createTime": "",
			"updateTime": "",
			"createUser": 0,
			"updateUser": 0
		}
	]
}
```


# 报表相关接口


## 营业额走势统计


**接口地址**:`/admin/report/turnoverStatistics`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|begin||query|true|string(date)||
|end||query|true|string(date)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResultTurnoverReportVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|msg||string||
|data||TurnoverReportVO|TurnoverReportVO|
|&emsp;&emsp;dateList||string||
|&emsp;&emsp;turnoverList||string||


**响应示例**:
```javascript
{
	"code": 0,
	"msg": "",
	"data": {
		"dateList": "",
		"turnoverList": ""
	}
}
```


## 导出运营数据报表


**接口地址**:`/admin/report/export`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||


**响应参数**:


暂无


**响应示例**:
```javascript

```