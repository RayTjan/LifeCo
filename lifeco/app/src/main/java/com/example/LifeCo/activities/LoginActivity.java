package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnDaftarAkun;
    String email, password;
    TextInputLayout emailLogin, passwordLogin;
    TextView loginStatus;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String currentUserId;
    DatabaseReference targetRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnDaftarAkun = findViewById(R.id.btnDaftarAkun);
        emailLogin = findViewById(R.id.inputEmailLogin);
        passwordLogin = findViewById(R.id.inputPasswordLogin);
        loginStatus = findViewById(R.id.textLoginStatus);
        fAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailLogin.getEditText().getText().toString().trim();
                password = passwordLogin.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailLogin.setError("Silahkan isi email Anda.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordLogin.setError("Silahkan isi password Anda.");
                    return;
                }
                if(password.length() < 6){
                    passwordLogin.setError("Password harus 6 karakter atau lebih.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "User Logged In.", Toast.LENGTH_SHORT).show();

                                currentUserId = fAuth.getCurrentUser().getUid();
                                targetRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                Log.d("Logged in Id:", currentUserId);
                                targetRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("checking 1", "hi");
                                        if (dataSnapshot.exists()) {
                                            Log.d("checking 2", "hi " + dataSnapshot.child("account").getValue());
                                            if((dataSnapshot.child("account").getValue()).equals("patient")){
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                String account = "pasien";
                                                intent.putExtra("account",account);
                                                startActivity(intent);
                                                finish();
                                            } else if((dataSnapshot.child("account").getValue()).equals("ambulance")){
                                                Log.d("checking account", "hi");
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                String account = "ambulans";
                                                intent.putExtra("account",account);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else if((dataSnapshot.child("account").getValue()).equals("SOCS")){
                                                Log.d("checking account", "hi");
                                                Intent intent = new Intent(LoginActivity.this, SOCSMainActivity.class);
                                                String account = "SOCS";
                                                intent.putExtra("account",account);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            Log.d("ERROR", "Empty snapshot");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.d("ERROR", databaseError.toString());
                                    }
                                });

                        }else {
                            Toast.makeText(LoginActivity.this, "Tidak dapat log in akun.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        btnDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, welcome.class);
                startActivity(intent);
            }
        });
    }

//    private void LoginChecking(String emailChecking, String passwordChecking){
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("email", emailChecking);
//        params.put("password", passwordChecking);
//        String url = "http://iamtinara.com/lifeco/loginuser.php";
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    String login_status = responseObject.getString("login_status");
//                    if (login_status.equalsIgnoreCase("logged_in")) {
//                        Log.d("loginstat", login_status);
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Log.d("loginstat", login_status);
//                        loginStatus.setText("Wrong email or password! Try again!!!");
//
//                    }
//                } catch (Exception e) {
//                    Log.d("ExceptionHistory", "onSuccess: " + e.getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("onFailureHistory", "onFailure: " + error.getMessage());
//            }
//        });
//    }
}
