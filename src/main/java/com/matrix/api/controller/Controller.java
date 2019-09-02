package com.matrix.api.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.matrix.api.dto.Commands;
import com.matrix.api.services.MatrixServiceImpl;
import reactor.core.publisher.Flux;

@CrossOrigin("*")
@RestController
public class Controller {
  
  private MatrixServiceImpl matrixService;
  
  @Autowired
  public Controller(MatrixServiceImpl matrixService) {
    super();
    this.matrixService = matrixService;
  }

  @GetMapping(path = "numeros", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Integer> all() {
    return Flux.range(1, 30).delayElements(Duration.ofSeconds(1)).repeat().map(n -> n);
  }

  @PostMapping(path = "flux/matrix")
  public Flux<String> home(@RequestBody @Valid Commands commands) {
    return Flux.fromIterable(matrixService.calculate(commands)).delayElements(Duration.ofMillis(1000)).take(3);
  }

  @PostMapping(path = "matrix")
  public Collection<String> calculateMatrix(@RequestBody @Valid Commands commands) {    
    return matrixService.calculate(commands).stream().collect(Collectors.toList());
  }
  
  @GetMapping("/stream-sse")
  public Flux<ServerSentEvent<String>> streamEvents() {
    return Flux.interval(Duration.ofSeconds(1))
        .map(sequence -> ServerSentEvent
            .<String>builder()
            .id(String.valueOf(sequence))
            .event("periodic-event")
            .data("SSE - " + LocalTime.now().toString())
            .build());
  }

}
