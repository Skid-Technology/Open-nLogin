package com.nickuc.login.auth.login;

import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LowLoginService extends LocalLoginHandler<PreparedStatement> {
   public LowLoginService(SharedListenerContract target, Connection input, PreparedStatement output) {
      super(target, input, output);
   }

   @Override
   public void close() {
      try {
         this.resolveObject().close();
      } catch (SQLException e) {
      }
      this.sharedListenerContract.sendConnection(this.connection);
   }
}
