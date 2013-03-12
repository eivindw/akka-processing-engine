package eivindw.definition;

import akka.actor.*;
import eivindw.steps.MultiStep;
import eivindw.steps.MultiStepHub;

import java.util.ArrayList;
import java.util.List;

public class MultiStepDefinition<T> extends StepDefinition<T> {

   private final StepDefinition[] stepDefinitions;
   private final Class<? extends MultiStep> clazz;

   protected MultiStepDefinition(Class<? extends MultiStep> clazz, StepDefinition... stepDefinitions) {
      this.clazz = clazz;
      this.stepDefinitions = stepDefinitions;
   }

   protected List<ActorRef> createSubSteps(ActorRefFactory context) {
      List<ActorRef> subSteps = new ArrayList<>();
      for(StepDefinition stepDefinition : stepDefinitions) {
         subSteps.add(stepDefinition.toActor(context));
      }
      return subSteps;
   }

   @Override
   public ActorRef toActor(ActorRefFactory context) {
      final List<ActorRef> stepList = createSubSteps(context);
      final Props multiStepProps = new Props(new UntypedActorFactory() {
         @Override
         public Actor create() throws Exception {
            return clazz.getConstructor(List.class).newInstance(stepList);
         }
      });
      return context.actorOf(new Props(new UntypedActorFactory() {
         @Override
         public Actor create() throws Exception {
            return new MultiStepHub<>(multiStepProps);
         }
      }));
   }
}
