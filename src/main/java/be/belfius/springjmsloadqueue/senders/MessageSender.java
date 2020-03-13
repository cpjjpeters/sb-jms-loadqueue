package be.belfius.springjmsloadqueue.senders;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value(value = "${springjms.myQueue}")
	private String queue;
	
	public void send(String message) {
//		jmsTemplate.convertAndSend(queue, message);
		
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
	MessageCreator mc = s ->s.createTextMessage("Put your text here !");
	jmsTemplate.send(queue, mc);
	}

}
