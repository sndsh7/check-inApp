package co.in.socailbuzz.socialact;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import static co.in.socailbuzz.socialact.Constants.EXHIBITOR_CREDENTIALS;
import static co.in.socailbuzz.socialact.Constants.EXHIBITOR_NAME;

public class ExhibitorScanQR extends AppCompatActivity implements View.OnClickListener {

    private TextView exhibitorName;
    private Button exhibitorScan, exhibitorLogout;
    private SharedPreferences preferences;
    private String exhibitorNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitor_scan_qr);
        exhibitorName = findViewById(R.id.exhibitorName_scanQr);
        exhibitorScan = findViewById(R.id.scanButton_scanQr);
        exhibitorLogout = findViewById(R.id.logOutButton_scanQr);
        exhibitorScan.setOnClickListener(this);
        exhibitorLogout.setOnClickListener(this);
        preferences = getSharedPreferences(EXHIBITOR_CREDENTIALS, Context.MODE_PRIVATE);
        updateExhibitor();
    }

    private void updateExhibitor() {
        exhibitorNameString = preferences.getString(EXHIBITOR_NAME, "Not Valid");
        exhibitorName.setText(exhibitorNameString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exhibitorName_scanQr:
                scanExhibitorQr();
                break;

            case R.id.logOutButton_scanQr:
                logOutExhibitor();
                break;
        }
    }


    private void scanExhibitorQr() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();
    }

    private void logOutExhibitor() {
        preferences.edit().remove(EXHIBITOR_NAME).apply();
        startActivity(new Intent(this, ExhibitorLogin.class));
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

    private void showAlert(String result) {
        final User user = MyApplication.getGson().fromJson(result, User.class);
        if (isInvalid(user, R.string.invalid_user_data)) return;
        String bandId = user.getBand_uid();
        if (isInvalid(bandId, R.string.invalid_band_id)) return;
        DataAdapter.getInstance().getDataSource()
                .checkInExhibitor(exhibitorNameString, user.getBand_uid(), new PostCallback<ExhibitorResponseData>() {
                    @Override
                    public void onResultCalled(boolean isSuccess, ExhibitorResponseData result) {
                        if (result != null && result.status.trim().equals("SUCCESS"))
                            showCheckInDialog(user, true);
                        else
                            showCheckInDialog(user, false);
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


    public <T> boolean isInvalid(T t, int stringId) {
        if (t == null) {
            Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
