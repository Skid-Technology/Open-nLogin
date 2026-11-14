package com.nickuc.login.auth.login;

import com.nickuc.login.model.LoudPremiumOption;

public class PrimaryLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[LoudPremiumOption.CURRENT_LOUDPREMIUMOPTION.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[LoudPremiumOption.ACTIVE_LOUDPREMIUMOPTION.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
