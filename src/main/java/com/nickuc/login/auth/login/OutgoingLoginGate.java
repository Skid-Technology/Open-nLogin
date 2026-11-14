package com.nickuc.login.auth.login;

import com.nickuc.login.api.enums.LoginType;

public class OutgoingLoginGate {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[LoginType.REGISTER.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[LoginType.LOGIN.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
