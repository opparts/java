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
        properties.put(PropertyKeyConst.ConsumerId, "CID_topic_alex_1");// ���ڿ���̨������ Consumer ID
        properties.put(PropertyKeyConst.AccessKey, "xeGuynZzF3WhtWWp");// AccessKey �����������֤���ڰ����Ʒ������������̨����
        properties.put(PropertyKeyConst.SecretKey, "Ejl0AOb31iYVuRyIvCtZNyuqlUqPDL");// SecretKey �����������֤���ڰ����Ʒ������������̨����
        //����������������http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //�����ƹ��⻷����http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet
        //���ݽ����ƻ�����http://jbponsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //���������ƻ�����http://mq4finance-sz.addr.aliyun.com:8080/rocketmq/nsaddr4client-internal
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");//�˴��Թ�������������Ϊ��
        properties.put(PropertyKeyConst.SuspendTimeMillis, "100"); // ˳����Ϣ����ʧ�ܽ�������ǰ�ĵȴ�ʱ�� ��λ(����)
        properties.put(PropertyKeyConst.MaxReconsumeTimes, "20"); // ��Ϣ����ʧ��ʱ��������Դ���
        // �ڶ�����Ϣǰ��������� start ���������� Consumer��ֻ�����һ�μ��ɡ�
        OrderConsumer consumer = ONSFactory.createOrderedConsumer(properties);
        consumer.subscribe(
                // Message������Topic
                "topic_alex_1",
                // ����ָ��Topic�µ�Tags��
                // 1. * ��ʾ����������Ϣ
                // 2. TagA || TagB || TagC ��ʾ����TagA �� TagC �� TagD ����Ϣ
                "*",
                new MessageOrderListener() {
                    /**
                     * 1. ��Ϣ���Ѵ���ʧ�ܻ��ߴ�������쳣������OrderAction.Suspend<br>
                     * 2. ��Ϣ����ɹ��������뷵��OrderAction.Success
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
