// package com.learn.mvc.mqtt;
//
// import lombok.Data;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.PropertySource;
//
// /**
//  * @program: springboot-summary
//  * @version:
//  * @description:
//  * @author: ling
//  * @create: 2022-08-11 17:22
//  **/
// // @Configuration
// // @PropertySource(encoding = "UTF-8", value = {"classpath:config/mqtt.properties"})
// // @Data
// public class MqttProperties {
//     @Value("${mqtt.username}")
//     private String username;
//
//     @Value("${mqtt.password}")
//     private String password;
//
//     @Value("${mqtt.url}")
//     private String url;
//
//     @Value("${mqtt.producer.clientId}")
//     private String producerClientId;
//
//     @Value("${mqtt.producer.defaultTopic}")
//     private String producerDefaultTopic;
//
//     @Value("${mqtt.consumer.clientId}")
//     private String consumerClientId;
//
//     @Value("${mqtt.consumer.defaultTopic}")
//     private String consumerDefaultTopic;
// }
