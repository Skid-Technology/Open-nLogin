package com.nickuc.login.auth.login;

import com.nickuc.login.model.PremiumState;

public class DeadLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[PremiumState.CURRENT_PREMIUMSTATE.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[PremiumState.PRIMARY_PREMIUMSTATE.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
