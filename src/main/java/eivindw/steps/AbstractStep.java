package eivindw.steps;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import eivindw.messages.Input;
import eivindw.messages.Result;

public abstract class AbstractStep<T> extends UntypedActor implements Step<T> {

   protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);

   @Override
   public void onReceive(Object message) throws Exception {
      if(message instanceof Input) {
         T input = ((Input<T>) message).getData();
         handleInput(input);
      } else if(message instanceof Result) {
         handleResult((Result) message);
      } else {
         unhandled(message);
      }
   }

   protected String navn() {
      return String.format("%s (%s)", this.getClass().getSimpleName(), getSelf().path());
   }

   protected void handleResult(Result result) {
      throw new RuntimeException(String.format("[UNHANDLED RESULT] %s: %s", navn(), result));
   }

   protected abstract void handleInput(T input);
}
