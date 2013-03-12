package eivindw.steps;

import eivindw.messages.Result;

public abstract class SynchronousEndStep<T> extends AbstractStep<T> {

   @Override
   protected void handleInput(T input) {
      getSender().tell(handle(input), getSelf());
   }

   protected abstract Result<T> handle(T input);
}
