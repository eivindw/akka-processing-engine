package eivindw.steps;

import akka.actor.ActorRef;
import eivindw.messages.Input;

import java.util.List;

public class ParallelMultiStep<T> extends MultiStep<T> {

   public ParallelMultiStep(List<ActorRef> stepList) {
      super(stepList);
   }

   @Override
   protected void executeSubSteps() {
      for(ActorRef step : getStepList()) {
         step.tell(new Input<>(getInput()), getSelf());
      }
   }
}
