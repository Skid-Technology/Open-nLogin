package com.nickuc.login.proxy;

import com.nickuc.login.auth.login.StoredLoginHandler;
import com.nickuc.login.loader.platform.VelocityLoader;
import com.nickuc.login.platform.session.LinkedSessionHandler;
import com.nickuc.login.platform.command.StrictCommandHandler;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class PrimaryVelocityForwarder implements LinkedSessionHandler {
   private final StoredLoginHandler storedLoginHandler = new StoredLoginHandler();
   private final VelocityLoader velocityLoader;

   public VelocityLink handleVelocityLink(Consumer<StrictCommandHandler> target, long input, long context, TimeUnit value) {
      return new VelocityLink(this.storedLoginHandler, target).processVelocityLink(this.velocityLoader, input, context, value);
   }

   public VelocityLink computeVelocityLink(Runnable target, long input, long context, TimeUnit value) {
      return new VelocityLink(this.storedLoginHandler, target).processVelocityLink(this.velocityLoader, input, context, value);
   }

   public VelocityLink handleVelocityLink(Consumer<StrictCommandHandler> target) {
      return new VelocityLink(this.storedLoginHandler, target).buildVelocityLink(this.velocityLoader);
   }

   public VelocityLink processVelocityLink(Consumer<StrictCommandHandler> target, long input, TimeUnit context) {
      return new VelocityLink(this.storedLoginHandler, target).computeVelocityLink(this.velocityLoader, input, context);
   }

   @Override
   public void dispatchTask() {
      this.getCollection().forEach(StrictCommandHandler::performTask);
   }

   @Override
   public StoredLoginHandler loadStoredLoginHandler() {
      return this.storedLoginHandler;
   }

   public VelocityLink buildVelocityLink(Runnable target, long input, TimeUnit context) {
      return new VelocityLink(this.storedLoginHandler, target).computeVelocityLink(this.velocityLoader, input, context);
   }

   public PrimaryVelocityForwarder(VelocityLoader target) {
      this.velocityLoader = target;
   }

   public VelocityLink processVelocityLink(Runnable target) {
      return new VelocityLink(this.storedLoginHandler, target).buildVelocityLink(this.velocityLoader);
   }

   @Override
   public void run() {
      this.dispatchTask();
   }


   @Override
   public StrictCommandHandler loadStrictCommandHandler(Consumer<StrictCommandHandler> target, long input, long context, TimeUnit value) {
      return this.handleVelocityLink(target, input, context, value);
   }

   @Override
   public StrictCommandHandler loadStrictCommandHandler(Consumer<StrictCommandHandler> target) {
      return this.handleVelocityLink(target);
   }



   @Override
   public StrictCommandHandler loadStrictCommandHandler(Consumer<StrictCommandHandler> target, long input, TimeUnit context) {
      return this.processVelocityLink(target, input, context);
   }

   @Override
   public StrictCommandHandler buildStrictCommandHandler(Runnable target) {
      return this.processVelocityLink(target);
   }

   @Override
   public StrictCommandHandler loadStrictCommandHandler(Runnable target, long input, TimeUnit context) {
      return this.buildVelocityLink(target, input, context);
   }

   @Override
   public StrictCommandHandler processStrictCommandHandler(Runnable target, long input, long context, TimeUnit value) {
      return this.computeVelocityLink(target, input, context, value);
   }
}
