package com.matrix.api.services;

public interface MatrixService<T, R> {
  
  public T calculate(R matrix); 

}
