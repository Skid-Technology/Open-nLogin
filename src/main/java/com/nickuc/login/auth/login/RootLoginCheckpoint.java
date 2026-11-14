package com.nickuc.login.auth.login;

import com.nickuc.login.model.PrivateLoginOption;

public class RootLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[PrivateLoginOption.PRIVATE_LOGIN_OPTION.ordinal()] = 1;
      } catch (NoSuchFieldError output) {
      }

      try {
         values[PrivateLoginOption.ACTIVE_PRIVATELOGINOPTION.ordinal()] = 2;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[PrivateLoginOption.PENDING_PRIVATELOGINOPTION.ordinal()] = 3;
      } catch (NoSuchFieldError target) {
      }
   }
}
