package com.sanchi.java.docker.services;

import com.sanchi.java.docker.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class DataService {

  @Autowired
  protected NamedParameterJdbcTemplate jdbcTemplate;

  public void saveMessage(Message message) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("sender", message.getSender())
        .addValue("receiver", message.getReceiver())
        .addValue("timestamp", message.getTimestamp())
        .addValue("message_text", message.getMessageText());

    String query = "insert into message(sender, receiver, timestamp, message_text) "
        + "values(:sender, :receiver, :timestamp, :message_text)";
    jdbcTemplate.update(query, namedParameters);
  }
}
