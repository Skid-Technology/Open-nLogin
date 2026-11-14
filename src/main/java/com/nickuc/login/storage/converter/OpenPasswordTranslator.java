package com.nickuc.login.storage.converter;

import com.nickuc.login.storage.platform.IncomingPlatformCatalog;


public class OpenPasswordTranslator {
   private final String name;
   private final String activeName;
   private final String pendingName;
   private final String currentName;
   private final IncomingPlatformCatalog incomingPlatformCatalog;
   private final String primaryName;
   private final int count;

   @Override
   public String toString() {
      return "LibreLogin.StorageConfig(storageType="
         + this.incomingPlatformCatalog
         + ", database="
         + this.name
         + ", host="
         + this.pendingName
         + ", user="
         + this.currentName
         + ", password="
         + this.activeName
         + ", port="
         + this.count
         + ", path="
         + this.primaryName
         + ")";
   }

   public OpenPasswordTranslator(IncomingPlatformCatalog target, String input, String output, String context, String data, int value, String result) {
      this.incomingPlatformCatalog = target;
      this.name = input;
      this.pendingName = output;
      this.currentName = context;
      this.activeName = data;
      this.count = value;
      this.primaryName = result;
   }

   public static PasswordTranslator getPasswordTranslator() {
      return new PasswordTranslator();
   }

   public static String buildMessage(OpenPasswordTranslator translator) {
      return translator.primaryName;
   }


   public static com.nickuc.login.storage.platform.IncomingPlatformCatalog loadIncomingPlatformCatalog(OpenPasswordTranslator translator) {
      return translator.incomingPlatformCatalog;
   }

   public static int computeCount(OpenPasswordTranslator translator) {
      return translator.count;
   }

   public static String resolveMessage(OpenPasswordTranslator translator) {
      return translator.pendingName;
   }

   public static String computeMessage(OpenPasswordTranslator translator) {
      return translator.name;
   }

   public static String createMessage(OpenPasswordTranslator translator) {
      return translator.currentName;
   }

   public static String computeMessageForMessage(OpenPasswordTranslator translator) {
      return translator.activeName;
   }

}
