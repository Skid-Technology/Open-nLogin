package com.nickuc.login.auth.login;

import org.json.JSONObject;
import com.nickuc.login.platform.server.VerifiedServerAdapter;

public class ReadyLoginCheckpoint extends RemoteLoginCheckpoint {
   private final int count;
   private final JSONObject jSONObject;

   public ReadyLoginCheckpoint(VerifiedServerAdapter target, JSONObject input) {
      super(target);
      this.jSONObject = input;
      this.count = input.toString().length();
   }

   public static JSONObject buildJSONObject(ReadyLoginCheckpoint checkpoint) {
      return checkpoint.jSONObject;
   }

   public static int buildCount(ReadyLoginCheckpoint checkpoint) {
      return checkpoint.count;
   }
}
