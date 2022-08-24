package com.example.mynotesapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE =1;
    public static final int EDIT_NOTE =2;

    NotesViewModel viewModel;
    RecyclerView rv;
    NotesAdapter adapter;
    ArrayList<Notes> notesList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter();
        rv.setAdapter(adapter);
        notesList = new ArrayList<>();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NotesViewModel.class);
        viewModel.getAllNotes().observe(MainActivity.this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                adapter.setNotesList(notes);
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddEditNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                viewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(rv);

        adapter.setOnItemClickListener(new NotesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Notes note) {
                Intent intent = new Intent(MainActivity.this,AddEditNoteActivity.class);
                intent.putExtra("ID",note.getId());
                intent.putExtra("Title",note.getTitle());
                intent.putExtra("Desc",note.getDesc());
                intent.putExtra("Priority",note.getPriority());

                startActivityForResult(intent,EDIT_NOTE);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDelete:
                viewModel.deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK && data!=null) {
            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            int priority = data.getIntExtra("Priority",1);

            Notes note = new Notes(title,desc,priority);
            viewModel.insert(note);
        }else if(requestCode == EDIT_NOTE && resultCode == RESULT_OK && data!=null){
            int id = data.getIntExtra("ID",-1);
            if(id == -1){
                Toast.makeText(this, "Note can't be Updated!", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            int priority = data.getIntExtra("Priority",1);

            Notes note = new Notes(title,desc,priority);
            note.setId(id);
            viewModel.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Note not Saved!", Toast.LENGTH_SHORT).show();
        }
    }
}