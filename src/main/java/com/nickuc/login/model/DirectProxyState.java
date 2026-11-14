package com.nickuc.login.model;



public enum DirectProxyState {
   DIRECT_PROXY_STATE(0),
   ACTIVE_DIRECTPROXYSTATE(587),
   PENDING_DIRECTPROXYSTATE(465);

   private final int count;

   DirectProxyState(int output) {
      this.count = output;
   }

   public static DirectProxyState handleDirectProxyState(String instance, int target) {
      return loadDirectProxyState(instance, target);
   }

   private static DirectProxyState loadDirectProxyState(String instance, int target) {
      boolean input = "AUTO".equalsIgnoreCase(instance);

      for (DirectProxyState value : values()) {
         boolean result = input ? (value.count == target) : value.name().equalsIgnoreCase(instance);
         if (result) {
            return value;
         }
      }

      return DIRECT_PROXY_STATE;
   }
}
