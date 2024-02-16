package com.example.notes_assignment_q4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    //creating variables for edittext and buttons
    EditText noteTitle,noteContent;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //mapping ID's with variables
        noteTitle = findViewById(R.id.notepadTitle);
        noteContent = findViewById(R.id.notepadContent);
        save = findViewById(R.id.save);


        Intent result = new Intent();
        //setting onClickListener for all buttons
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("SimpleDateFormat")
            public void onClick(View view) {
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();

                if(title.isEmpty() || content.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    //inserting the values
                    ContentValues values = new ContentValues();
                    values.put(MyContentProvider.title,title);
                    values.put(MyContentProvider.content,content);
                    //getting current date and time
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'Date : 'dd-MM-yyyy '\tTime : 'HH:mm:ss ");
                    String savedTime = simpleDateFormat.format(new Date());
                    values.put(MyContentProvider.times,savedTime);
                    // inserting into database through content URI
                    getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                    Toast.makeText(getApplicationContext(),"Notes Saved",Toast.LENGTH_SHORT).show();
                    result.putExtra("Title",title);
                    result.putExtra("content",content);
                    result.putExtra("time",savedTime);
                    setResult(5,result);
                    finish();
                }
            }
        });
    }
}