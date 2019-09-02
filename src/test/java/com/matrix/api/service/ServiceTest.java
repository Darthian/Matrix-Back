package com.matrix.api.service;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.matrix.api.dto.Commands;
import com.matrix.api.services.MatrixServiceImpl;

public class ServiceTest {

  public MatrixServiceImpl matrixService;

  @Before
  public void setup() {
    matrixService = new MatrixServiceImpl();
  }

  @Test
  public void matrixExecuteTest() {
    List<String> results = matrixService.calculate(createCommands());
    assertEquals(createMockListSolution(), results);
  }

  private Commands createCommands() {
    return new Commands(Arrays.asList("2", "4 5", "UPDATE 2 2 2 4", "QUERY 1 1 1 3 3 3",
        "UPDATE 1 1 1 23", "QUERY 2 2 2 4 4 4", "QUERY 1 1 1 3 3 3", "2 4", "UPDATE 2 2 2 1",
        "QUERY 1 1 1 1 1 1", "QUERY 1 1 1 2 2 2", "QUERY 2 2 2 2 2 2"));
  }

  private List<String> createMockListSolution() {
    return Arrays.asList("4", "4", "27", "0", "1", "1");
  }

}
