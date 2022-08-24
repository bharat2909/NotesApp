package com.example.mynotesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDAO {

    @Insert
    public void insert(Notes note);

    @Update
    public void update(Notes note);

    @Delete
    void delete(Notes note);

    @Query("SELECT * FROM Notes ORDER BY priority ASC")
    LiveData<List<Notes>> getAllNotes();

    @Query("DELETE FROM Notes")
    void deleteAllNotes();

}
