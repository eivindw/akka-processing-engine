package eivindw.steps;

import akka.actor.ActorRef;
import eivindw.messages.Input;

import java.util.List;

public class SerieltMultiStep<T> extends MultiStep<T> {

   private int stepPointer = 0;

   public SerieltMultiStep(List<ActorRef> stegliste) {
      super(stegliste);
   }

   @Override
   protected void executeSubSteps() {
      getStepList().get(stepPointer).tell(new Input<>(getInput()), getSelf());
      stepPointer++;
   }

   @Override
   protected void missingResults() {
      executeSubSteps();
   }
}
