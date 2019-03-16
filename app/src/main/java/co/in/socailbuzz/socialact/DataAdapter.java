package co.in.socailbuzz.socialact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataAdapter {

    private static DataAdapter mInstance;
    private static DataSource dataSource;

    private DataAdapter() {
    }

    public static DataAdapter getInstance() {
        if (mInstance == null) {
            mInstance = new DataAdapter();
        }

        return mInstance;
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new DataSource();
        }
        return dataSource;
    }

    public class DataSource {
        private Api api;

        DataSource() {
            api = MyApplication.getNetworkInstance().create(Api.class);
        }

        public void getUsers(final UserCallback callback) {
            api.getAllUsers()
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if (response.isSuccessful())
                                callback.onUsers(response.body().getData());
                            else
                                callback.onFailed("Unable to get Users");
                        }

                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            callback.onFailed("Unable to get Users" + t.getMessage());
                        }
                    });
        }


        public void checkInUser(String deviceId, String band_uid, final PostCallback callback) {
            api.postCheckIn(deviceId, band_uid).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        String status = "fail";
                        try {
                            status = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (status.trim().equals("SUCCESS"))
                            callback.onResultCalled(true, "SUCCESS");
                        else
                            callback.onResultCalled(false, null);
                    } else
                        callback.onResultCalled(false, null);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.onResultCalled(false, null);
                }
            });
        }

        public void checkInExhibitor(String exhibitorName, String band_uid, final PostCallback callback) {
            api.exhibitorCheckIn(exhibitorName,band_uid).enqueue(new Callback<ExhibitorResponseData>() {
                @Override
                public void onResponse(Call<ExhibitorResponseData> call, Response<ExhibitorResponseData> response) {
                    if(response.isSuccessful())
                        callback.onResultCalled(true,response.body());
                }

                @Override
                public void onFailure(Call<ExhibitorResponseData> call, Throwable t) {

                }
            });
        }

    }
}
