package eivindw.messages;

public abstract class Message<D> {

   private D data;

   protected Message(D data) {
      this.data = data;
   }

   public D getData() {
      return data;
   }

   @Override
   public String toString() {
      return data.toString();
   }
}
