package be.belfius.springjmsloadqueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJms
@Slf4j
public class SbJmsLoadqueueApplication implements CommandLineRunner {

	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) {
		log.info("Starting the application");
		SpringApplication.run(SbJmsLoadqueueApplication.class, args);
	}

	public static String createStringFromFile() throws IOException{
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader("D:\\Data\\17L07462.txt"));
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

	@Override
	public void run(String... args) throws Exception {
		String payload = "payload";
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext =cf.createContext();) {
			jmsContext.createProducer().send(queue, createStringFromFile());
//			"D:\\Data\\17L07462.txt"
//			"D:\\Data\\19L03549.txt"
//			"D:\\Data\\19L03553.txt"
//			"D:\\Data\\20L00003.txt"
//			"D:\\Data\\20L00029.txt"
			

		} 
	}
}
