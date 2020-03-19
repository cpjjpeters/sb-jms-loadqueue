package be.belfius.springjmsloadqueue.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageSender {
	static StringBuilder sb = new StringBuilder();
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value(value = "${springjms.myQueue}")
	private String queue;
	
	public void send(String message) {
		log.info("MessageSender.send");
		jmsTemplate.convertAndSend(queue, message);
		
//		MessageCreator mc = new MessageCreator() {
//
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		};
//		jmsTemplate.send(queue, mc);
//	MessageCreator mc = s ->s.createTextMessage("Put your text here ! 1");
		// -----------------------------------------
//	MessageCreator mc = s ->s.createTextMessage(message);
//	jmsTemplate.send(queue, mc);
	}

}
