package com.nickuc.login.protocol;

import com.github.retrooper.packetevents.protocol.ConnectionState;

public class SettingsAdapter {
   public static int[] values = new int[256];
   public static int[] activeValues = new int[256];
   public static int[] pendingValues = new int[256];

   static {
      try {
         values[ConnectionState.LOGIN.ordinal()] = 1;
      } catch (NoSuchFieldError output) {
      }

      try {
         values[ConnectionState.CONFIGURATION.ordinal()] = 2;
      } catch (NoSuchFieldError input) {
      }

      try {
         values[ConnectionState.PLAY.ordinal()] = 3;
      } catch (NoSuchFieldError target) {
      }
   }
}
