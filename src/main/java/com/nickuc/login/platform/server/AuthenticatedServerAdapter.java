package com.nickuc.login.platform.server;

import com.nickuc.login.platform.sender.OutgoingSenderAdapter;

public interface AuthenticatedServerAdapter extends OutgoingSenderAdapter {
   void performMessage(String target);

   default void k(String target) {
      this.performMessage(target);
   }
}
