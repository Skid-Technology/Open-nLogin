package com.nickuc.login.platform.listener;

public interface ChildListenerContract {
   <T> T findObject();

   default <T> T c() {
      return this.findObject();
   }
}
