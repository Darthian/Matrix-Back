package com.matrix.api.dto;

import java.util.regex.Matcher;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatcherInfo {
  
  private Integer index;
  private Matcher matcher;

}
