1. spring boot单元测试配置

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test{
}
```
2. spring 单元测试

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mybatis/mybatis-spring.xml"})
public class Test{
}
```


> 其中SpringRunner是继承SpringJUnit4ClassRunner类的