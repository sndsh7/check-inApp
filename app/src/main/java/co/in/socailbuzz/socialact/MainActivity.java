package co.in.socailbuzz.socialact;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UserCallback {

    private Button scan;
    private Button exhibitor;
    private Button refresh;
    private DataAdapter.DataSource source;
    private List<User> users;
    private Gson gson;
    private final String DEVICE_ID = "sbt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan = findViewById(R.id.scan);
        exhibitor = findViewById(R.id.exhibitor);
        source = DataAdapter.getInstance().getDataSource();
        source.getUsers(this);
        Toast.makeText(this, "Please Wait..", Toast.LENGTH_SHORT).show();
        scan.setOnClickListener(this);
        refresh = findViewById(R.id.fetch);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //source.getUsers(this);
                onUsers(users);
            }
        });
        exhibitor.setOnClickListener(this);
        gson = new Gson();
    }

    @Override
    public void onClick(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();
    }


    //@Override
    public void onUsers(List<User> users) {
        this.users = users;
        Toast.makeText(this, "Fetch complete now you can scan ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String message) {
        scan.setEnabled(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                showAlert(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showAlert(String contents) {
        final User user = gson.fromJson(contents, User.class);
        final boolean isValid = users.contains(user);

        if (TextUtils.isEmpty(user.getBand_uid())) {
            Toast.makeText(this, MyApplication.getInstance().getString(R.string.invalid_band_id), Toast.LENGTH_LONG).show();
            return;
        }

         DataAdapter.getInstance().getDataSource().checkInUser(DEVICE_ID, user.getBand_uid(), new PostCallback() {
            @Override
            public void onResultCalled(boolean isSuccess, String result) {
                if (isSuccess) showCheckInDialog(user, isValid);
                else
                    Toast.makeText(MainActivity.this, MyApplication.getInstance().getString(R.string.unable_to_check_in), Toast.LENGTH_LONG).show();
            }
        });

        DataAdapter.getInstance().getDataSource().checkInExhibitor(DEVICE_ID, user.getBand_uid(), new PostCallback() {
            @Override
            public void onResultCalled(boolean isSuccess, String result) {
                if(isSuccess) showCheckInDialog(user,isValid);
                else
                    Toast.makeText(MainActivity.this,MyApplication.getInstance().getString(R.string.unable_to_check_in),Toast.LENGTH_LONG).show();
            }
        });


    }

    private void showCheckInDialog(User user, boolean isValid) {
        View view = LayoutInflater.from(this).inflate(R.layout.user, null);

        formatOnOutput(user, view, isValid);

        new AlertDialog.Builder(this)
                .setView(view)
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void formatOnOutput(User user, View view, boolean isValid) {
        ImageView photo = view.findViewById(R.id.user_image);
        TextView name = view.findViewById(R.id.user_name);
        TextView email = view.findViewById(R.id.user_email);
        TextView mobile = view.findViewById(R.id.user_mobile);
        TextView status = view.findViewById(R.id.container_status);
        ViewGroup group = view.findViewById(R.id.container);
        if (!isValid) {
            photo.setImageResource(R.drawable.fail);
            status.setText("Invalid User");
            group.setVisibility(View.GONE);
        } else {
            Picasso.get().load(user.getAvatar()).centerCrop().into(photo);//need to remove all "\" from url link
            name.setText(user.getName());
            email.setText(user.getEmail());
            mobile.setText(user.getMobile());
            status.setText("User Info");
        }
    }

}
