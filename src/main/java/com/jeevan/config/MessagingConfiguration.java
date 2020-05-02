package com.jeevan.config;

import java.util.Arrays;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
public class MessagingConfiguration {

	private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";// we should not change this
	private static final String MESSAGE_QUEUE = "message_queue"; // name

	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);// we need to set the url to send the data
		connectionFactory.setTrustedPackages(Arrays.asList("com.jeevan"));// add all packages which is used in send and
																			// receive Message object.
		return connectionFactory;
	}

	/*
	 * Used here for Sending Messages.
	 */
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());// Set the ConnectionFactory to use for obtaining JMS
															// Connections.jms template needs the connection factory
		template.setDefaultDestinationName(	MESSAGE_QUEUE); /* queue name settingSet the destination name to be used on send/receive
								 * operations thatdo not have a destination parameter. The specified name will
								 * bedynamically resolved via the DestinationResolver.
								 * 
								 * Alternatively, specify a JMS Destination object as "defaultDestination".
								 */

		return template;
	}

	@Bean
	MessageConverter converter() {
		/*
		 * A simple message converter which is able to handle TextMessages,
		 * BytesMessages,MapMessages, and ObjectMessages. Used as default conversion
		 * strategyby org.springframework.jms.core.JmsTemplate, for convertAndSend and
		 * receiveAndConvert operations. Converts a String to a javax.jms.TextMessage, a
		 * byte array to a javax.jms.BytesMessage, a Map to a javax.jms.MapMessage, anda
		 * Serializable object to a javax.jms.ObjectMessage (or vice versa).
		 */
		return new SimpleMessageConverter();
	}

}