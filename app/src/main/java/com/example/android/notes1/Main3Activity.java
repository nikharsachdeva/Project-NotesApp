package com.example.android.notes1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

    EditText editText;
    Button save,cancel;
    SQLiteDatabase myDatabase;
    String EditMee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = findViewById(R.id.editField);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        Intent intent = getIntent();
         EditMee = intent.getStringExtra("EditMe=");
        editText.setText(EditMee);
    }

    public void saveFun(View view){

        createDatabaseandTable();
        EditQuery();

        finish();




    }

    public void createDatabaseandTable(){

        myDatabase = this.openOrCreateDatabase("Notes",MODE_PRIVATE,null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY,text VARCHAR)");
    }

    public void EditQuery(){

        myDatabase.execSQL("UPDATE notes SET text='"+editText.getText().toString()+"' WHERE text='"+EditMee+"'");
    }


    public void cancelFun(View view){

        finish();


    }

}

