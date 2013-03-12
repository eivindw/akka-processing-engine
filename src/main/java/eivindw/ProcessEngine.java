package eivindw;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import eivindw.definition.StepDefinition;
import eivindw.messages.ProsessInfo;
import eivindw.messages.Result;
import scala.concurrent.duration.Duration;

public class ProcessEngine extends UntypedActor {

   private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

   private SupervisorStrategy strategy = new OneForOneStrategy(
      2,
      Duration.Inf(),
      new Function<Throwable, SupervisorStrategy.Directive>() {
         @Override
         public SupervisorStrategy.Directive apply(Throwable t) {
            log.info("Jobbinfo ERROR: " + t.getMessage());
            return SupervisorStrategy.restart();
         }
      }
   );

   @Override
   public SupervisorStrategy supervisorStrategy() {
      return strategy;
   }

   @Override
   public void onReceive(Object message) {
      if(message instanceof ProsessInfo) {
         ProsessInfo prosessInfo = (ProsessInfo) message;
         StepDefinition startSteg = prosessInfo.getProcessDefinition().createTopLevelStep();
         log.info("Jobbinfo START: " + prosessInfo.getInput());
         ActorRef actor = startSteg.toActor(getContext());
         actor.tell(prosessInfo.getInput(), getSelf());
      } else if(message instanceof Result) {
         log.info("Jobbinfo SUCCESS: " + ((Result) message).getData());
      } else {
         unhandled(message);
      }
   }
}
