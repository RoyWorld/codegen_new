# Project Description
这是一个将database中的表转化成对象的小工具. 目的是以最小的代码量来实现所需的功能, 方便以后以module或jar的形式加入到其他project中.

# About Project

### project structure
![Project Structure](https://raw.githubusercontent.com/RoyWorld/Dolphins/master/src/main/resources/images/projectStructure.png)

### domain
常用的domain其实就是`Table`和`Column`

### how to use
在配置文件中加入database相关的配置信息, 然后直接执行以下代码, 获取`tableList`
```java
List<Table> tableList = TableFactory.getInstance().getAllTables();
```
### properties
* location: 在resource下的database.xml
* content: database相关的信息

### DDL
下面的实例是`Mysql`下的**原来的DDL**
```sql
CREATE TABLE `tb_xx` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `lastModifier` varchar(36) DEFAULT NULL COMMENT '最后修改人',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新建时间',
  `lastModify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='XX表|系统XX信息|XX管理|系统管理|CreateBaseDomain\r\n系统XX表';
```

由于现在注释了`TableFactory`中的部分代码, 部分改动代码如下:
![Change Code](https://raw.githubusercontent.com/RoyWorld/Dolphins/master/src/main/resources/images/changeCode.png)

下面的实例是`Mysql`下的**现在的DDL**
```sql
CREATE TABLE `tb_xx` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `creator` varchar(36) DEFAULT NULL COMMENT '创建人',
  `lastModifier` varchar(36) DEFAULT NULL COMMENT '最后修改人',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新建时间',
  `lastModify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='XX表|CreateBaseDomain\r\n系统XX表';
```

