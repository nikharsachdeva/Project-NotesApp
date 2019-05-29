package com.example.android.notes1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ListView listView;
    String note;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    static ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> notesList = new ArrayList<String>();
    SQLiteDatabase myDatabase;
    Cursor c;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.addNote:
                Intent intent = new Intent(this,Main2Activity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }
    public void fetchMyData(){

        c = myDatabase.rawQuery("SELECT * FROM notes",null);

        int textIndex = c.getColumnIndex("text");
//        c.moveToFirst();

        if (c != null)
            if (c.moveToFirst()) {
                do {
                    Log.i("entry",c.getString(textIndex));
                    notesList.add(c.getString(textIndex));
                } while (c.moveToNext());
            }


    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


            }
        }
    }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        myDatabase = this.openOrCreateDatabase("Notes",MODE_PRIVATE,null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY,text VARCHAR)");
        fetchMyData();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notesList);
        arrayAdapter.notifyDataSetChanged();



        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);

                intent.putExtra("EditMe=",notesList.get(i));
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),friendsName.get(i),Toast.LENGTH_SHORT).show();
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int ii, long l) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete!!")
                        .setMessage("Do you really want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Log.i("Fuck",notesList.get(ii));
                                myDatabase.execSQL("DELETE FROM notes WHERE text='"+notesList.get(ii)+"'");
                                dialogInterface.dismiss();
                                Toast.makeText(MainActivity.this,"Note Deleted Successfully!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }).show();


                //Toast.makeText(getApplicationContext(),listView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                return true;
            }
        });






    }

}
