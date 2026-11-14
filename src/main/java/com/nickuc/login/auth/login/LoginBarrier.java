package com.nickuc.login.auth.login;

import com.nickuc.login.platform.listener.SharedListenerContract;
import com.nickuc.login.storage.notice.DirectNoticeCatalog;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginBarrier implements SharedListenerContract {
   private final int count;
   private final SharedListenerContract sharedListenerContract;
   private final AtomicInteger atomicInteger = new AtomicInteger();
   private final Connection connection;

   @Override
   public void sendConnection(Connection target) {
      if (this.atomicInteger.get() > 0) {
         try {
            target.commit();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
   }

   @Override
   public Connection resolveConnection() {
      if (this.atomicInteger.getAndIncrement() >= this.count) {
         this.atomicInteger.set(1);
         try {
            this.connection.commit();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }

      return this.connection;
   }

   @Override
   public DirectNoticeCatalog resolveDirectNoticeCatalog() {
      return this.sharedListenerContract.resolveDirectNoticeCatalog();
   }

   public LoginBarrier(SharedListenerContract target, int input) {
      this.sharedListenerContract = target;
      this.count = input;
      this.connection = target.resolveConnection();
      try {
         this.connection.setAutoCommit(false);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void executeTask() {
      try {
         if (this.atomicInteger.get() > 0) {
            this.connection.commit();
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      } finally {
         try {
            this.connection.close();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public int loadCount() {
      return this.count;
   }
}
