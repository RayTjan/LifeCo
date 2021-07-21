package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.LifeCo.model.Socs;
import com.example.LifeCo.model.History;
import com.example.lifeco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrationSOCSActivity extends AppCompatActivity {
    Button btnDaftarAkun;
    TextInputLayout inpNamaDriver, inpEmailDriver, inpPasswordDriver, inpNoHPDriver;
    String tipeakun;

    String userNama, userEmail, userPassword, userNoHP;
    String userID;
    String status, search, imageURL;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseFirestore fStore;
    CollectionReference histRef;

    //    Users users;
    Socs socs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_s_o_c_s);

        inpNamaDriver = findViewById(R.id.inpNamaDriverRegis);
        inpEmailDriver = findViewById(R.id.inpEmailDriverRegis);
        inpPasswordDriver = findViewById(R.id.inpPasswordDriverRegis);
        inpNoHPDriver = findViewById(R.id.inpNoTelpDriverRegis);
        btnDaftarAkun = findViewById(R.id.btnDaftarAkunDriver);

//        users = new Users();
        socs = new Socs();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        btnDaftarAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNama = inpNamaDriver.getEditText().getText().toString().trim();
                userEmail = inpEmailDriver.getEditText().getText().toString().trim();
                userPassword = inpPasswordDriver.getEditText().getText().toString().trim();
                userNoHP = inpNoHPDriver.getEditText().getText().toString().trim();


                if (TextUtils.isEmpty(userNama)) {
                    inpNamaDriver.setError("Silahkan isi nama Anda.");
                    return;
                }
                if (TextUtils.isEmpty(userEmail)) {
                    inpEmailDriver.setError("Silahkan isi email Anda.");
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    inpPasswordDriver.setError("Silahkan isi password Anda.");
                    return;
                }

                if (userPassword.length() < 6) {
                    Toast.makeText(RegistrationSOCSActivity.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    register(userNama, userEmail, userPassword, userNoHP);
                }

            }
        });

    }

    private void register(final String nama, final String email, final String password, final String noHP) {

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                tipeakun = "SOCS";

                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationSOCSActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Users").document(userID);

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", nama);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("phoneNumber", noHP);
                    user.put("imageURL", "default");
                    user.put("account", tipeakun);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("New User", "onSuccess: New User Registered for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Failed to Register", "onFailure: " + e.toString());
                        }
                    });

                    FirebaseUser firebaseUser = fAuth.getCurrentUser();
                    assert firebaseUser != null;
                    final String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

//                    Users users = new Users();
                    Socs socs = new Socs();
                    socs.setUid(userid);
                    socs.setName(nama);
                    socs.setEmail(email);
                    socs.setAccount("SOCS");
                    socs.setPassword(password);
                    socs.setPhoneNumber(noHP);

                    reference.setValue(socs).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("New User - Realtime", "onSuccess: New User Registered for " + userid);
                            }
                        }
                    });
                    buatAkun();
                    Intent intent = new Intent(RegistrationSOCSActivity.this, SOCSMainActivity.class);
                    String account = "SOCS";
                    intent.putExtra("account", account);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(RegistrationSOCSActivity.this, "Tidak dapat mendaftarkan akun.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void buatAkun() {

        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        assert firebaseUser != null;
        final String userid = firebaseUser.getUid();

        String aktivitas = "Mendaftarkan akun";
        String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String waktu = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        History history = new History(aktivitas, tanggal, waktu);

        histRef = fStore.collection("Users");

        histRef.document(userid).collection("History").add(history);

    }
}