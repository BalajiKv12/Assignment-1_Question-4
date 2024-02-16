package com.example.notes_assignment_q4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements recyclerInterface {

    FloatingActionButton addButton;
    RecyclerView recyclerView;
    ArrayList<notes> note = new ArrayList<>();
    Notes_RecyclerAdapter adapter = new Notes_RecyclerAdapter(this,note,this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.floatingAddButton);
        recyclerView = findViewById(R.id.recycleView);

        //printind the notes if present
        printView();

        //setting onClickListener for add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launching new intent
                Intent notes = new Intent(MainActivity.this,MainActivity2.class);
                getResult.launch(notes);
            }
        });

    }

    ActivityResultLauncher<Intent> getResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("Range")
        @Override
        public void onActivityResult(ActivityResult result) {
            //if result code is 5 then the result comes from save button which is in second activity
            if(result.getResultCode()==5) {
                if(adapter.getItemCount()!=0) {
                    Intent intent = result.getData();
                    String Title = intent.getStringExtra("Title");
                    String content = intent.getStringExtra("content");
                    String time = intent.getStringExtra("time");
                    //adding the data at last position
                    note.add(new notes(Title, content, time));
                    //notifying the adapter after inserting
                    adapter.notifyItemInserted(note.size() - 1);
                    Log.i("addingg","Recycler addd");
                }
                else{
                    //if the recycler view is empty then printing whole view
                    printView();
                    Log.i("Printing","Recycler priint");
                }
            }
            //if result code is 10 then the result comes from update button which is in third activity
            if(result.getResultCode()==10) {
                //getting the intent and updated details of the notes
                Intent intent = result.getData();
                String p = intent.getStringExtra("position");
                int position = Integer.parseInt(p);
                String Title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");
                String time = intent.getStringExtra("time");

                //replacing the notes with updated notes in the same position
                note.set(position,new notes(Title,content,time));
                //notifying the adapter for some changes done
                adapter.notifyItemChanged(position);
            }
            //if result code is 15 then the result comes from delete button which is in third activity
            if(result.getResultCode()==15) {
                //getting the intent and deleting the notes from the arraylist
                Intent intent = result.getData();
                String p = intent.getStringExtra("position");
                int position = Integer.parseInt(p);
                //removing the note
                note.remove(position);
                //notifying the adapter for deleting the book
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, note.size());
            }
        }
    });

    //setting onClickListener for recyclerView
    @Override
    public void onItemClick(notes note, int position) {

        //launching new activity
        Intent onClick = new Intent(MainActivity.this, MainActivity3.class);
        String title = note.getTitle();
        String content = note.getContent();
        String pos = Integer.toString(position);

        //sending data to next activity
        onClick.putExtra("title",title);
        onClick.putExtra("content",content);
        onClick.putExtra("position",pos);
        getResult.launch(onClick);
    }

    @SuppressLint("Range")
    public void printView() {
        // creating a cursor object of the
        // content URI
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,
                null, null, null, null);
        // iteration of the cursor
        // to print whole table
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String title, content, time;
                while (!cursor.isAfterLast()) {
                    title = cursor.getString(cursor.getColumnIndex(MyContentProvider.title));
                    content = cursor.getString(cursor.getColumnIndex(MyContentProvider.content));
                    time = cursor.getString(cursor.getColumnIndex(MyContentProvider.times));
                    note.add(new notes(title, content, time));
                    cursor.moveToNext();
                }
                //setting adapter with recyclerView
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        }
    }
}