package com.demo.service;

import com.alibaba.fastjson.JSON;
import com.demo.service.xwj.DataFactory;
import org.apache.catalina.startup.Tool;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.List;

public class EsService {
    private static Logger logger = LoggerFactory.getLogger(EsService.class);

    public final static String HOST = "127.0.0.1";

    public final static int PORT = 9300; //http请求的端口是9200，客户端是9300

    public static void main(String[] args) throws Exception {
        Client client = init();
        if (false) {
            // 添加数据
            addDocument();
        }

        if (false) {
            // 查询所有数据
            SearchRequestBuilder search = client.prepareSearch("explorer").setTypes("txdetail");

            //使用原生排序优化性能
            search.addSort("height", SortOrder.DESC);
            //设置每批读取的数据量
            search.setSize(100);
            //默认是查询所有
            search.setQuery(QueryBuilders.queryStringQuery("*:*"));
            //设置 search context 维护1分钟的有效期
            search.setScroll(TimeValue.timeValueMinutes(1));

            //获得首次的查询结果
            SearchResponse scrollResp = search.get();
            //打印命中数量
            System.out.println("命中总数量：" + scrollResp.getHits().getTotalHits());

            //打印计数
            int count = 1;
            do {
                System.out.println("第" + count + "次打印数据：");
                //读取结果集数据
                for (SearchHit hit : scrollResp.getHits().getHits()) {
                    System.out.println(hit.getSourceAsString());
                }
                count++;
                //将scorllId循环传递
                scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(TimeValue.timeValueMinutes(1)).execute().actionGet();

                //当searchHits的数组为空的时候结束循环，至此数据全部读取完毕
            } while (scrollResp.getHits().getHits().length != 0);
        }

        if (false) {
            // 查询总数(默认查十条)
            SearchResponse searchResponse = client.prepareSearch("explorer").setTypes("txdetail").get();
            Long count = searchResponse.getHits().getTotalHits();
            System.out.println("总数查询：" + count);
        }

        if (false) {
            // 按照条件查询
            SearchRequestBuilder search = client.prepareSearch("explorer").setTypes("txdetail");
            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("txnhash", "0075ea2642d667b41175ff781eb8fecc1fcf6b26e6f25b7fbf13500a5ce02f20");
            QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("contracthash", "1ddbb682743e9d9e2b71ff419e97a9358c5c4ee9");

            SearchResponse searchResponse = search.setQuery(QueryBuilders.boolQuery().must(queryBuilder).must(queryBuilder2)).execute().actionGet();
            SearchHits hits = searchResponse.getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }

        if (false) {
            // 分页查询数据
            int pageStart = 10;
            int pageSize = 100;
            SearchResponse response = client.prepareSearch("explorer").setTypes("txdetail")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .addSort("height", SortOrder.DESC)
                    .setFrom(pageStart)   //跳过前10个文档
                    .setSize(pageSize)   //获取100个文档
                    .execute().actionGet();

            System.out.println("分页查询结果：" + response.getHits().getHits().length);
        }

        if (true) {
            // groupby 查询
//            TermsBuilder companyNameAgg = AggregationBuilders.terms("companyName").field("companyName").size(10);
//            SumBuilder companyNameAggSum = AggregationBuilders.sum("companyNameSum").field("cvcount");
//            companyNameAgg.subAggregation(companyNameAggSum);//把sum聚合器放入到Term聚合器中，相当于先group by在sum
//            SearchRequestBuilder searchBuilder = ElasticClientFactory.getClient().prepareSearch(indexname).setTypes(typeName).addAggregation(companyNameAgg);
//            SearchResponse searchResponse = searchBuilder.execute().actionGet();
//            Terms terms = searchResponse.getAggregations().get("companyName");
//            List<Terms.Bucket> buckets = terms.getBuckets();
//            List<String> list = Lists.newArrayList();
//            for (Terms.Bucket bucket : buckets) {
//                InternalSum internalSum = bucket.getAggregations().get("companyNameSum");//注意从bucket而不是searchResponse
//                System.out.println(bucket.getKeyAsString() + "\t" + bucket.getDocCount() + "\t"+internalSum.getValue());
//            }
//            System.out.println("done");
        }

        client.close();
    }

    private static Client init() throws Exception {
        /* 创建客户端 */
        Settings settings = Settings.builder().put("cluster.name", "my-application").put("client.transport.sniff", true).build();
        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(HOST), PORT);
        Client client = new PreBuiltTransportClient(settings).addTransportAddress(transportAddress);

//			Client client = TransportClient.builder().build()
//					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));

        return client;
    }

    private static void addDocument() throws Exception {
        Client client = init();

        List<XContentBuilder> jsonData = DataFactory.getInitJsonData();
        for (int i = 0; i < jsonData.size(); i++) {
            client.prepareIndex("blog", "article").setId((i + 1) + "").setSource(jsonData.get(i)).get();
        }
        client.close();
    }
}
