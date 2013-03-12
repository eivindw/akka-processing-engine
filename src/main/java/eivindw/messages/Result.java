package eivindw.messages;

public class Result<T> extends Message<Object> {

   public Result(Object data) {
      super(data);
   }

   public void applyTo(T value) {
      throw new RuntimeException("[DEFAULT] Applying " + getData() + " to " + value + " has no effect!");
   }

   public boolean isType(Class clazz) {
      return clazz.isInstance(getData());
   }
}
