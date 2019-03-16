package co.in.socailbuzz.socialact;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setUpRetrofit();
    }

    private void setUpRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Api.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Retrofit getNetworkInstance() {
        return retrofit;
    }

}
