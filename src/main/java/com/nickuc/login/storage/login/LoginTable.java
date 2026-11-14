package com.nickuc.login.storage.login;

import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.Connection;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;
import com.nickuc.login.storage.settings.ReadySettingsTable;


public class LoginTable implements SharedListenerContract {
   private final ReadySettingsTable readySettingsTable;
   private final DirectNoticeCatalog directNoticeCatalog;

   @Override
   public DirectNoticeCatalog resolveDirectNoticeCatalog() {
      return this.directNoticeCatalog;
   }

   @Override
   public void sendConnection(Connection target) {
      try {
         target.close();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public Connection resolveConnection() {
      return this.readySettingsTable.getConnection();
   }

   @Override
   public void executeTask() {
      this.readySettingsTable.executeTask();
   }

   public LoginTable(DirectNoticeCatalog target, ReadySettingsTable input) {
      this.directNoticeCatalog = target;
      this.readySettingsTable = input;
   }
}
