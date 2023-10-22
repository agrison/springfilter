package com.turkraft.springfilter.transformer.processor;

import com.turkraft.springfilter.language.InsensitiveLikeOperator;
import com.turkraft.springfilter.parser.node.InfixOperationNode;
import com.turkraft.springfilter.transformer.FilterExpressionTransformer;
import jakarta.persistence.criteria.Expression;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class InsensitiveLikeOperationExpressionProcessor implements
    FilterInfixOperationProcessor<FilterExpressionTransformer, Expression<?>>,
    LikeExpressionProcessor {
  private final LikeOperationExpressionProcessor likeOperationExpressionProcessor;
  private final Character escapeCharacter;

  public InsensitiveLikeOperationExpressionProcessor(
          LikeOperationExpressionProcessor likeOperationExpressionProcessor,
          @Value("${springfilter.jpa-like-escape-character:#{null}}") Character escapeCharacter) {
    this.likeOperationExpressionProcessor = likeOperationExpressionProcessor;
    this.escapeCharacter = escapeCharacter;
  }

  @Override
  public Class<FilterExpressionTransformer> getTransformerType() {
    return FilterExpressionTransformer.class;
  }

  @Override
  public Class<InsensitiveLikeOperator> getDefinitionType() {
    return InsensitiveLikeOperator.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Expression<?> process(FilterExpressionTransformer transformer, InfixOperationNode source) {
    transformer.registerTargetType(source, Boolean.class);
    transformer.registerTargetType(source.getLeft(), String.class);
    transformer.registerTargetType(source.getRight(), String.class);

    return like(transformer.getCriteriaBuilder(),
            transformer.getCriteriaBuilder()
                    .upper((Expression<String>) transformer.transform(source.getLeft())),
            transformer.getCriteriaBuilder()
                    .upper(likeOperationExpressionProcessor.getLikePatternExpression(transformer,
                            source.getRight())),
            escapeCharacter);
  }

}
