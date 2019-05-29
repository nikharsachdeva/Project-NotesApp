package com.example.android.notes1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText editText;
    Button save,cancel;
    SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MainActivity mainActivity = new MainActivity();

        editText = findViewById(R.id.editField);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        Intent intent = getIntent();
        String EditMee = intent.getStringExtra("EditMe=");
        editText.setText(EditMee);

    }

    public void saveFun(View view){

        createDatabaseandTable();
        insertQuery();

//        Intent intent = new Intent(this,MainActivity.class);
//        intent.putExtra("Note=", editText.getText().toString());
//        setResult(RESULT_OK, intent);
        finish();
        //fetchQuery();



    }

    public void createDatabaseandTable(){

        myDatabase = this.openOrCreateDatabase("Notes",MODE_PRIVATE,null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY,text VARCHAR)");
    }

    public void insertQuery(){

        //myDatabase.execSQL("DELETE FROM notes");
        //myDatabase.execSQL("INSERT INTO notes(text) VALUES('First Note') ");
        //Toast.makeText(this,"Successful",Toast.LENGTH_SHORT);
        myDatabase.execSQL("INSERT INTO notes(text) VALUES ('"+editText.getText().toString()+"')");
    }

    /*public void fetchQuery(){
        Cursor c = myDatabase.rawQuery("SELECT * FROM notes",null);

        int textIndex = c.getColumnIndex("text");
//        c.moveToFirst();

        if (c != null)
            if (c.moveToFirst()) {
                do {
                    Log.i("entry",c.getString(textIndex));
                } while (c.moveToNext());
            }
    } */

    public void cancelFun(View view){

        finish();


    }

}
