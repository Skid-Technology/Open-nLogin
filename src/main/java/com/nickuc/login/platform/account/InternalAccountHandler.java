package com.nickuc.login.platform.account;

import com.nickuc.login.storage.password.PasswordStore;

import com.nickuc.login.storage.login.LoginCollection;
import com.nickuc.login.platform.connection.SecondaryConnectionContract;

public interface InternalAccountHandler extends IncomingAccountHandler {
   void processTask();

   void executeTask();

   LoginCollection getLoginCollection();

   SecondaryConnectionContract retrieveSecondaryConnectionContract();

   default void a(PasswordStore target, boolean input) {
      this.processPasswordStore(target, input);
   }

   default void processPasswordStore(PasswordStore target, boolean input) {
   }
}
