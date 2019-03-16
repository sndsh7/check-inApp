package co.in.socailbuzz.socialact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExhibitorLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText exhibitorName;
    private Button exhibitorLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitor_login);
        exhibitorName = findViewById(R.id.exhibitorName);
        exhibitorLoginButton = findViewById(R.id.exhibitorLoginButton);
        exhibitorLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String exName = exhibitorName.getText().toString();

        if (TextUtils.isEmpty(exName))
            Toast.makeText(this, "Invalid Exhibitor Name", Toast.LENGTH_SHORT).show();

    }
}
