package eivindw.definition;

import eivindw.steps.ParallelMultiStep;
import eivindw.steps.SerieltMultiStep;
import eivindw.steps.Step;

public abstract class ProcessDefinition<T> {

   public abstract StepDefinition<T> createTopLevelStep();

   protected <X> ClassStepDefinition<X> steg(Class<? extends Step<X>> stegklasse) {
      return new ClassStepDefinition<>(stegklasse);
   }

   @SafeVarargs
   protected final <X> MultiStepDefinition<X> seriell(StepDefinition<X>... stegdefinisjoner) {
      return new MultiStepDefinition<>(SerieltMultiStep.class, stegdefinisjoner);
   }

   @SafeVarargs
   protected final <X> MultiStepDefinition<X> parallell(StepDefinition<X>... stegdefinisjoner) {
      return new MultiStepDefinition<>(ParallelMultiStep.class, stegdefinisjoner);
   }
}
