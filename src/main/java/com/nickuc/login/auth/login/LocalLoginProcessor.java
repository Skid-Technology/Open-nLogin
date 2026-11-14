package com.nickuc.login.auth.login;

import java.util.Map;
import java.util.concurrent.Callable;

public class LocalLoginProcessor {
   public static <T, V> T processObject(Map<T, V> instance, Callable<T> target) {
      Object input;
      try {
         do {
            input = target.call();
         } while (instance.containsKey(input));
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

      return (T)input;
   }
}
