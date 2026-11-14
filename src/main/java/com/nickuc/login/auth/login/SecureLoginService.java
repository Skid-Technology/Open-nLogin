package com.nickuc.login.auth.login;

import com.nickuc.login.platform.proxy.SilentProxyState;

public class SecureLoginService {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[SilentProxyState.SILENT_PROXY_STATE.ordinal()] = 1;
      } catch (NoSuchFieldError output) {
      }

      try {
         values[SilentProxyState.ACTIVE_SILENTPROXYSTATE.ordinal()] = 2;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[SilentProxyState.PENDING_SILENTPROXYSTATE.ordinal()] = 3;
      } catch (NoSuchFieldError target) {
      }
   }
}
