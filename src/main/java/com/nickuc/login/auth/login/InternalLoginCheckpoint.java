package com.nickuc.login.auth.login;

import com.nickuc.login.model.RemotePremiumState;

public class InternalLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[RemotePremiumState.REMOTE_PREMIUM_STATE.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[RemotePremiumState.ACTIVE_REMOTEPREMIUMSTATE.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
