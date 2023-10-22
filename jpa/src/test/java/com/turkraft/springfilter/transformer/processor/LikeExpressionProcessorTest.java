package com.turkraft.springfilter.transformer.processor;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LikeExpressionProcessorTest {
  LikeExpressionProcessor processor = spy(LikeExpressionProcessor.class);

  @Test
  void likeWithoutEscapeCharacter() {
    // Given
    CriteriaBuilder builder = mock();
    Expression<String> left = mock();
    Expression<String> pattern = mock();
    Character escapeCharacter = null;

    // When
    processor.like(builder, left, pattern, escapeCharacter);

    // Then
    verify(builder).like(left, pattern);
  }

  @Test
  void likeWithEscapeCharacter() {
    // Given
    CriteriaBuilder builder = mock();
    Expression<String> left = mock();
    Expression<String> pattern = mock();
    Character escapeCharacter = '`';

    // When
    processor.like(builder, left, pattern, escapeCharacter);

    // Then
    verify(builder).like(left, pattern, '`');
  }
}