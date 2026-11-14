package com.nickuc.login.auth.login;



public class LocalLoginGate {
   private final boolean enabled;
   private final boolean activeEnabled;
   private final long timestamp;

   public LocalLoginGate(long target, boolean output, boolean context) {
      this.timestamp = target;
      this.activeEnabled = output;
      this.enabled = context;
   }

   public static boolean isState(LocalLoginGate gate) {
      return gate.activeEnabled;
   }

   public static boolean validateState(LocalLoginGate gate) {
      return gate.enabled;
   }

   public static long loadTime(LocalLoginGate gate) {
      return gate.timestamp;
   }
}
