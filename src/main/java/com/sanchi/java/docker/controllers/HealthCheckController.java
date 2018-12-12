package com.sanchi.java.docker.controllers;

import com.sanchi.java.docker.models.HeathCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @Value("${project.version}")
  private String projectVersion;

  @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
  public HeathCheck getHealth() {
    HeathCheck heathCheck = new HeathCheck();
    heathCheck.setVersion(projectVersion);
    return heathCheck;
  }

}
