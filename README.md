# Dubbo源码学习计划

### Dubbo 核心流程源码实现

#### 1. [Dubbo SPI机制](https://blog.csdn.net/qiangcai/article/details/77750541) 
#### 2. [Spring Bean注册](http://veryjj.github.io/2018/04/22/Dubbo%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90-Spring-Bean%E6%B3%A8%E5%86%8C/)
#### 3. Provider 注册、暴露服务、处理请求 / Consumer注册、订阅服务、调用实现

```
参考：
1. https://blog.csdn.net/qiangcai/article/details/73992080
2. https://veryjj.github.io/2018/05/02/Dubbo%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90-Provider%E6%9A%B4%E9%9C%B2%E6%9C%8D%E5%8A%A1/
```
```
疑问：afterPropertiesSet和onApplicationEvent的区别？执行顺序又是什么？
    
    这里的监听应该是旧版本的时候使用的，之后用该用的是afterPropertiesSet
    
    Spring启动，constructor,@PostConstruct,afterPropertiesSet,onApplicationEvent执行顺序如下：
    
    1. constructor
    2. @postConstruct
    3. afterPropertiesSet
    4. onApplicationEvent(每一次bean的注入，spring都会被刷新，方法也会被调用)
        
```
        
#### 4. Dubbo Filter机制