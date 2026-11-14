package com.nickuc.login.storage.login;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.storage.settings.SettingsTable;

public class PrimaryLoginTable extends SettingsTable {
   public PrimaryLoginTable(PasswordStore target) {
      super(target, MessageOption.SAFE_MESSAGEOPTION, "logins.yml", "Login", "senha", null);
   }
}
