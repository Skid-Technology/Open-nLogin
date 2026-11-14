package com.nickuc.login.platform.command;

import com.nickuc.login.platform.listener.ChildListenerContract;

public interface StrictCommandHandler extends ChildListenerContract, Runnable {
   boolean fetchState();

   String loadMessage();

   void performTask();
}
