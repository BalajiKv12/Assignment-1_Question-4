package com.example.notes_assignment_q4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notes_RecyclerAdapter extends RecyclerView.Adapter<Notes_RecyclerAdapter.MyViewHolder> {

    Context context;
    //creating arraylist of note objects
    ArrayList<notes> note;
    private recyclerInterface recyclerInterface;

    //creating constructor for adapter
    public Notes_RecyclerAdapter(Context context, ArrayList<notes> book, recyclerInterface recyclerInterface) {
        this.context = context;
        this.note = book;
        this.recyclerInterface=recyclerInterface;
    }

    @NonNull
    @Override
    //below method used to inflate the view when user scrolls up/down to show the details which coundn't fit the screen

    public Notes_RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardlayout,parent,false);
        return new Notes_RecyclerAdapter.MyViewHolder(view);
    }

    @Override
    @SuppressLint({"RecyclerView", "SimpleDateFormat", "SetTextI18n"})
    //onBindViewHolder method for setting details in view for all note titles one by one by using their position
    public void onBindViewHolder(@NonNull Notes_RecyclerAdapter.MyViewHolder holder,  int position) {

        //setting the details of the note to show
        holder.title.setText(note.get(position).getTitle());
        holder.time.setText("Saved on:\n"+note.get(position).getTime());

        //below method is for setting on click response of the view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerInterface.onItemClick(note.get(position),position);
            }
        });
    }

    //getItemcount method return the size of the items to be displayed
    @Override
    public int getItemCount() {
        return note.size();
    }


    //MyViewHolder method for setting the view for recycle view
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,time;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //assigning the views by finding their respective id
            title = itemView.findViewById(R.id.noteTitle);
            time = itemView.findViewById(R.id.time);
            cardView=itemView.findViewById(R.id.Cardlayout);
        }
    }
}
