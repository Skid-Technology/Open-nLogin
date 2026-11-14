package com.nickuc.login.storage.settings;

import java.util.List;

import com.nickuc.login.account.MessageOption;
import com.nickuc.login.config.PasswordHashContainer;
import com.nickuc.login.config.PasswordHashLoader;
import com.nickuc.login.platform.sender.OutgoingSenderAdapter;
import java.util.Set;
import com.nickuc.login.storage.password.PasswordHashRepository;
import com.nickuc.login.storage.password.PasswordStore;

public abstract class PendingSettingsGateway extends PasswordHashRepository {
   private final String name;
   private final String activeName;

   @Override
   public void handleOutgoingSenderAdapter(OutgoingSenderAdapter target) {
      PasswordHashLoader input = new PasswordHashLoader(this.activeName, this.retrieveFile());
      Set output = input.loadSet(this.name);
      this.timestamp = output.size();

      for (String data : (List<String>) output) {
         try {
            String value = this.name + "." + data + ".";
            this.handlePasswordHashLoader(input, data, value);
         } catch (Exception source) {
            PasswordHashContainer.processMessage(
               "[" + this.messageOption.getName() + "] " + (data == null ? "Unknown" : data + "'s") + " data could not be converted.", source
            );
         } finally {
            this.pendingTimestamp++;
         }
      }

      output.clear();
      this.sendOutgoingSenderAdapter(target);
   }

   public PendingSettingsGateway(PasswordStore target, MessageOption input, String output, String context) {
      super(target, input);
      this.activeName = output;
      this.name = context;
   }

   public abstract void handlePasswordHashLoader(PasswordHashLoader target, String input, String output);
}
