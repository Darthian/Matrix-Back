package com.matrix.api.dto;

import java.util.regex.Matcher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatcherInfo {
  
  public Integer index;
  public Matcher matcher;

}
