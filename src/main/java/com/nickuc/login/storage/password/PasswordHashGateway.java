package com.nickuc.login.storage.password;

import java.util.Map;

import com.nickuc.login.auth.login.LiveLoginCheckpoint;
import com.nickuc.login.auth.login.LocalLoginGate;
import com.nickuc.login.auth.locale.OpenLocaleBarrier;
import com.nickuc.login.auth.login.PrimaryLoginHandler;
import com.nickuc.login.config.PasswordHashContainer;
import com.nickuc.login.model.OutgoingSpawnState;
import com.nickuc.login.platform.session.NestedSessionHandler;
import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.nickuc.login.storage.settings.LocalSettingsRepository;
import com.nickuc.login.storage.settings.CachedSettingsGateway;
import com.nickuc.login.storage.spawn.SpawnState;

public class PasswordHashGateway implements NestedSessionHandler {
   public static final PasswordHashGateway passwordHashGateway = new PasswordHashGateway();

   @Override
   public boolean checkState(PasswordStore target, LocalSettingsRepository input, SharedListenerContract output) {
      if (LocalPasswordTable.canState(output, SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]))
         && LocalPasswordTable.canState(output, SpawnState.INCOMING_SPAWNSTATE.a(new Object[0]))) {
         if (LocalPasswordTable.canState(output, SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]) + "_102")) {
            return false;
         }

         try (PrimaryLoginHandler context = output.buildPrimaryLoginHandler(
            "SELECT 1 FROM `" + SpawnState.INCOMING_SPAWNSTATE.a(new Object[0]) + "` WHERE `" + SpawnState.SECONDARY_SPAWNSTATE.a(new Object[0]) + "` = ?",
            "server_uuid"
         )) {
            ResultSet data = context.resolveObject();
            return data.next();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      } else {
         return false;
      }
   }

   @Override
   public String retrieveMessage() {
      return "nLogin103Converter";
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
      String value = data + "_102";
      this.a(output, data, value);
      input.handleState(true);
      String[] result = new String[]{
         OutgoingSpawnState.OUTGOING_SPAWN_STATE.getName(),
         OutgoingSpawnState.ACTIVE_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.CURRENT_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.PRIMARY_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.MAIN_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.LOCAL_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.REMOTE_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.CACHED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.STORED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.VERIFIED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.AUTHENTICATED_OUTGOINGSPAWNSTATE.getName()
      };
      String[] request = new String[]{
         OutgoingSpawnState.OUTGOING_SPAWN_STATE.getName(),
         OutgoingSpawnState.ACTIVE_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.CURRENT_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.PRIMARY_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.MAIN_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.LOCAL_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.REMOTE_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.CACHED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.STORED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.VERIFIED_OUTGOINGSPAWNSTATE.getName(),
         OutgoingSpawnState.AUTHENTICATED_OUTGOINGSPAWNSTATE.getName()
      };
      output.saveMessage(String.format("INSERT INTO " + data + " (%s) SELECT %s FROM " + value, String.join(", ", result), String.join(", ", request)));
      output.saveMessage(
         String.format("DELETE FROM `%s` WHERE `%s` = ?", SpawnState.INCOMING_SPAWNSTATE.a(new Object[0]), SpawnState.SECONDARY_SPAWNSTATE.a(new Object[0])),
         "server_uuid"
      );
      PasswordHashContainer.performMessage("Deleting duplicated accounts...");
      Integer count = output.computeStrictLoginHandler(
            String.format(
               "DELETE FROM `" + data + "` WHERE `%s` IS NULL AND `%s` IS NULL AND `%s` IS NULL AND `%s` IS NULL OR `%s` = ?",
               OutgoingSpawnState.MAIN_OUTGOINGSPAWNSTATE.getName(),
               OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
               OutgoingSpawnState.CURRENT_OUTGOINGSPAWNSTATE.getName(),
               OutgoingSpawnState.PRIMARY_OUTGOINGSPAWNSTATE.getName(),
               OutgoingSpawnState.MAIN_OUTGOINGSPAWNSTATE.getName()
            ),
            "transferred"
         )
         .resolveObject();
      PasswordHashContainer.performMessage("Deleted " + OpenLocaleBarrier.resolveMessage(count.intValue()) + " duplicated accounts");
      PasswordHashContainer.performMessage("Fetching duplicated UUIDs...");
      Map<String, List<LocalLoginGate>> response = new HashMap<>();
      try (PrimaryLoginHandler source = output.buildPrimaryLoginHandler(
         String.format(
            "SELECT `%s`, COUNT(`%s`) FROM " + data + " GROUP BY `%s` HAVING COUNT(`%s`) > 1",
            OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
            OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
            OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
            OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName()
         )
      )) {
         ResultSet entry = source.resolveObject();

         while (entry.next()) {
            response.computeIfAbsent(entry.getString(1), instance -> new ArrayList<>());
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

      PasswordHashContainer.performMessage("Fetching duplicated UUIDs accounts...");
      Connection event = output.resolveConnection();

      try {
         for (String element : new HashSet<String>(response.keySet())) {
            try (PreparedStatement packet = event.prepareStatement(
               String.format(
                  "SELECT `%s`, `%s`, `%s` FROM " + data + " WHERE `%s` = ?",
                  OutgoingSpawnState.OUTGOING_SPAWN_STATE.getName(),
                  OutgoingSpawnState.CURRENT_OUTGOINGSPAWNSTATE.getName(),
                  OutgoingSpawnState.PRIMARY_OUTGOINGSPAWNSTATE.getName(),
                  OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName()
               )
            )) {
               packet.setString(1, element);
               ResultSet content = packet.executeQuery();

               while (content.next()) {
                  List<LocalLoginGate> payload = response.computeIfAbsent(element, instance -> new ArrayList<>());
                  payload.add(new LocalLoginGate(content.getLong(1), content.getString(2) != null, content.getString(3) != null));
               }
            }
         }
      } catch (SQLException argument) {
         throw new RuntimeException(argument);
      } finally {
         output.sendConnection(event);
      }

      PasswordHashContainer.performMessage("Deleting duplicated UUIDs... (" + OpenLocaleBarrier.resolveMessage(response.size()) + ")");

      label178:
      for (List<LocalLoginGate> account : response.values()) {
         Long player = null;

         for (LocalLoginGate identity : account) {
            if (LocalLoginGate.isState(identity)) {
               break label178;
            }

            if (!LocalLoginGate.validateState(identity)) {
               if (player != null) {
                  break label178;
               }

               player = LocalLoginGate.loadTime(identity);
            }
         }

         if (player != null) {
            output.saveMessage(
               String.format(
                  "UPDATE `" + data + "` SET `%s` = ? WHERE `%s` = ?",
                  OutgoingSpawnState.PENDING_OUTGOINGSPAWNSTATE.getName(),
                  OutgoingSpawnState.OUTGOING_SPAWN_STATE.getName()
               ),
               null,
               player
            );
         }
      }

      PasswordHashContainer.performMessage("Conversion finished! (" + context.loadMessage(TimeUnit.SECONDS, 3) + "s)");
   }
}
