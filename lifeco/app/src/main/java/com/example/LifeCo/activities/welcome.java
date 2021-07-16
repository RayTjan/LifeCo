package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lifeco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class welcome extends AppCompatActivity {

    Button pasienButton, ambulansButton, socsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        pasienButton = findViewById(R.id.pasienButton);
        ambulansButton = findViewById(R.id.ambulansButton);
        socsButton = findViewById(R.id.socsButton);

        ambulansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterDriverIntent = new Intent(welcome.this,RegistrationDriverActivity.class);
                startActivity(LoginRegisterDriverIntent);
            }
        });
        pasienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterPassengerIntent = new Intent(welcome.this,RegistrationActivity.class);
                startActivity(LoginRegisterPassengerIntent);
            }
        });
        socsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterSocsIntent = new Intent(welcome.this,SOCSMainActivity.class);
                startActivity(LoginRegisterSocsIntent);
            }
        });
    }
}