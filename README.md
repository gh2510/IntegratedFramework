# IntegratedFramework
IntegratedFramework Project
## 完成
* 生成数据库实体类，配置完实体关系。
* 实现大部分数据库接口，实现数据库实体和JSON的转换。
* 20170606:添加3D车间的基本信息访问接口，车间插入更新接口。
* 实现生成排程数据表的记录。
* 修复工艺实体中循环引用导致转换json失败的问题。
* 20170612:添加3D车间对设备、订单报表信息访问接口
* 实现排程时的数据库同步语句。
* 优化了数据库操作层的代码结构，精简代码。
* 20170613:添加对APS的socket的状态查询接口、增加APS对框架的计算结果反馈接口
* 修复了由于表结构的改动引起的对象序列化出现的无限循环问题
* 20170614:添加schedule和aps之间的统一标识;实现APS排程结束后，自动根据标识更新排程记录状态;增加对APS排程状态查询
* 20170615:添加设备故障处理，并通知前端
* 修复了之前多次排程发生的错误。
* 20170619:接收并解析APS结果同时产生快照记录。
* 20170619:增加设备调整故障处理前端访问接口
* 修复了不能都多次添加订单的问题
* 20170621:修改与3D车间的访问接口，增加对所有布局信息的请求。
* 20170622:完成与3D车间的调试，增加通过订单查找模拟数据。
* 20170623:添加用户配置信息，根据用户ID查找该用户的基本信息。取代global全局变量
* 20170624:增加服务器的跨域访问设置，通过添加拦截器并增加请求头
* 20170624:增加3D车间根据设备号单独更新布局状态
* 20170626:调整3D车间模拟数据回复格式;调整新建排程过程
           调整hibernate连接时区，解决8小时时差问题；解决中文显示问题
* 20170627:增加通过选取快照树节点，查看对应3D车间的模拟状态。
           增加选取快照树，将对应结果下发给MES(未嵌入activeMQ)
           增加主页面通知
           增加紧急插单APS调用处理接口
           增加根据订单ID删除订单及其相关联信息
* 20170628:增加设备和订单状态表；嵌入3D车间；部署3D车间至服务器
* 20170629:嵌入APS activeX控件，支持登录与切换
            

## 已知问题
* ~~数据存储依然有问题~~
* 3D车间访问订单和设备报表信息，还未加入activeMQ请求访问MES
* ~~DAOImpl里面的findallbyid应返回一个对象，而不是一个list.~~
* ~~transaction应该不用那么频繁的commit。~~
* 数据库中的表结构多对多的关系再确认一次，多对多的关系会导致序列化出错。
* 20170619个人告警中心表创建完成，但还未根据故障插入数据表
* ~~新建排程中的日期与系统时间相差8小时~~
* 框架plan表未转换成3D模拟数据格式
* 将排程结果下发给MES，未添加activeMQ查询
* IE11不支持3D车间，APS不支持chrome、firfox和edge