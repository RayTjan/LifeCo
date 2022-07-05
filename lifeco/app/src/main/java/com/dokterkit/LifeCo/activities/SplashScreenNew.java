package com.dokterkit.LifeCo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dokterkit.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenNew extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String userId;
    FirebaseAuth fAuth;
    DatabaseReference targetRef;
    String tipeakun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_new);
        Log.d("hmmm", "i dun get it :(");
        fAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Log.d("langsungaaaaa", "wth");
                Log.d("otw login", "aaaa");
//                if (fAuth.getCurrentUser() != null) {
//                    userId = fAuth.getCurrentUser().getUid();
//                    targetRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
//                    Log.d("Logged in Id:", userId);
//                    targetRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Log.d("checking 1", "hi");
//                            if (dataSnapshot.exists()) {
//                                Log.d("checking 2", "hi " + dataSnapshot.child("account").getValue());
////                                if((dataSnapshot.child("account").getValue()).equals("patient")){
////                                    Intent intent = new Intent(SplashScreenNew.this, MainActivity.class);
////                                    String account = "pasien";
////                                    intent.putExtra("account",account);
////                                    startActivity(intent);
////                                    finish();
////                                } else if((dataSnapshot.child("account").getValue()).equals("ambulance")){
////                                    Log.d("checking account", "hi");
////                                    Intent intent = new Intent(SplashScreenNew.this, MainActivity.class);
////                                    String account = "ambulans";
////                                    intent.putExtra("account",account);
////                                    startActivity(intent);
////                                    finish();
////                                } else if ((dataSnapshot.child("account").getValue()).equals("SOCS")) {
////                                    Log.d("checking account", "hi");
////                                    Intent intent = new Intent(SplashScreenNew.this, SOCSMainActivity.class);
////                                    String account = "socs";
////                                    intent.putExtra("account", account);
////                                    startActivity(intent);
////                                    finish();
////                                }
//                                //posisi udah login
//                                Intent intent = new Intent(SplashScreenNew.this, welcome.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Log.d("ERROR", "Empty snapshot");
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Log.d("ERROR", databaseError.toString());
//                        }
//                    });
//
//                } else {
//                    Intent intent = new Intent(SplashScreenNew.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
                Intent intent = new Intent(SplashScreenNew.this, welcome.class);
                startActivity(intent);
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
