package com.nickuc.login.storage.update;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.auth.login.PrimaryLoginHandler;
import com.nickuc.login.auth.login.PrivateLoginHandler;
import com.nickuc.login.config.PasswordHashContainer;
import com.nickuc.login.platform.sender.OutgoingSenderAdapter;
import com.nickuc.login.premium.IndirectPasswordResolver;
import com.nickuc.login.premium.SpawnLookup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;
import com.nickuc.login.storage.password.PasswordHashRepository;
import com.nickuc.login.storage.password.PasswordStore;
import com.nickuc.login.storage.settings.LocalSettingsRepository;
import com.nickuc.login.storage.spawn.SpawnState;

public class UpdateGateway extends PasswordHashRepository {
   private final DirectNoticeCatalog directNoticeCatalog;
   private final DirectNoticeCatalog activeDirectNoticeCatalog;

   private boolean validateState(DirectNoticeCatalog target) {
      return (target == DirectNoticeCatalog.ACTIVE_DIRECTNOTICECATALOG || target == DirectNoticeCatalog.DIRECT_NOTICE_CATALOG)
         && (
            this.activeDirectNoticeCatalog == DirectNoticeCatalog.ACTIVE_DIRECTNOTICECATALOG
               || this.activeDirectNoticeCatalog == DirectNoticeCatalog.DIRECT_NOTICE_CATALOG
         );
   }

   @Override
   public void handleOutgoingSenderAdapter(OutgoingSenderAdapter target) {
      DirectNoticeCatalog input = this.passwordStore.fetchLocalSettingsRepository().loadSharedListenerContract().resolveDirectNoticeCatalog();
      if (input != this.activeDirectNoticeCatalog && !this.validateState(input)) {
         throw new IllegalStateException("Current backend type does not match with target backend!");
      }

      this.sharedListenerContract = LocalSettingsRepository.processSharedListenerContract(this.passwordStore, this.directNoticeCatalog, false);
      String output = SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]);
      this.processMessage(output);

      try {
         IndirectPasswordResolver context = this.passwordStore.findIndirectPasswordResolver();
         try (PrimaryLoginHandler data = this.sharedListenerContract
            .buildPrimaryLoginHandler("SELECT * FROM `" + SpawnState.CURRENT_SPAWNSTATE.a(new Object[0]) + "`")) {
            ResultSet value = data.resolveObject();

            while (value.next() && this.passwordStore.resolveState()) {
               String result = null;

               try {
                  SpawnLookup request = context.processSpawnLookup(value);
                  if (request == null) {
                     throw new Exception("Failed to build account. (" + this.directNoticeCatalog + ")");
                  }

                  result = request.retrieveMessage();
                  SpawnLookup response = context.computeSpawnLookup(request.retrieveMessage(), request.getMojangId(), request.getBedrockId(), false);
                  if (response == null) {
                     throw new Exception("Failed to build " + result + "'s account. (" + this.activeDirectNoticeCatalog + ")");
                  }

                  if (!response.fetchState()) {
                     if (!context.checkState(this.loginBarrier, request)) {
                        throw new Exception("Failed to update " + result + "'s account. (" + this.activeDirectNoticeCatalog + ")");
                     }

                     this.activeTimestamp++;
                  }
               } catch (Exception secondaryData) {
                  PasswordHashContainer.processMessage(
                     "[" + this.messageOption.getName() + "] " + (result == null ? "Unknown" : result + "'s") + " data could not be converted.", secondaryData
                  );
               } finally {
                  this.pendingTimestamp++;
               }
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }

         try (PrivateLoginHandler localInput = this.sharedListenerContract.getPrivateLoginHandler()) {
            Connection localOutput = localInput.retrieveConnection();
            try (PreparedStatement localContext = localOutput.prepareStatement("SELECT * FROM `" + SpawnState.INCOMING_SPAWNSTATE.a(new Object[0]) + "`");
                 ResultSet localData = localContext.executeQuery()) {
               while (localData.next() && this.passwordStore.resolveState()) {
                  String localValue = localData.getString(SpawnState.SECONDARY_SPAWNSTATE.a(new Object[0]));
                  byte[] source = localData.getBytes(SpawnState.DIRECT_SPAWNSTATE.a(new Object[0]));

                  try {
                     try (PreparedStatement entry = localOutput.prepareStatement(
                        String.format(
                           "UPDATE `%s` SET `%s` = ? WHERE `%s` = ?",
                           SpawnState.INCOMING_SPAWNSTATE.a(new Object[0]),
                           SpawnState.DIRECT_SPAWNSTATE.a(new Object[0]),
                           SpawnState.SECONDARY_SPAWNSTATE.a(new Object[0])
                        )
                     )) {
                        entry.setBytes(1, source);
                        entry.setString(2, localValue);
                        int record = entry.executeUpdate();
                        if (record == 0) {
                           try (PreparedStatement item = localOutput.prepareStatement(
                              String.format(
                                 "INSERT INTO `%s` (`%s`, `%s`) VALUES (?, ?)",
                                 SpawnState.INCOMING_SPAWNSTATE.a(new Object[0]),
                                 SpawnState.SECONDARY_SPAWNSTATE.a(new Object[0]),
                                 SpawnState.DIRECT_SPAWNSTATE.a(new Object[0])
                              )
                           )) {
                              item.setString(1, localValue);
                              item.setBytes(2, source);
                              item.execute();
                           }
                        }
                     }
                  } catch (Exception secondaryTarget) {
                     throw new RuntimeException("Unable to update or insert data with key \"" + localValue + "\"!", secondaryTarget);
                  }
               }
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      } finally {
         this.sharedListenerContract.executeTask();
      }

      this.sendOutgoingSenderAdapter(target);
   }

   public UpdateGateway(PasswordStore target, MessageOption input, DirectNoticeCatalog output, DirectNoticeCatalog context) {
      super(target, input);
      this.directNoticeCatalog = output;
      this.activeDirectNoticeCatalog = context;
   }
}
