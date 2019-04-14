package co.in.socailbuzz.socialact;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String ENDPOINT = "http://socialact.in/api/";
    String DEVICE_ID = "device";
    String BAND_UID = "tagid";
    String BANDS = "bands";
    String EXHIBITOR_NAME = "exhibitor_name";

    @GET("terminal-social-users/GHEDEX/ghedex-2019-checkin")
    Call<Data> getAllUsers();

    @POST("post/message")
    Call<ResponseBody> postCheckIn(@Query(DEVICE_ID) String deviceId, @Query(BAND_UID) String band_uid);

    @POST("exhibitor/{" + EXHIBITOR_NAME + "}")
    Call<ExhibitorResponseData> exhibitorCheckIn(@Path(EXHIBITOR_NAME) String exhibitorName, @Query(BANDS) String bands);
}
