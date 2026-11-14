package com.nickuc.login.storage.settings;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.config.SettingsDefinition;
import com.nickuc.login.storage.password.PasswordStore;

public class DeadSettingsRepository extends SettingsDefinition {
   public DeadSettingsRepository(PasswordStore target) {
      super(target, MessageOption.ACTIVE_AUTHENTICATED_MESSAGEOPTION);
   }
}
