package com.example.mynotesapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class},version = 1)
public abstract class notesDatabase extends RoomDatabase {

    public static notesDatabase instance;
    public abstract NotesDAO notesDAO();

    public static synchronized notesDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),notesDatabase.class,"notes_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    };
}
