# APIJSON动态数据源修改DEMO

1.动态传入数据源初始化链接

2.批量进行增删改`saveBatch`

3.批量增删改事务一致性和`ADDTransactional`

请求示例
```json
[
  {
    "method": "post",
    "data": {
      "CZKTEST40": {
        "business_code": "f15afbd7-5f82-413d-85a6-d2054d60061a",
        "item_code": 1,
        "id": "010",
        "@dsUrl": "jdbc:mysql://10.133.92.80:3306/erp_dev_demo?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true",
        "@dsUserName": "root",
        "@dsPassword": "Mysql@2020"
      }
    }
  },
  {
    "method": "post",
    "data": {
      "CZKTEST40": {
        "business_code": "f15afbd7-5f82-413d-85a6-d2054d60061a",
        "item_code": 1,
        "id": "020",
        "@dsUrl": "jdbc:mysql://10.133.92.80:3306/erp_dev_demo?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true",
        "@dsUserName": "root",
        "@dsPassword": "Mysql@2020"
      }
    }
  }
]
```

响应示例
```json
[
  {
    "msg": "success",
    "CZKTEST40": {
      "msg": "success",
      "code": 200,
      "@dsPassword": "Mysql@2020",
      "@dsUserName": "root",
      "count": 1,
      "id": "010",
      "ok": true,
      "@dsUrl": "jdbc:mysql://10.133.92.80:3306/erp_dev_demo?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true"
    },
    "code": 200,
    "ok": true
  },
  {
    "msg": "success",
    "CZKTEST40": {
      "msg": "success",
      "code": 200,
      "@dsPassword": "Mysql@2020",
      "@dsUserName": "root",
      "count": 1,
      "id": "020",
      "ok": true,
      "@dsUrl": "jdbc:mysql://10.133.92.80:3306/erp_dev_demo?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true"
    },
    "code": 200,
    "ok": true
  }
]
```