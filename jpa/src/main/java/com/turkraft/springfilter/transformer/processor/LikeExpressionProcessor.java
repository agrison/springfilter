package com.turkraft.springfilter.transformer.processor;

import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

interface LikeExpressionProcessor {
  default Predicate like(CriteriaBuilder builder, Expression<String> left,
                       Expression<String> pattern, @Nullable Character escapeCharacter) {
    if (escapeCharacter == null) {
      return builder.like(left, pattern);
    } else {
      return builder.like(left, pattern, escapeCharacter);
    }
  }
}