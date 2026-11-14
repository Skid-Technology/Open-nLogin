package com.nickuc.login.storage.login;

import com.nickuc.login.protocol.LocaleAdapter;
import com.nickuc.login.protocol.LocaleInterceptor;
import com.nickuc.login.protocol.MessageAdapter;
import com.nickuc.login.protocol.MessageHandler;
import com.nickuc.login.storage.password.PasswordStore;


public class LoginDao {
   public final LocaleInterceptor localeInterceptor;
   private final PasswordStore passwordStore;
   public final MessageHandler messageHandler = new MessageHandler(this);
   public final MessageAdapter messageAdapter;
   public final LocaleAdapter localeAdapter = new LocaleAdapter(this);

   public LoginDao(PasswordStore target) {
      this.localeInterceptor = new LocaleInterceptor(this);
      this.messageAdapter = new MessageAdapter(this);
      this.passwordStore = target;
   }

   public static PasswordStore processPasswordStore(LoginDao dao) {
      return dao.passwordStore;
   }
}
