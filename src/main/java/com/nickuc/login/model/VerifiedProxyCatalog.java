package com.nickuc.login.model;



public enum VerifiedProxyCatalog {
   VERIFIED_PROXY_CATALOG(true),
   ACTIVE_VERIFIEDPROXYCATALOG(false),
   PENDING_VERIFIEDPROXYCATALOG(false),
   CURRENT_VERIFIEDPROXYCATALOG(false);

   private final boolean enabled;

   VerifiedProxyCatalog(boolean output) {
      this.enabled = output;
   }


   public static boolean canState(VerifiedProxyCatalog cat) {
      return cat != null && cat.enabled;
   }

}
