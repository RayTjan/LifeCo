package com.example.LifeCo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d("hmmm", "i dun get it :(");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                fAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();
                userId = fAuth.getCurrentUser().getUid();
                if (fAuth.getCurrentUser() != null) {
                    // User is logged in
                    DocumentReference documentReference = fStore.collection("Users").document(userId);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if(documentSnapshot.getString("tipeakun").equalsIgnoreCase("driver")){
                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                String account = "ambulans";
                                intent.putExtra("account",account);
                                startActivity(intent);
                                finish();
                            } else if(documentSnapshot.getString("tipeakun").equalsIgnoreCase("pasien")){
                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                String account = "pasien";
                                intent.putExtra("account",account);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, welcome.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
