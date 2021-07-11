package com.example.LifeCo.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.LifeCo.activities.BarcodeDataActivity;
import com.example.LifeCo.activities.MainActivity;
import com.example.LifeCo.activities.MessageActivity;
import com.example.lifeco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeFragment extends Fragment {

    FirebaseAuth mAuth;

    private View view;
    private String userID, scannedUserID;
    private ImageView barcode_imageView;
    private Button barcode_button;
    private int barcodeSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_barcode, container, false);
        barcode_imageView = view.findViewById(R.id.barcode_imageView);
        barcode_button = view.findViewById(R.id.barcode_button);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        barcodeSize = 600;

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(userID, BarcodeFormat.QR_CODE, barcodeSize, barcodeSize);
            barcode_imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(BarcodeFragment.this);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult.getContents()!= null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Results");
//            builder.setMessage(intentResult.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            builder.show();

            scannedUserID = intentResult.getContents();

            Intent intent = new Intent(getContext(), BarcodeDataActivity.class);
            intent.putExtra("userID", scannedUserID);
            startActivity(intent);

        }else{
            Toast.makeText(getContext(), "OOPS... That doesn't look like a barcode, please try again!", Toast.LENGTH_SHORT).show();
        }
    }
}
