package co.in.socailbuzz.socialact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
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


        public boolean checkInUser(String deviceId, String band_uid) {
            String status = "FAIL";
            try {
                status = api.postCheckIn(deviceId, band_uid).execute().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (status.trim().equals("SUCCESS")) return true;
            else return false;
        }

    }
}
