package com.nickuc.login.storage.settings;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.auth.login.PrimaryLoginHandler;
import com.nickuc.login.config.PasswordHashLoader;
import com.nickuc.login.platform.sender.OutgoingSenderAdapter;
import java.sql.ResultSet;
import com.nickuc.login.storage.password.PasswordHashRepository;
import com.nickuc.login.storage.password.PasswordStore;

public abstract class InternalSettingsGateway extends PasswordHashRepository {
   private final String name;
   public String activeName;

   public InternalSettingsGateway(PasswordStore target, MessageOption input, String output, boolean context) {
      super(target, input, context);
      this.name = output;
   }

   public abstract void dispatchPasswordHashLoader(PasswordHashLoader target);

   @Override
   public void handleOutgoingSenderAdapter(OutgoingSenderAdapter target) {
      PasswordHashLoader input = new PasswordHashLoader(this.name, this.retrieveFile());
      this.dispatchPasswordHashLoader(input);
      if (this.activeName != null) {
         this.processTask();
      } else {
         this.performPasswordHashLoader(input);
      }

      this.sendOutgoingSenderAdapter(target);
   }

   public abstract void performPasswordHashLoader(PasswordHashLoader target);

   private void processTask() {
      if (this.activeName == null) {
         throw new IllegalArgumentException("Table name cannot be null!");
      }

      if (this.activeName.isEmpty()) {
         throw new IllegalArgumentException("Table name cannot be empty!");
      }

      this.processMessage(this.activeName);
      try (PrimaryLoginHandler target = this.sharedListenerContract.buildPrimaryLoginHandler("SELECT * FROM `" + this.activeName + "`")) {
         ResultSet input = target.resolveObject();

         while (input.next() && this.passwordStore.resolveState()) {
            try {
               this.dispatchSet(input);
            } finally {
               this.pendingTimestamp++;
            }
         }
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public abstract void dispatchSet(ResultSet target);
}
