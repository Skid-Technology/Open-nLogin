package com.nickuc.login.platform.server;

import com.nickuc.login.platform.player.IndirectPlayerContract;

public interface LinkedServerAdapter extends IndirectPlayerContract {
   @Override
   default boolean canState(String target) {
      return false;
   }

   @Override
   default String computeMessage(String target) {
      throw new UnsupportedOperationException();
   }
}
