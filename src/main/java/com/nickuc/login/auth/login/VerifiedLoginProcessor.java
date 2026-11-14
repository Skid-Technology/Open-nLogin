package com.nickuc.login.auth.login;

import com.nickuc.login.account.LoudProxyState;

public class VerifiedLoginProcessor {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[LoudProxyState.PRIMARY_LOUDPROXYSTATE.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[LoudProxyState.MAIN_LOUDPROXYSTATE.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
