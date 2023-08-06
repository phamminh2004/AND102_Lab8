package com.minhpt.lab8;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
    Button btn_map, btn_fb, btn_gg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_map = findViewById(R.id.btn_map);
        btn_fb = findViewById(R.id.btn_fb);
        btn_gg = findViewById(R.id.btn_gg);
        btn_map.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btn_gg.setOnClickListener(v -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            registerForActivityResult.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> registerForActivityResult
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                try {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());

                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    String email = account.getEmail();

                    String name = account.getDisplayName();
                    Toast.makeText(MainActivity.this, "email: " + email + "\n" + "name: " + name, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("TAG", "onFailure" + e);
                }
            } else {
                Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    });

}
