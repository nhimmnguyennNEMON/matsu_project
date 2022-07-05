package utils;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Google;
public class GoogleUtils {
  public static String getToken(final String code) throws ClientProtocolException, IOException {
    String response = Request.Post(env.GOOGLE_LINK_GET_TOKEN)
        .bodyForm(Form.form().add("client_id", env.GOOGLE_CLIENT_ID)
            .add("client_secret", env.GOOGLE_CLIENT_SECRET)
            .add("redirect_uri",env.GOOGLE_REDIRECT_URI).add("code", code) 
            .add("grant_type", env.GOOGLE_GRANT_TYPE).build())
            
        .execute().returnContent().asString();
      JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
      String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
      return accessToken;
  }
  public static Google getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
    String link = env.GOOGLE_LINK_GET_USER_INFO + accessToken;
    String response = Request.Get(link).execute().returnContent().asString();
    Google googlePojo = new Gson().fromJson(response, Google.class);
    return googlePojo;
  }
}