package com.nickuc.login.auth.login;

import com.nickuc.login.platform.listener.SharedListenerContract;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class DeadLoginGate implements SharedListenerContract {
   private final String name;
   private final Driver driver;
   private final Properties properties;
   private final DirectNoticeCatalog directNoticeCatalog;

   public DeadLoginGate(DirectNoticeCatalog target, Driver input, String output, Properties context) {
      this.directNoticeCatalog = target;
      this.driver = input;
      this.name = output;
      this.properties = context;
   }

   @Override
   public void executeTask() {
   }

   @Override
   public Connection resolveConnection() {
      try {
         return this.driver.connect(this.name, this.properties);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void sendConnection(Connection target) {
      try {
         target.close();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public DirectNoticeCatalog resolveDirectNoticeCatalog() {
      return this.directNoticeCatalog;
   }
}
