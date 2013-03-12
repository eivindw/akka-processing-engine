package eivindw.definition;

import akka.actor.*;
import eivindw.steps.Step;

public class ClassStepDefinition<T> extends StepDefinition<T> {

   private final Class<? extends Step<T>> stegklasse;
   private StepDefinition understeg;

   protected ClassStepDefinition(Class<? extends Step<T>> stegklasse) {
      this.stegklasse = stegklasse;
   }

   @Override
   public ActorRef toActor(final ActorRefFactory context) {
      ActorRef ref = context.actorFor(stegklasse.getSimpleName());
      if(ref.isTerminated()) {
         if(understeg != null) {
            ref = context.actorOf(new Props(new UntypedActorFactory() {
               @Override
               public Actor create() throws Exception {
                  return stegklasse.getConstructor(ActorRef.class).newInstance(understeg.toActor(context));
               }
            }), stegklasse.getSimpleName());
         } else {
            ref = context.actorOf(new Props(stegklasse), stegklasse.getSimpleName());
         }
      }
      return ref;
   }

   public <R> StepDefinition<T> med(StepDefinition<R> understeg) {
      this.understeg = understeg;
      return this;
   }
}
