package co.in.socailbuzz.socialact;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String ENDPOINT="http://socialact.in/";

    @GET("api/campaign-users/tavf2018")
     Call<Data> getAllUsers();
}
