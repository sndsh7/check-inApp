package co.in.socailbuzz.socialact;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static Retrofit retrofit;
    private static Gson gson;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        gson = new Gson();
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

    public static Gson getGson() {
        return gson;
    }

    public static boolean isNetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) MyApplication.mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }



    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Retrofit getNetworkInstance() {
        return retrofit;
    }

}
