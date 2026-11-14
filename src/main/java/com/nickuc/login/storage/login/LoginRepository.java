package com.nickuc.login.storage.login;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.storage.update.UpdateGateway;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;

public class LoginRepository extends UpdateGateway {
   public LoginRepository(PasswordStore target) {
      super(target, MessageOption.ACTIVE_INTERNAL_MESSAGEOPTION, DirectNoticeCatalog.CURRENT_DIRECTNOTICECATALOG, DirectNoticeCatalog.ACTIVE_DIRECTNOTICECATALOG);
   }
}
