---
title: SpingMvc学习笔记
comments: true
date: 2017-05-25 17:17:08
categories: java编程思想
tags: java基础
---

首先我们看一下下面代码的输出是什么？

```java
class A {
	public A(int i) {
		System.out.println(i);
	}
}

public class Test001 {
	A a1 = new A(1);

	public Test001() {
		A a2 = new A(2);
	}

	A a3 = new A(3);

	public static void main(String[] args) {
		new Test001();
	}
}
```

答案如下：

```
1
3
2
```

结论：
成员变量的初始化顺序早于构造函数的初始化顺序

why?

如果上面的结论得不到保证，那么下面的代码将无法正确执行

```
SomeService myService = new SomeService();
public MyConstructor() {
    someService.doSomething();
}
```

如果构造函数的执行顺序在成员变量的初始化之前，那么someService在没有得到初始化就被调用，可定将会报错

