package com.demo;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

public class ProcedureTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "XXX");//���ڿ���̨������Producer ID
        properties.put(PropertyKeyConst.AccessKey,"XXX");// AccessKey �����������֤���ڰ����Ʒ������������̨����
        properties.put(PropertyKeyConst.SecretKey, "XXX");// SecretKey �����������֤���ڰ����Ʒ������������̨����
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");//���÷��ͳ�ʱʱ�䣬��λ����
        //PropertyKeyConst.ONSAddr��ַ�����ʵ�������Ӧ���¼���������룺
        //����������������http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //�����ƹ��⻷����http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet
        //���ݽ����ƻ�����http://jbponsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //���������ƻ�����http://mq4finance-sz.addr.aliyun.com:8080/rocketmq/nsaddr4client-internal
        //��̫����1�����ƻ�����ֻ�������¼���ECS����http://ap-southeastaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal
        properties.put(PropertyKeyConst.ONSAddr,
          "http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal");//�˴��Թ�������������Ϊ��
        Producer producer = ONSFactory.createProducer(properties);
        // �ڷ�����Ϣǰ���������start����������Producer��ֻ�����һ�μ���
        producer.start();
        //ѭ��������Ϣ
        for (int i = 0; i < 100; i++){
            Message msg = new Message( //
                // Message������Topic
                "TopicTestMQ",
                // Message Tag �����ΪGmail�еı�ǩ������Ϣ�����ٹ��࣬����Consumerָ������������MQ����������
                "TagA",
                // Message Body �������κζ�������ʽ�����ݣ� MQ�����κθ�Ԥ��
                // ��ҪProducer��ConsumerЭ�̺�һ�µ����л��ͷ����л���ʽ
                "Hello MQ".getBytes());
            // ���ô�����Ϣ��ҵ��ؼ����ԣ��뾡����ȫ��Ψһ��
            // �Է��������޷������յ���Ϣ����£���ͨ�������Ʒ������������̨��ѯ��Ϣ������
            // ע�⣺������Ҳ����Ӱ����Ϣ�����շ�
            msg.setKey("ORDERID_" + i);
            // ͬ��������Ϣ��ֻҪ�����쳣���ǳɹ�
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }
        // ��Ӧ���˳�ǰ������Producer����
        // ע�⣺���������Ҳû������
        producer.shutdown();
    }
}