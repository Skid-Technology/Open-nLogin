package com.nickuc.login.auth.login;

import com.nickuc.login.model.SecondaryPlatformCatalog;

public class PendingLoginProcessor {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[SecondaryPlatformCatalog.ACTIVE_SECONDARYPLATFORMCATALOG.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[SecondaryPlatformCatalog.PENDING_SECONDARYPLATFORMCATALOG.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
