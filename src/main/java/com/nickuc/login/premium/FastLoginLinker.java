package com.nickuc.login.premium;

import com.nickuc.login.auth.login.LiveLoginCheckpoint;
import com.nickuc.login.auth.login.PrimaryLoginHandler;
import com.nickuc.login.config.PasswordHashContainer;
import com.nickuc.login.config.PasswordHashLoader;
import com.nickuc.login.model.OutgoingSpawnState;
import com.nickuc.login.platform.session.NestedSessionHandler;
import com.nickuc.login.platform.listener.SharedListenerContract;
import com.nickuc.login.storage.settings.CachedSettingsGateway;
import com.nickuc.login.storage.password.LocalPasswordTable;
import com.nickuc.login.storage.settings.LocalSettingsRepository;
import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.storage.spawn.SpawnState;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class FastLoginLinker implements NestedSessionHandler {
   public static final FastLoginLinker fastLoginLinker = new FastLoginLinker();

   @Override
   public boolean checkState(PasswordStore target, LocalSettingsRepository input, SharedListenerContract output) {
      if (!LocalPasswordTable.canState(output, SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]))) {
         return false;
      }

      String context = SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]);
      switch (output.resolveDirectNoticeCatalog()) {
         case DIRECT_NOTICE_CATALOG:
         case ACTIVE_DIRECTNOTICECATALOG:
            try (PrimaryLoginHandler item = output.buildPrimaryLoginHandler("SHOW COLUMNS FROM `" + context + "`")) {
               ResultSet element = item.resolveObject();
               while (element.next()) {
                  String content = element.getString("Field");
                  if (content.equals("premium")) {
                     return true;
                  }
               }
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
            return false;
         case CURRENT_DIRECTNOTICECATALOG:
            try (PrimaryLoginHandler data = output.buildPrimaryLoginHandler("PRAGMA table_info(" + context + ")")) {
               ResultSet value = data.resolveObject();
               while (value.next()) {
                  String result = value.getString("name");
                  if ("premium".equals(result)) {
                     return true;
                  }
               }
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
            return false;
         default:
            throw new IllegalArgumentException("Invalid database type! " + output.resolveDirectNoticeCatalog());
      }
   }

   @Override
   public void savePasswordStore(PasswordStore target, LocalSettingsRepository input, SharedListenerContract output) {
      LiveLoginCheckpoint context = new LiveLoginCheckpoint();
      if (CachedSettingsGateway.loadState()) {
         PasswordHashContainer.performMessage("");
         PasswordHashContainer.performMessage("O servidor exige uma conversão do banco de dados. (" + this.retrieveMessage() + ")");
         PasswordHashContainer.performMessage("NÃO DESLIGUE O SEU SERVIDOR (mesmo que o processo pareça travado)");
         PasswordHashContainer.performMessage("");
      } else {
         PasswordHashContainer.performMessage("");
         PasswordHashContainer.performMessage("Server requires conversion of database. (" + this.retrieveMessage() + ")");
         PasswordHashContainer.performMessage("DO NOT TURN OFF YOUR SERVER (even if the process seems stuck)");
         PasswordHashContainer.performMessage("");
      }

      String data = SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]);
      String value = data + "_101";
      if (LocalPasswordTable.canState(output, value)) {
         this.a(output, data, data + "_1023");
         this.a(output, value, data);
      }

      this.a(output, data, value);
      input.handleState(false);
      PasswordHashLoader result = target.retrievePasswordHashLoader();
      String[] request = new String[]{
         OutgoingSpawnState.OUTGOING_SPAWN_STATE.getName(),
         OutgoingSpawnState.ACTIVE_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.CURRENT_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.MAIN_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.LOCAL_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.REMOTE_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.CACHED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.STORED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.VERIFIED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.AUTHENTICATED_OUTGOINGSPAWNSTATE.getName()
      };
      String[] response = new String[]{
         result.a("database.table.account.columns.id", "id"),
         result.a("database.table.account.columns.real_name", "realname"),
         result.a("database.table.account.columns.unique_id", "uniqueId"),
         result.a("database.table.account.columns.premium_id", "premiumId"),
         OutgoingSpawnState.MAIN_OUTGOINGSPAWNSTATE.getName(),
         result.a("database.table.account.columns.address", "address"),
         result.a("database.table.account.columns.last_login", "lastlogin"),
         result.a("database.table.account.columns.reg_date", "regdate"),
         OutgoingSpawnState.STORED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.VERIFIED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.AUTHENTICATED_OUTGOINGSPAWNSTATE.getName()
      };
      output.saveMessage(String.format("INSERT INTO " + data + " (%s) SELECT %s FROM " + value, String.join(", ", request), String.join(", ", response)));
      PasswordHashContainer.performMessage("Conversion finished! (" + context.loadMessage(TimeUnit.SECONDS, 3) + "s)");
   }

   @Override
   public String retrieveMessage() {
      return "nLogin102Converter";
   }
}
