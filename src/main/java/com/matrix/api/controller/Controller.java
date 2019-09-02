package com.matrix.api.controller;

import com.matrix.api.dto.Commands;
import com.matrix.api.services.MatrixServiceImpl;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class Controller {
  
  private MatrixServiceImpl matrixService;
  
  @Autowired
  public Controller(MatrixServiceImpl matrixService) {
    super();
    this.matrixService = matrixService;
  }

  @PostMapping(path = "matrix")
  public Collection<String> calculateMatrix(@RequestBody @Valid Commands commands) {    
    return matrixService.calculate(commands).stream().collect(Collectors.toList());
  }

}
