package com.example.mynotesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> notesList;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);
        notesList = repository.getALlNotes();
    }

    public void insert(Notes note){
        repository.insert(note);
    }

    public void delete(Notes note){
        repository.delete(note);
    }

    public void update(Notes note){
        repository.update(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Notes>> getAllNotes(){
        return notesList;
    }
}
