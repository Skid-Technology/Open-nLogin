package com.nickuc.login.platform.session;

import com.nickuc.login.auth.login.IncomingLoginGate;
import com.nickuc.login.platform.account.OutgoingAccountHandler;
import com.nickuc.login.platform.account.ParentAccountHandler;
import com.nickuc.login.platform.connection.TightConnectionContract;
import com.nickuc.login.platform.sender.TightSenderAdapter;

public interface CachedSessionHandler {
   void performTask();

   IncomingLoginGate loadIncomingLoginGate();

   TightSenderAdapter[] findValues();

   void handleTask();

   OutgoingAccountHandler loadOutgoingAccountHandler();

   boolean verifyState(String target);

   TightConnectionContract retrieveTightConnectionContract();

   void processTask();

   void dispatchTask();

   void updateTask();

   LinkedSessionHandler computeLinkedSessionHandler(boolean target);

   ParentAccountHandler resolveParentAccountHandler();

   void sendTask();

   OutgoingAccountHandler getOutgoingAccountHandler();
}
