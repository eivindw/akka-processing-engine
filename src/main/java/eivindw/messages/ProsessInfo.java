package eivindw.messages;

import eivindw.definition.ProcessDefinition;
import eivindw.definition.StepDefinition;

public final class ProsessInfo {

   private final ProcessDefinition prosessdefinisjon;
   private final Object input;

   public ProsessInfo(final ProcessDefinition prosessdefinisjon, final Object input) {
      this.prosessdefinisjon = prosessdefinisjon;
      this.input = input;
   }

   public ProsessInfo(final Class stepClass, final Object input) {
      prosessdefinisjon = new ProcessDefinition() {
         @Override
         public StepDefinition createTopLevelStep() {
            return steg(stepClass);
         }
      };
      this.input = input;
   }

   public ProcessDefinition getProcessDefinition() {
      return prosessdefinisjon;
   }

   public Input getInput() {
      return new Input<>(input);
   }
}
