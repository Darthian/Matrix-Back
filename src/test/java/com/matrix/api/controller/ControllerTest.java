package com.matrix.api.controller;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrix.api.dto.Commands;
import com.matrix.api.services.MatrixServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ControllerTest {

  @Autowired
  public WebApplicationContext context;

  @MockBean
  public MatrixServiceImpl matrixService;

  public MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void testExecuteMatrixCommands() throws Exception {
     Mockito.when(matrixService.calculate(Mockito.any())).thenReturn(createMockListSolution());
     RequestBuilder requestBuilder =
     MockMvcRequestBuilders.post("/matrix")
     .contentType(MediaType.APPLICATION_JSON)
     .content(asJsonString(createRequest()));
    
     MvcResult result = mvc.perform(requestBuilder).andReturn();
     MockHttpServletResponse response = result.getResponse();
    
    assertEquals(HttpStatus.OK.value(), response.getStatus());

  }

  public List<String> createMockListSolution() {
    return Arrays.asList("4", "4", "27", "0", "1", "1");
  }
  
  public Commands createRequest() {
    return new Commands(Arrays.asList("2",
        "4 5",
        "UPDATE 2 2 2 4",
        "QUERY 1 1 1 3 3 3",
        "UPDATE 1 1 1 23",
        "QUERY 2 2 2 4 4 4",
        "QUERY 1 1 1 3 3 3",
        "2 4",
        "UPDATE 2 2 2 1",
        "QUERY 1 1 1 1 1 1",
        "QUERY 1 1 1 2 2 2",
        "QUERY 2 2 2 2 2 2"));
  }

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
