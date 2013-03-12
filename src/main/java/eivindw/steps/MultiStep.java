package eivindw.steps;

import akka.actor.ActorRef;
import eivindw.messages.Result;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiStep<T> extends AbstractStep<T> {

   private List<ActorRef> stepList = new ArrayList<>();
   private ActorRef handler;
   private int numberOfResults = 0;
   private T input;

   public MultiStep(List<ActorRef> stepList) {
      this.stepList = stepList;
   }

   @Override
   protected void handleInput(T input) {
      this.input = input;
      handler = getSender();
      executeSubSteps();
   }

   @Override
   protected void handleResult(Result resultat) {
      if(resultat.isType(input.getClass())) {
         input = (T) resultat.getData();
      } else {
         resultat.applyTo(input);
      }
      if (++numberOfResults == stepList.size()) {
         handler.tell(new Result(input), getSelf());
      } else {
         missingResults();
      }
   }

   public List<ActorRef> getStepList() {
      return stepList;
   }

   public T getInput() {
      return input;
   }

   protected abstract void executeSubSteps();

   protected void missingResults() {
   }
}
