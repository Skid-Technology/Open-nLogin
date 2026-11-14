package com.nickuc.login.storage.settings;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.auth.login.PrimaryLoginHandler;
import com.nickuc.login.config.PasswordHashContainer;
import com.nickuc.login.config.PasswordHashLoader;
import com.nickuc.login.platform.sender.OutgoingSenderAdapter;
import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nullable;
import com.nickuc.login.storage.password.PasswordHashRepository;
import com.nickuc.login.storage.password.PasswordStore;

public abstract class StoredSettingsGateway extends PasswordHashRepository {
   public String name;
   public String activeName;
   public String pendingName;

   public PrimaryLoginHandler computePrimaryLoginHandler(SharedListenerContract target) {
      return target.buildPrimaryLoginHandler("SELECT * FROM `" + this.pendingName + "`");
   }

   private void sendTask() {
      if (this.pendingName == null) {
         throw new IllegalArgumentException("Table name cannot be null!");
      }

      if (this.pendingName.isEmpty()) {
         throw new IllegalArgumentException("Table name cannot be empty!");
      }

      this.processMessage(this.pendingName);

      try (PrimaryLoginHandler target = this.computePrimaryLoginHandler(this.sharedListenerContract)) {
         ResultSet input = target.resolveObject();

         while (input.next() && this.passwordStore.resolveState()) {
            try {
               this.performSet(input);
            } catch (Exception source) {
               PasswordHashContainer.processMessage(
                  "[" + this.messageOption.getName() + "] " + (this.activeName == null ? "Unknown" : this.activeName + "'s") + " data could not be converted.",
                  source
               );
            } finally {
               this.pendingTimestamp++;
            }
         }
      } catch (Exception record) {
         throw new RuntimeException(record);
      }
   }

   public abstract void performSet(ResultSet target) throws SQLException;

   public abstract void updatePasswordHashLoader(PasswordHashLoader target);

   public StoredSettingsGateway(PasswordStore target, MessageOption input, String output, @Nullable String context, boolean data) {
      super(target, input, data);
      this.name = output;
      this.pendingName = context;
   }

   public StoredSettingsGateway(PasswordStore target, MessageOption input, String output) {
      this(target, input, output, null);
   }

   @Override
   public void handleOutgoingSenderAdapter(OutgoingSenderAdapter target) {
      PasswordHashLoader input = this.computePasswordHashLoader(this.name);
      this.updatePasswordHashLoader(input);
      this.sendTask();
      this.sendOutgoingSenderAdapter(target);
   }

   public StoredSettingsGateway(PasswordStore target, MessageOption input, String output, @Nullable String context) {
      this(target, input, output, context, true);
   }

   public PasswordHashLoader computePasswordHashLoader(String target) {
      return new PasswordHashLoader(target, this.retrieveFile());
   }
}
