package com.nickuc.login.auth.login;

import com.nickuc.login.account.StrictMessageKind;

public class DirectLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[StrictMessageKind.STRICT_MESSAGE_KIND.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[StrictMessageKind.ACTIVE_STRICTMESSAGEKIND.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
