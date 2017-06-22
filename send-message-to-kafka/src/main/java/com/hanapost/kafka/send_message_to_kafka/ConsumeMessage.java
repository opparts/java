package com.hanapost.kafka.send_message_to_kafka;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
 
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
 
public class ConsumeMessage {
	
	public static void main(String[] args) {
		
		String topic = "topic";
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(createConsumerConfig()); 
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream =  consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
	    while(it.hasNext())
	      System.out.println("consume: " + new String(it.next().message()));
	}
	
	private static ConsumerConfig createConsumerConfig() {
	    Properties props = new Properties();
	    props.put("group.id","group1");
	    props.put("zookeeper.connect","localhost:2181");
	    props.put("zookeeper.session.timeout.ms", "400");
	    props.put("zookeeper.sync.time.ms", "200");
	    props.put("auto.commit.interval.ms", "1000");
	    return new ConsumerConfig(props);
	  }
}
