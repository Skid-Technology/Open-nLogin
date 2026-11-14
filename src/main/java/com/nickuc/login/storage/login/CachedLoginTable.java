package com.nickuc.login.storage.login;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.storage.settings.SettingsTable;

public class CachedLoginTable extends SettingsTable {
   public CachedLoginTable(PasswordStore target) {
      super(target, MessageOption.ACTIVE_LOCAL_MESSAGEOPTION, "data.yml", "Login", "Senha", "IP");
   }
}
