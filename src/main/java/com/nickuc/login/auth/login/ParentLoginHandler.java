package com.nickuc.login.auth.login;

import com.nickuc.login.platform.listener.InternalListenerContract;


public class ParentLoginHandler {
   private final Object object;
   private final SecureLoginGate secureLoginGate;
   private final InternalListenerContract internalListenerContract;

   public static Object computeObject(ParentLoginHandler handler) {
      return handler.object;
   }

   public ParentLoginHandler(InternalListenerContract target, SecureLoginGate input, Object output) {
      this.internalListenerContract = target;
      this.secureLoginGate = input;
      this.object = output;
   }


   public static com.nickuc.login.platform.listener.InternalListenerContract computeInternalListenerContract(ParentLoginHandler handler) {
      return handler.internalListenerContract;
   }

   public static SecureLoginGate resolveSecureLoginGate(ParentLoginHandler handler) {
      return handler.secureLoginGate;
   }

}
