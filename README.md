# manatee-cache(进行中)

## 目录<br> 
├─manatee-cache<br> 
│ ├─manatee-cache-client          缓存客户端（端到端）<br> 
│ ├─manatee-cache-example         example<br> 
│ ├─manatee-cache-parent          父模块<br> 

##一、项目背景<br> 
###1、用户痛点（电商、互联网系统）<br> 
目前遇到大促、618、双11会经常出现热key问题，打崩分布式缓存中间件的节点，影响业务集群稳定性，虽然部分业务逻辑提前加有本地缓存，但是对于任意突发流量还是比较棘手。一旦发现新热key，业务方不得不改代码，这样解决问题周期长，效率低。

###2、多级缓存OMC<br> 

为了应对任意突发性的无法预先感知的热点数据，本项目旨在设计一个支持热点数据毫秒级探测识别及本地自动缓存、高性能、高可靠的多级缓存平台。产品具备以下三个特性：
1、热点自动探测：及时发现热点提高系统稳定性，开发人员也无需再定位热key、反复调整维护代码解决，流量高峰期（如618、双11），缓存前置可提升系统响应速率。
2、缓存可观测：提供灵活规则配置，实时监控解决业务热点缓存黑盒痛点，有助提高缓存命中率。
3、业务0侵入：本地缓存透明化，电商、互联网系统业务均可平滑接入。多存储的缓存化支持，满足多业务场景需求。

##三、方案设计<br> 
## 客户端上报、缓存、失效流程<br> 
![](https://github.com/aRanhy/manatee-gateway/blob/master/doc/网关鉴权.png)
## 整体架构图<br> 
![](https://github.com/aRanhy/manatee-gateway/blob/master/doc/网关鉴权.png)
## 数据交互图<br> 
![](https://github.com/aRanhy/manatee-gateway/blob/master/doc/网关鉴权.png)

1、热点自动探测：应用通过openmc-sdk异步上报待探测数据给计算集群worker，worker根据预先设定的探测规则统计数据访问频率，达到阀值后再将热数据推送给应用层进行缓存，做到可以自动探测热点并及时在应用层进行本地缓存，在高并发情况下，缓存前置可有效减轻分布式缓存中间件压力，提高系统稳定性。
2、缓存可观测：worker探测到的热点数据同时也会实时推送给console展示，此外缓存命中率、访问次数也会定时推送到console，在console层即可实现热数据访问频率、探测规则命中率、热数据变热趋势、手动操作热数据、本地缓存实时可视化的功能。解决多级缓存效果难以验证、本地缓存黑盒的问题。
3、缓存透明性：为了减少业务侵入，做到可平滑接入，对redis客户端进行改造并接入openmc-sdk，业务只需升级指定版本的redis客户端即可完成openmc的接入，实现缓存透明化。

##四、稳定性保障<br> 
1、热key数据异步批量上报，且通信模块与业务线程隔离互不影响，同时支持热key数量、容量双限，保障不会对业务内存造成OOM。其次即使omc组件异常也是对业务无感，最坏不能提供热点探测能力，应用从本地拿不到数据还会走远端redis获取，不影响业务正常使用，因此业务可放心接入。
2、热key统计会在业务客户端先进行预计算，然后再推送到远端worker进行分布式聚合计算，worker推送和计算性能高峰可达16w/s(单节点8c16g)。
3、worker集群无状态支持扩缩容，业务可根据redis访问频率，自由配置worker节点的规格和数量来支持高并发的热点数据计算。同时etcd配置中心也做了分区，多etcd集群来支持超大规模的应用接入。

##五、应用场景<br>
omc不仅可以用作大促热商品、黑名单、热接口数据本地缓存，如直接使用omc-sdk的api还可以分布式限流作用，如集群接口、集群用户、爬虫用户等。


2、select缓存机制
2.1、select是否匹配缓存规则
        缓存规则在控制台配置，只有配置了mapperId（mapper.xml文件中namespace +selectId）的sql才进行缓存，客户端会在业务系统启动时扫描mapper信息并上报给omc。
2.2、select如何组装成查询key
       通过select的mapperId、sql、入参、RowBounds行范围这五个参数计算hashcode进行处理后拼接成查询key，再从caffeine中拿数据。
2.3、查询key如何load数据
 不需热点识别：从DB查询数据结果后，直接把key和结果集result写入caffeine cache中。
 需要热点识别：一开始caffeine cache中是不存在组装的key，需要将key上报给worker做热点识别，直到变热后由worker推送给客户端来缓存key（此时value为空或默认魔法值），二次查询的时候，识别到caffeine cache中存在组装的key但value为空，则从DB中查询数据结果后，再更新key的value为查询结果集合。
3、select缓存更新机制
     DB数据更新后，jins会往mq发送变更记录，每条记录包含了表中变更行各字段的详细变更信息，如何利用行更变去刷新客户端的select缓存，这里使用反匹配手段（仅限单表缓存），反匹配就是看变更的行是否匹配select查询语句的where条件即可，这样就可以做到局部更新，而不像mybaits只有表变更就去刷新所有查询，这样降低了缓存的命中率。 where  user.sex = 男 and user2.id=2 
     例举插入数据如何刷新缓存：
     步骤一：首先执行mapperId为"UserMapper.selectBySex" 的查询语句 select * from user where sex="男"；把结果缓存起来，并生成更新key（userMapper.selectBySex.男）。
     步骤二：再插入一条数据 insert  user(name,sex) values("张三"，"男");
     步骤三：数据库变动触发jins发布一条行插入消息，worker解析消息根据事先配置的缓存规则组装更新key（userMapper.selectBySex.男）推送给客户端。
     步骤四：客户端接收更新key(userMapper.selectBySex.男),匹配本地的更新key并刷新缓存值为空，待二次查询再缓存查询结果。
![omc-flow1.drawio.png](/img/bVc7ElN)
![omc-framework.drawio.png](/img/bVc7ElM)
![稳定性保障.png](/img/bVc7ElO)
![应用场景.png](/img/bVc7ElP)
![MultilevelCache.drawio (1).png](/img/bVc7ElQ)
![mysql缓存透明设计.drawio.png](/img/bVc7ElR)
