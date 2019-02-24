package com.getjavajob.simplenet.service;

import com.getjavajob.simplenet.common.entity.JMSChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static com.getjavajob.simplenet.service.config.JMSConfig.JMS_QUEUE;

@Service
public class JMSSenderService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JMSSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(JMSChatMessage jmsChatMessage) {
        jmsTemplate.convertAndSend(JMS_QUEUE, jmsChatMessage);
    }
}
