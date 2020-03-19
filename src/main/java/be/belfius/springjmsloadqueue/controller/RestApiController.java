package be.belfius.springjmsloadqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.belfius.springjmsloadqueue.senders.ArtemisProducer;

@RestController
public class RestApiController {

        @Autowired
        ArtemisProducer producer;

        @RequestMapping(value="/send")
        public String produce(@RequestParam("msg")String msg){
                producer.send(msg);
                return "Done";
        }
}
