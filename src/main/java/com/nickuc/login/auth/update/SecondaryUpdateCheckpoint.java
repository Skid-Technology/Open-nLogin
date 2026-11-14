package com.nickuc.login.auth.update;

import com.nickuc.login.platform.listener.SharedListenerContract;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.CheckReturnValue;
import com.nickuc.login.auth.login.StrictLoginHandler;
import com.nickuc.login.auth.login.PrivateLoginHandler;


public class SecondaryUpdateCheckpoint {
   private final Object[] values;
   private final String name;

   public SecondaryUpdateCheckpoint(String target, Object[] input) {
      this.name = target;
      this.values = input;
   }

   public int loadCount(SharedListenerContract target) {
      return target.computeStrictLoginHandler(this.name, this.values).resolveObject();
   }

   @CheckReturnValue
   public StrictLoginHandler<ResultSet> handleStrictLoginHandler(SharedListenerContract target) {
      return target.buildPrimaryLoginHandler(this.name, this.values);
   }

   public boolean validateState(SharedListenerContract target) {
      return target.resolveStrictLoginHandler(this.name, this.values).resolveObject();
   }

   public long computeTime(SharedListenerContract target) {
      try (PrivateLoginHandler input = target.getPrivateLoginHandler();
           PreparedStatement output = input.retrieveConnection().prepareStatement(this.name, 1)) {
         target.executePreparedStatement(output, this.values);
         int context = output.executeUpdate();
         if (context == 0) {
            throw new RuntimeException("Unable to update account! affected rows=" + context);
         }

         try (ResultSet data = output.getGeneratedKeys()) {
            if (!data.next()) {
               throw new SQLException("Unable to retrieve generated keys!");
            }

            return data.getLong(1);
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }
}
