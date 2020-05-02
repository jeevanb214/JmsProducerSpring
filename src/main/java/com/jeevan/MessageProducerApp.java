package com.jeevan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.jeevan.config.AppConfig;
import com.jeevan.model.Product;
import com.jeevan.producer.MessageSender;

public class MessageProducerApp {

	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Product product = new Product();
		product.setProductId(100);
		product.setName("Laptop");
		product.setQuantity(10);

		MessageSender messageSender = context.getBean(MessageSender.class);
		messageSender.sendMessage(product);
		System.out.println("Message has been sent successfully to Queue");

		((AbstractApplicationContext) context).close();
	}

}