package eivindw.steps;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class MultiStepHub<T> extends UntypedActor implements Step<T> {

   private final Props multiStepProps;

   public MultiStepHub(Props multiStepProps) {
      this.multiStepProps = multiStepProps;
   }

   @Override
   public void onReceive(Object message) throws Exception {
      final ActorRef multiStep = getContext().actorOf(multiStepProps);
      multiStep.forward(message, getContext());
   }
}
