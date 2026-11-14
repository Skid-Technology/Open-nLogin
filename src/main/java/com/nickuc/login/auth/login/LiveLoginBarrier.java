package com.nickuc.login.auth.login;



public class LiveLoginBarrier {
   private final String name;
   private final long timestamp;
   private final long activeTimestamp;

   public LiveLoginBarrier(String target, long input, long context) {
      this.name = target;
      this.timestamp = input;
      this.activeTimestamp = context;
   }

   public static String buildMessage(LiveLoginBarrier barrier) {
      return barrier.name;
   }

   public static long processTime(LiveLoginBarrier barrier) {
      return barrier.timestamp;
   }

   public static long processTimeForTime(LiveLoginBarrier barrier) {
      return barrier.activeTimestamp;
   }
}
