package com.example.LifeCo.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BarcodeDataActivity extends AppCompatActivity {

    private Toolbar barcodeData_toolbar;
    private ImageView barcodeData_profile_pict;
    private TextView barcodeData_nama, barcodeData_jeniskelamin, barcodeData_goldarah, barcodeData_penyakitsendiri, barcodeData_penyakitkeluarga, barcodeData_keluhanutama, barcodeData_obat, barcodeData_alergiobat, barcodeData_alergimakanan, barcodeData_tekanandarah, barcodeData_guladarah;

    private String userID;

    private Bundle bundle;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_data);

        initialize();
        setText();
        setListener();

    }

    private void setListener() {
        barcodeData_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setText() {
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                barcodeData_nama.setText(documentSnapshot.getString("name"));
                barcodeData_jeniskelamin.setText(documentSnapshot.getString("gender"));
                barcodeData_goldarah.setText(documentSnapshot.getString("bloodType"));
                barcodeData_penyakitsendiri.setText(documentSnapshot.getString("ownDisease"));
                barcodeData_penyakitkeluarga.setText(documentSnapshot.getString("geneticDisease"));
                barcodeData_keluhanutama.setText(documentSnapshot.getString("complaint"));
                barcodeData_obat.setText(documentSnapshot.getString("medicineIntake"));
                barcodeData_alergiobat.setText(documentSnapshot.getString("medicineAllergy"));
                barcodeData_alergimakanan.setText(documentSnapshot.getString("foodAllergy"));
                barcodeData_tekanandarah.setText(documentSnapshot.getString("bloodPressure"));
                barcodeData_guladarah.setText(documentSnapshot.getString("bloodSugar"));
            }
        });
    }

    private void initialize() {
        barcodeData_toolbar = findViewById(R.id.barcodeData_toolbar);
        barcodeData_profile_pict = findViewById(R.id.barcodeData_profile_pict);
        barcodeData_nama = findViewById(R.id.barcodeData_nama);
        barcodeData_jeniskelamin = findViewById(R.id.barcodeData_jeniskelamin);
        barcodeData_goldarah = findViewById(R.id.barcodeData_goldarah);
        barcodeData_penyakitsendiri = findViewById(R.id.barcodeData_penyakitsendiri);
        barcodeData_penyakitkeluarga = findViewById(R.id.barcodeData_penyakitkeluarga);
        barcodeData_keluhanutama = findViewById(R.id.barcodeData_keluhanutama);
        barcodeData_obat = findViewById(R.id.barcodeData_obat);
        barcodeData_alergiobat = findViewById(R.id.barcodeData_alergiobat);
        barcodeData_alergimakanan = findViewById(R.id.barcodeData_alergimakanan);
        barcodeData_tekanandarah = findViewById(R.id.barcodeData_tekanandarah);
        barcodeData_guladarah = findViewById(R.id.barcodeData_guladarah);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        bundle = getIntent().getExtras();
        if (bundle == null) {
            userID = null;
        } else {
            userID = bundle.getString("userID");
        }

    }
}