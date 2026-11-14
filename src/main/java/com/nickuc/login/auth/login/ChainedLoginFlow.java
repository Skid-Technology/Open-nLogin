package com.nickuc.login.auth.login;

import net.lingala.zip4j.ZipFile;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class ChainedLoginFlow {
   public static void executeFile(File instance, File target) {
      try (ZipFile input = new ZipFile(instance)) {
         input.extractAll(target.toString());
      } catch (Exception value) {
         throw new RuntimeException(value);
      }
   }

   public static byte[] loadPayload(byte[] instance) {
      try {
         ByteArrayOutputStream target = new ByteArrayOutputStream();
         try (DeflaterOutputStream input = new DeflaterOutputStream(target)) {
            input.write(instance);
         }
         return target.toByteArray();
      } catch (IOException result) {
         throw new RuntimeException("Unable to compress with Gzip!", result);
      }
   }

   public static void sendFile(File instance, File target) {
      try (ZipFile input = new ZipFile(target)) {
         if (instance.isDirectory()) {
            input.addFolder(instance);
         } else {
            input.addFile(instance);
         }
      } catch (Exception value) {
         throw new RuntimeException(value);
      }
   }

   public static void dispatchCollection(List<File> instance, File target) {
      if (instance.isEmpty()) {
         throw new IllegalArgumentException("Input collection cannot be empty!");
      }

      try (ZipFile input = new ZipFile(target)) {
         for (File context : instance) {
            if (context.isDirectory()) {
               input.addFolder(context);
            } else {
               input.addFile(context);
            }
         }
      } catch (Exception value) {
         throw new RuntimeException(value);
      }
   }

   public static byte[] resolvePayload(byte[] instance) {
      try {
         ByteArrayOutputStream target = new ByteArrayOutputStream();
         try (InflaterOutputStream input = new InflaterOutputStream(target)) {
            input.write(instance);
            input.flush();
         }
         return target.toByteArray();
      } catch (IOException result) {
         throw new RuntimeException("Unable to decompress with Gzip!", result);
      }
   }
}
