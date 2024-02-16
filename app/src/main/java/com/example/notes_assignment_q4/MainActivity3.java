package com.example.notes_assignment_q4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    //creating variables for edittext and buttons
    EditText noteTitle2,noteContent2;
    Button update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //mapping ID's with variables
        noteTitle2 = findViewById(R.id.notepadTitle2);
        noteContent2 = findViewById(R.id.notepadContent2);
        update = findViewById(R.id.update);
        delete =findViewById(R.id.delete);

        //getting values from main activity
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        String title = intent.getStringExtra("title");
        String content =intent.getStringExtra("content");

        //setting the text
        noteContent2.setText(content);
        noteTitle2.setText(title);

        //creating a intent to send result of this activity
        Intent result = new Intent();

        //setting onClickListener for update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("SimpleDateFormat")
            public void onClick(View view) {
                //getting the text
                String title1 = noteTitle2.getText().toString();
                String content1 = noteContent2.getText().toString();

                //checking all fields are filled with content
                if (title1.isEmpty() || content1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //setting the selection to title column
                    String select = "title=?";
                    //search for noteTitle and update its details
                    String args[] = {title};
                    // class to add values in the database
                    ContentValues values = new ContentValues();
                    values.put(MyContentProvider.title,title1);
                    values.put(MyContentProvider.content,content1);
                    //getting current date and time
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'Date : 'dd-MM-yyyy '\tTime : 'HH:mm:ss ");
                    String savedTime = simpleDateFormat.format(new Date());
                    values.put(MyContentProvider.times,savedTime);

                    // updating a row in database through content URI
                    getContentResolver().update(MyContentProvider.CONTENT_URI, values,select,args);
                    // displaying a toast message
                    android.widget.Toast.makeText(getApplicationContext(), "notes Updated", Toast.LENGTH_LONG).show();

                    //sending the details of book as result
                    result.putExtra("position",position);
                    result.putExtra("title",title1);
                    result.putExtra("content",content1);
                    result.putExtra("time",savedTime);
                    //when successful completion of third activity it sends result to main activity
                    setResult(10,result);
                    //finishing the third activity
                    finish();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setting the selection to title column
                String select = "title =?";
                //search for noteTitle and deletes its details
                String args[] = {title};
                // deleting a row in database through content URI
                getContentResolver().delete(MyContentProvider.CONTENT_URI,select,args);
                // displaying a toast message
                android.widget.Toast.makeText(getApplicationContext(), "notes Deleted", Toast.LENGTH_LONG).show();
                result.putExtra("position",position);
                //when successful completion of third activity it sends result to MainActivity
                setResult(15,result);
                //finishing the third activity
                finish();
            }
        });
    }
}