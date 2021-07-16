package com.example.LifeCo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.LifeCo.model.Note;
import com.example.LifeCo.model.OnCardClickListener;
import com.example.lifeco.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SOCSNotesFragment extends Fragment implements OnCardClickListener {

    private View view;

    private RecyclerView note_recyclerView;
    private FloatingActionButton note_FAB_create;
    private ArrayList<Note> noteList;
    private NoteRVAdapter adapter;

    Query noteReference;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_socs_notes, container, false);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        initialize();

        loadNote();

        setListener();

        return view;
    }

    @Override
    public void onClick(int position) {
        Note note = noteList.get(position);
        String noteId = note.NoteId;

        Intent intent = new Intent(getContext(), EditNoteActivity.class);
        intent.putExtra("noteId", noteId);
        startActivity(intent);

        note_search_input.clearFocus();
        note_search_input.setQuery("", false);
    }

    private void setListener() {
        note_FAB_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getParentFragmentManager();
                CreateNoteFragment createNote = new CreateNoteFragment(NoteFragment.this);
                createNote.show(fm, null);
            }
        });
    }


    private void loadNote() {
        noteReference = fStore.collection("user_collection")
                .document(userID).collection("note_collection")
                .orderBy("created", Query.Direction.DESCENDING);

        noteReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                noteList.clear();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        String id = doc.getId();
                        Note note = doc.toObject(Note.class).withId(id);

                        noteList.add(note);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initialize() {
        note_recyclerView = view.findViewById(R.id.note_recyclerView);
        note_FAB_create = view.findViewById(R.id.note_FAB_create);
        noteList = new ArrayList<Note>();
        adapter = new NoteRVAdapter(noteList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        note_recyclerView.setLayoutManager(manager);
        note_recyclerView.setAdapter(adapter);
    }
}