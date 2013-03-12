package eivindw.steps;

import akka.actor.ActorRef;
import akka.dispatch.Futures;
import eivindw.messages.Result;

import java.util.concurrent.Callable;

public abstract class AsynchronousEndStep<T> extends AbstractStep<T> {

   @Override
   protected void handleInput(final T input) {
      final ActorRef sender = getSender();

      Futures.future(new Callable<Void>() {
         @Override
         public Void call() throws Exception {
            sender.tell(handle(input), getSelf());

            return null;
         }
      }, getContext().dispatcher());
   }

   protected abstract Result<T> handle(final T input) throws Exception;
}
