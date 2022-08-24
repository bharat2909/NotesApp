package com.example.mynotesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.noteHolder> {

    List<Notes> notesList = new ArrayList<>();
    private onItemClickListener Listener;

//    public NotesAdapter(ArrayList<Notes> notesList) {
//        this.notesList = notesList;
//    }

    @NonNull
    @Override
    public noteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new noteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull noteHolder holder, int position) {
        Notes currentNote = notesList.get(position);
        holder.title.setText(currentNote.getTitle());
        holder.desc.setText(currentNote.getDesc());
        holder.priority.setText(""+currentNote.getPriority());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setNotesList(List<Notes> list){
        notesList = list;
        notifyDataSetChanged();
    }

    public Notes getNote(int position){
        return notesList.get(position);
    }

    class noteHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView desc;
        TextView priority;
        public noteHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            desc = itemView.findViewById(R.id.textViewDesc);
            priority = itemView.findViewById(R.id.textViewPriority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(Listener!=null && position!=RecyclerView.NO_POSITION){
                        Listener.onItemClick(notesList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Notes note);
    }

    public void setOnItemClickListener(onItemClickListener Listener){
        this.Listener = Listener;
    }
}
