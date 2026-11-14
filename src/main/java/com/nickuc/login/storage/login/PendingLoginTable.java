package com.nickuc.login.storage.login;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.storage.update.UpdateGateway;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;

public class PendingLoginTable extends UpdateGateway {
   public PendingLoginTable(PasswordStore target) {
      super(target, MessageOption.ACTIVE_UPSTREAM_MESSAGEOPTION, DirectNoticeCatalog.ACTIVE_DIRECTNOTICECATALOG, DirectNoticeCatalog.CURRENT_DIRECTNOTICECATALOG);
   }
}
