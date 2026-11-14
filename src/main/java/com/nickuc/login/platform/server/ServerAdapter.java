package com.nickuc.login.platform.server;

import com.nickuc.login.api.nLoginAPI;
import com.nickuc.login.premium.FloodgateResolver;
import com.nickuc.login.premium.SettingsLinker;
import com.nickuc.login.platform.account.InternalAccountHandler;

public interface ServerAdapter {
   boolean retrieveState();

   default SettingsLinker resolveSettingsLinker() {
      return null;
   }

   void performTask();

   nLoginAPI loadNLoginAPI();

   InternalAccountHandler getInternalAccountHandler();

   void executeTask();

   FloodgateResolver fetchFloodgateResolver();
}
