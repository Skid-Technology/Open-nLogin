package com.nickuc.login.platform.account;

import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.platform.player.LoudPlayerContract;

public interface UpstreamAccountHandler extends LoudPlayerContract {
   boolean at();

   default boolean loadState() {
      return true;
   }

   boolean canState(PasswordStore target);
}
