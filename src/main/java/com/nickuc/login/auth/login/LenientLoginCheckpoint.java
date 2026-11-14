package com.nickuc.login.auth.login;

import com.nickuc.login.storage.notice.DirectNoticeCatalog;

public class LenientLoginCheckpoint {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[DirectNoticeCatalog.DIRECT_NOTICE_CATALOG.ordinal()] = 1;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[DirectNoticeCatalog.ACTIVE_DIRECTNOTICECATALOG.ordinal()] = 2;
      } catch (NoSuchFieldError target) {
      }
   }
}
