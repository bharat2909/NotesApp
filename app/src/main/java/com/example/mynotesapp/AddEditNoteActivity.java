package com.example.mynotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    EditText Title;
    EditText Desc;
    NumberPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        Title = findViewById(R.id.editTextTextTitle);
        Desc = findViewById(R.id.editTextTextDescription);
        picker = findViewById(R.id.numberPicker);

        picker.setMaxValue(10);
        picker.setMinValue(1);

        getSupportActionBar();
        Intent intent = getIntent();

        if(intent.hasExtra("ID")){
            setTitle("Edit Note");
            Title.setText(intent.getStringExtra("Title"));
            Desc.setText(intent.getStringExtra("Desc"));
            picker.setValue(intent.getIntExtra("Priority",1));

        }else{
            setTitle("Add Note");
        }
    }

    public void saveNote(){
        String title = Title.getText().toString();
        String desc = Desc.getText().toString();
        int priority = picker.getValue();

        if(title.trim().equals("") || desc.trim().equals("")){
            Toast.makeText(this, "Please Enter Title and Description to continue!", Toast.LENGTH_SHORT).show();
        }

        int id = getIntent().getIntExtra("ID",-1);


        Intent data = new Intent();
        data.putExtra("Title",title);
        data.putExtra("Desc",desc);
        data.putExtra("Priority",priority);

        if(id!=-1){
            data.putExtra("ID",id);
        }
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSave:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}