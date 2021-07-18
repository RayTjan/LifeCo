package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lifeco.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class SOCSNotesEditActivity extends AppCompatActivity {

    private Toolbar editNote_toolbar;
    private TextInputLayout editNote_title_textInput, editNote_description_textInput;
    private Intent intent;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socs_notes_edit);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        initialize();

        DocumentReference noteReference = fStore.collection("Users")
                .document(userID).collection("Notes")
                .document(noteId);

        noteReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                editNote_title_textInput.getEditText().setText(value.getString("title"));
                editNote_description_textInput.getEditText().setText(value.getString("description"));
            }
        });

        setListener();
    }

    @Override
    public void onBackPressed() {
        if (getCurrentFocus() instanceof TextInputEditText) {
            getCurrentFocus().clearFocus();
        } else {
            saveNote();
            super.onBackPressed();
        }
    }

    private void saveNote() {
        String title = editNote_title_textInput.getEditText().getText().toString().trim();
        String description = editNote_description_textInput.getEditText().getText().toString().trim();

        DocumentReference noteReference = fStore.collection("Users")
                .document(userID).collection("Notes")
                .document(noteId);

        Map<String, Object> note = new HashMap<>();
        note.put("title", title);
        note.put("description", description);
        note.put("updated", FieldValue.serverTimestamp());

        noteReference.update(note).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error", e.toString());
            }
        });
    }

    private void setListener() {
        editNote_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                finish();
            }
        });

        editNote_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.editNote_menu_delete:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(SOCSNotesEditActivity.this);
                        dialog.setTitle("Delete note")
                                .setMessage("Are you sure you want to delete this note?" +
                                        "\n\nThis action cannot be undone!")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DocumentReference noteReference = fStore.collection("Users")
                                                .document(userID).collection("Notes").document(noteId);

                                        noteReference.delete().addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("error", e.toString());
                                            }
                                        });

                                        Toast.makeText(SOCSNotesEditActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .show();
                        break;
                }

                return true;
            }
        });
    }

    private void initialize() {
        editNote_toolbar = findViewById(R.id.editNote_toolbar);
        editNote_title_textInput = findViewById(R.id.editNote_title_textInput);
        editNote_description_textInput = findViewById(R.id.editNote_description_textInput);

        intent = getIntent();
        noteId = intent.getStringExtra("noteId");
    }
}