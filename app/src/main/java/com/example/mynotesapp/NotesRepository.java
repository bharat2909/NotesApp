package com.example.mynotesapp;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository{

    public NotesDAO notesDAO;
    public LiveData<List<Notes>> notesList;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NotesRepository(Application application) {
        notesDatabase database = notesDatabase.getInstance(application);
        notesDAO = database.notesDAO();
        notesList = notesDAO.getAllNotes();
    }

    public void insert(Notes note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDAO.insert(note);
            }
        });
    }

    public void update(Notes note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDAO.update(note);
            }
        });
    }

    public void delete(Notes note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDAO.delete(note);
            }
        });
    }

    public void deleteAllNotes(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDAO.deleteAllNotes();
            }
        });
    }

    public LiveData<List<Notes>> getALlNotes(){
        return notesList;
    }




}
