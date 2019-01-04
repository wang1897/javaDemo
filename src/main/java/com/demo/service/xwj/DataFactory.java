package com.demo.service.xwj;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {


    public static DataFactory dataFactory = new DataFactory();

    private DataFactory() {
    }

    public DataFactory getInstance() {
        return dataFactory;
    }

    public static List<XContentBuilder> getInitJsonData() {
        List<XContentBuilder> list = new ArrayList<XContentBuilder>();
        XContentBuilder data1 = JsonUtil.model2Json(new Blog(6, "git简介", "2016-06-19", "SVN与Git最主要的区别..."));
        XContentBuilder data2 = JsonUtil.model2Json(new Blog(7, "Java中泛型的介绍与简单使用", "2016-06-19", "学习目标 掌握泛型的产生意义..."));
        XContentBuilder data3 = JsonUtil.model2Json(new Blog(8, "SQL基本操作", "2016-06-19", "基本操作：CRUD ..."));
        XContentBuilder data4 = JsonUtil.model2Json(new Blog(9, "Hibernate框架基础", "2016-06-19", "Hibernate框架基础..."));
        XContentBuilder data5 = JsonUtil.model2Json(new Blog(10, "Shell基本知识", "2016-06-19", "Shell是什么..."));
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        list.add(data5);
        return list;
    }
}
