package com.demo;


import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;

import java.util.Properties;


public class ConsumerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_topic_alex_1");// 您在控制台创建的 Consumer ID
        properties.put(PropertyKeyConst.AccessKey, "xeGuynZzF3WhtWWp");// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "Ejl0AOb31iYVuRyIvCtZNyuqlUqPDL");// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        //公有云生产环境：http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //公有云公测环境：http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet
        //杭州金融云环境：http://jbponsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //杭州深圳云环境：http://mq4finance-sz.addr.aliyun.com:8080/rocketmq/nsaddr4client-internal
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");//此处以公有云生产环境为例
        properties.put(PropertyKeyConst.SuspendTimeMillis, "100"); // 顺序消息消费失败进行重试前的等待时间 单位(毫秒)
        properties.put(PropertyKeyConst.MaxReconsumeTimes, "20"); // 消息消费失败时的最大重试次数
        // 在订阅消息前，必须调用 start 方法来启动 Consumer，只需调用一次即可。
        OrderConsumer consumer = ONSFactory.createOrderedConsumer(properties);
        consumer.subscribe(
                // Message所属的Topic
                "topic_alex_1",
                // 订阅指定Topic下的Tags：
                // 1. * 表示订阅所有消息
                // 2. TagA || TagB || TagC 表示订阅TagA 或 TagC 或 TagD 的消息
                "*",
                new MessageOrderListener() {
                    /**
                     * 1. 消息消费处理失败或者处理出现异常，返回OrderAction.Suspend<br>
                     * 2. 消息处理成功，返回与返回OrderAction.Success
                     */
                    @Override
                    public OrderAction consume(Message message, ConsumeOrderContext context) {
                        System.out.println(message);
                        return OrderAction.Success;
                    }
                });
        consumer.start();
    }
}
