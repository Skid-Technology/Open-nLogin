package com.nickuc.login.storage.password;

import com.nickuc.login.config.PasswordHashLoader;
import com.zaxxer.hikari.HikariConfig;
import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;
import com.nickuc.login.storage.settings.BusySettingsArchive;

public class LocalPasswordTable {
   public static boolean canState(SharedListenerContract instance, String target) {
      Connection input = instance.resolveConnection();

      try {
         try (ResultSet output = input.getMetaData().getTables(input.getCatalog(), null, "%", null)) {
            while (output.next()) {
               if (output.getString(3).equalsIgnoreCase(target)) {
                  return true;
               }
            }
            return false;
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      } finally {
         instance.sendConnection(input);
      }
   }

   public static PasswordRepository buildPasswordRepository(PasswordHashLoader instance, DirectNoticeCatalog target) {
      Properties input = new Properties();
      String output = instance.b("database.remote.hostname");
      String context = instance.b("database.remote.database");
      String data = instance.b("database.remote.username");
      String value = instance.b("database.remote.password");

      for (String request : instance.loadSet("database.remote.properties")) {
         String response = instance.b("database.remote.properties." + request);
         input.setProperty(request, response);
      }

      return PasswordRepository.processPasswordRepository(output, context, data, value, input, target.loadCount());
   }

   public static boolean validateState(SharedListenerContract instance, String target, String input) {
      Connection output = instance.resolveConnection();

      try {
         try (ResultSet context = output.getMetaData().getColumns(output.getCatalog(), null, target, input)) {
            return context.next();
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      } finally {
         instance.sendConnection(output);
      }
   }

   public static BusySettingsArchive createBusySettingsArchive(
      PasswordStore instance, DirectNoticeCatalog target, PasswordRepository input, @Nullable Consumer<HikariConfig> output
   ) {
      if (!target.fetchState()) {
         throw new IllegalArgumentException("Database " + target + " is not remote!");
      }

      switch (target) {
         case DIRECT_NOTICE_CATALOG:
            return PasswordTable.resolvePasswordTable(instance, input, output);
         case ACTIVE_DIRECTNOTICECATALOG:
            return PrimaryPasswordDao.resolvePrimaryPasswordDao(instance, input, output);
         default:
            throw new IllegalArgumentException("Unsupported database type! " + target);
      }
   }

   public static boolean isState(ResultSet instance, String target) {
      try {
         ResultSetMetaData input = instance.getMetaData();
         int output = input.getColumnCount();

         for (int context = 1; context <= output; context++) {
            if (target.equals(input.getColumnName(context))) {
               return true;
            }
         }

         return false;
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }
}
