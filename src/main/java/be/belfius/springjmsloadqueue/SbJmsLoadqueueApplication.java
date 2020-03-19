package be.belfius.springjmsloadqueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJms
@Slf4j
public class SbJmsLoadqueueApplication {

	static StringBuilder sb = new StringBuilder();
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message
		// converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		factory.setConnectionFactory(connectionFactory);
	    factory.setErrorHandler(t -> {
	         log.error("Error in listener!", t);
	       });
		return factory;
	}

    
	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	public static void main(String[] args) {
		log.info(">>> starting SbJmsLoadqueueApplication <<<");
		ConfigurableApplicationContext context = SpringApplication.run(SbJmsLoadqueueApplication.class, args);

		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

		// Send a message with a POJO - the template reuse the message converter
		jmsTemplate.convertAndSend("myQueue","simple text");
		String payload = "payload";
		try {
			payload = createStringFromFile();
			jmsTemplate.convertAndSend("myQueue",payload);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
public static String createStringFromFile() throws IOException{
		
		try {
////		"D:\\Data\\17L07462.txt"
////		"D:\\Data\\19L03549.txt"
////		"D:\\Data\\19L03553.txt"
////		"D:\\Data\\20L00003.txt"
////		"D:\\Data\\20L00029.txt"			
			BufferedReader reader = new BufferedReader(new FileReader("D:\\Data\\19L03553.txt"));
			char[] buf = new char[1024];
			int numRead = 0;
			while((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				sb.append(readData);
				buf = new char[1024];
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		String returnStr = sb.toString();
		return returnStr;
	}

}
