package com.nickuc.login.auth.message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import javax.annotation.CheckReturnValue;

public class MessageProcessor {
   public static boolean checkState(String instance, File target) {
      InputStream input = FastMessageHandler.createInputStream(instance);
      if (input == null) {
         return false;
      }

      try {
         if (!isState(target)) {
            return false;
         }

         updateInputStream(input, target);
         return true;
      } finally {
         try {
            input.close();
         } catch (IOException ignored) {
         }
      }
   }

   @CheckReturnValue
   public static BufferedInputStream createBufferedInputStream(File instance, OpenOption... target) {
      try {
         return new BufferedInputStream(Files.newInputStream(instance.toPath(), target));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   @CheckReturnValue
   public static BufferedOutputStream handleBufferedOutputStream(File instance, int target, OpenOption... input) {
      try {
         return new BufferedOutputStream(Files.newOutputStream(instance.toPath(), input), target);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static boolean verifyState(File instance, File target) {
      if (isState(target)) {
         try (BufferedInputStream input = createBufferedInputStream(instance);
              BufferedOutputStream output = computeBufferedOutputStream(target)) {
            FastMessageHandler.processInputStream(input, output);
            return true;
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      } else {
         return false;
      }
   }

   public static String handleMessage(File instance) {
      try (BufferedInputStream target = createBufferedInputStream(instance)) {
         byte[] input = FastMessageHandler.handlePayload(target);
         return new String(Base64.getEncoder().encode(input));
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public static File buildFile(File instance, String target) {
      int input = 0;
      File output = instance.getParentFile();

      File context;
      do {
         context = new File(output, String.format(target, input++));
      } while (context.exists());

      return context;
   }

   public static File handleFile(Class<?> instance) {
      try {
         String target = instance.getProtectionDomain().getCodeSource().getLocation().getPath();
         String input = URLDecoder.decode(target, "UTF-8");
         return new File(input);
      } catch (UnsupportedEncodingException output) {
         throw new RuntimeException(output);
      }
   }

   public static void updateInputStream(InputStream instance, File target) {
      try (BufferedOutputStream input = computeBufferedOutputStream(target)) {
         FastMessageHandler.processInputStream(instance, input);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   @CheckReturnValue
   public static BufferedInputStream handleBufferedInputStream(File instance, int target, OpenOption... input) {
      try {
         return new BufferedInputStream(Files.newInputStream(instance.toPath(), input), target);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static boolean validateState(File instance) {
      if (instance.isDirectory()) {
         File[] target = instance.listFiles();
         if (target != null) {
            for (File data : target) {
               validateState(data);
            }
         }
      }

      return instance.delete();
   }

   @CheckReturnValue
   public static BufferedOutputStream computeBufferedOutputStream(File instance, OpenOption... target) {
      try {
         return new BufferedOutputStream(Files.newOutputStream(instance.toPath(), target));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static String createMessage(File instance, MessageDigest target) throws IOException {
      try (BufferedInputStream input = createBufferedInputStream(instance)) {
         return FastMessageHandler.createMessage(input, target);
      }
   }

   public static boolean validateState(File instance, long target) {
      try {
         BasicFileAttributes output = Files.readAttributes(instance.toPath(), BasicFileAttributes.class);
         long context = output.creationTime().toMillis();
         return System.currentTimeMillis() - context >= target;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static boolean canState(File instance, boolean target) {
      if (!target && instance.exists()) {
         return true;
      }

      File input = instance.getParentFile();
      try {
         return input != null && !input.exists() && !input.mkdirs() ? false : instance.createNewFile();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static String resolveMessage(File instance) {
      String target = instance.getName();
      String[] input = target.split("\\.");
      return input.length == 1 ? target : String.join(".", Arrays.copyOfRange(input, 0, input.length - 1));
   }

   public static boolean isState(File instance) {
      return canState(instance, false);
   }
}
