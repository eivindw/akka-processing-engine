package eivindw.definition;

import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;

public abstract class StepDefinition<T> {
   public abstract ActorRef toActor(ActorRefFactory context);
}
