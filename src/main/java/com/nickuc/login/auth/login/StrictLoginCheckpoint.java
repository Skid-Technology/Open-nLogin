package com.nickuc.login.auth.login;

import com.nickuc.login.model.PrivateLoginOption;

public class StrictLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[PrivateLoginOption.CACHED_PRIVATELOGINOPTION.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[PrivateLoginOption.STORED_PRIVATELOGINOPTION.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
