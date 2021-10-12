package com.example.LifeCo.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.LifeCo.activities.LoginActivity;
import com.example.lifeco.R;
import com.example.LifeCo.activities.SplashScreenNew;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


public class AccountDriverFragment extends Fragment {

    TextView nama, email, nohp, rumahsakit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FloatingActionButton btnEditAkunDriver;
    Button btnLogoutDriver;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accountdriver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nama = view.findViewById(R.id.txtNamaDriver);
        email = view.findViewById(R.id.txtEmailDriver);
        nohp = view.findViewById(R.id.txtNoHPDriver);
        rumahsakit = view.findViewById(R.id.txtRumahSakit);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

//        DocumentReference documentReference = fStore.collection("Users").document(userId);
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
//                nama.setText(documentSnapshot.getString("name"));
//                email.setText(documentSnapshot.getString("email"));
//                rumahsakit.setText(documentSnapshot.getString("hospital"));
//                nohp.setText(documentSnapshot.getString("phoneNumber"));
//            }
//        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("checking 1", "hi");
                if (dataSnapshot.exists()) {
                    Log.d("checking 2", "hi " + dataSnapshot.child("account").getValue());
                    nama.setText((dataSnapshot.child("name").getValue()).toString());
                    email.setText((dataSnapshot.child("email").getValue()).toString());
                    rumahsakit.setText((dataSnapshot.child("hospital").getValue()).toString());
                    nohp.setText((dataSnapshot.child("phoneNumber").getValue()).toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Cannot get info", "Null");
            }
        });

        btnEditAkunDriver = view.findViewById(R.id.btnEditProfileDriver);
        btnEditAkunDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new EditAkunFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_main, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
        btnLogoutDriver = view.findViewById(R.id.btnLogOutDriver);
        btnLogoutDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                FirebaseFirestore.getInstance().terminate();
                if (FirebaseAuth.getInstance().getUid() == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}
