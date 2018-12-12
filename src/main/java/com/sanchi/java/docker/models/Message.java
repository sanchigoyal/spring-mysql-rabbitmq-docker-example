package com.sanchi.java.docker.models;

import java.time.LocalDateTime;

public class Message {

  private String sender;
  private String receiver;
  private LocalDateTime timestamp;
  private String messageText;


  @Override
  public String toString() {
    return "Message{" +
        "sender='" + sender + '\'' +
        ", receiver='" + receiver + '\'' +
        ", timestamp=" + timestamp +
        ", messageText='" + messageText + '\'' +
        '}';
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }
}
