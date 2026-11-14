package com.nickuc.login.auth.login;

import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrimaryLoginHandler extends LocalLoginHandler<ResultSet> {
   private final PreparedStatement preparedStatement;

   public PrimaryLoginHandler(SharedListenerContract target, Connection input, PreparedStatement output, ResultSet context) {
      super(target, input, context);
      this.preparedStatement = output;
   }

   @Override
   public void close() {
      try {
         this.resolveObject().close();
      } catch (SQLException e) {
      }
      try {
         this.preparedStatement.close();
      } catch (SQLException e) {
      }
      this.sharedListenerContract.sendConnection(this.connection);
   }
}
