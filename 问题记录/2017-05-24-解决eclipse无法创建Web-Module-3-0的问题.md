---
title: 解决eclipse无法创建Web Module 3.0的问题
comments: true
date: 2017-05-24 13:12:02
categories: Eclipse
tags: Question
---


> Eclipse 新建Maven工程选择maven-archetype-webapp新建dynamic web工程创建的工程版本比较低

在Eclipse中把项目修改为web Moudule3.0或者更高的版本时，会有如下问题：  
**Can not change version of project facet Dynamic web Moudule to 3.0**  
需要项目目录.setting目录的org.eclipse.wst.common.project.facet.core.xml文件
把该文件中的jst.web属性修改为3.0，然后在Eclipse中刷新工程就好
