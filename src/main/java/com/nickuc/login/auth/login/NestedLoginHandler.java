package com.nickuc.login.auth.login;

import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class NestedLoginHandler implements SharedListenerContract {
   private Connection connection;

   @Override
   public synchronized void executeTask() {
      if (this.connection != null) {
         try {
            this.connection.close();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         } finally {
            this.connection = null;
         }
      }
   }

   @Override
   public void sendConnection(Connection target) {
   }

   @Override
   public synchronized Connection resolveConnection() {
      try {
         if (this.connection == null || this.connection.isClosed()) {
            this.connection = this.fetchConnection();
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

      return this.connection;
   }

   public abstract Connection fetchConnection();
}
