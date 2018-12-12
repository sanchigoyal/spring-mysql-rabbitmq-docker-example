package com.sanchi.java.docker.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanchi.java.docker.models.Message;
import com.sanchi.java.docker.services.DataService;
import java.io.IOException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  protected DataService dataService;


  /**
   * RabbitMQ listener.
   */
  @RabbitListener(queues = "message-queue")
  public void receiveMessage(byte[] payload) throws IOException {

    // convert byte to object
    Message message = mapper.readValue(payload, Message.class);

    // if no message, reject the payload.
    if (message == null) {
      throw new AmqpRejectAndDontRequeueException("Received message is null");
    }

    dataService.saveMessage(message);
  }

}
