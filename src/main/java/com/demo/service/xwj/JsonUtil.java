package com.demo.service.xwj;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class JsonUtil {

    // Java实体对象转json对象
    public static XContentBuilder model2Json(Blog blog) {
        XContentBuilder jsonBuild = null;
        try {
            jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
                    .field("id", blog.getId())
                    .field("title", blog.getTitle())
                    .field("posttime", blog.getPosttime())
                    .field("content", blog.getContent()).endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonBuild;
    }
}
