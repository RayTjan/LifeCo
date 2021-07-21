package com.example.LifeCo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.LifeCo.activities.SplashScreenNew;
import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SOCSProfileFragment extends Fragment {
    Button btnLogOut;
    TextView nama, email;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    public SOCSProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_socs_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nama = view.findViewById(R.id.txtNamaDriver);
        email = view.findViewById(R.id.txtEmailDriver);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

//        DocumentReference documentReference = fStore.collection("Users").document(userId);
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                nama.setText(documentSnapshot.getString("name"));
//                email.setText(documentSnapshot.getString("email"));
//            }
//        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (FirebaseAuth.getInstance().getUid() == null) {
                    Intent intent = new Intent(getActivity(), SplashScreenNew.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}