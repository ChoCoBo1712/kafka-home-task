package com.stratosky.taxiapp.validator;

@FunctionalInterface
public interface PostValidator<I, T> {
  boolean validate(I id, T t);
}
