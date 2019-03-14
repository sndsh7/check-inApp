package co.in.socailbuzz.socialact;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String ENDPOINT="http://socialact.in/";

    @GET("api/campaign-users/comex-2019")
     Call<Data> getAllUsers();

    @POST("api/campaign-users/comex-2019")
    Call<ResponseBody> postCheckIn(@Body String checkIn);
}
