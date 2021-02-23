package com.example.todoapllication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter adapter;
    ListView viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // changing the color of plus in the floating action button.
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.button);
        myFab.setColorFilter(Color.WHITE);


        items = new ArrayList<String>();
        readItems();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        viewList = (ListView) findViewById(R.id.lstView);
        viewList.setAdapter(adapter);
        setupListViewListener();

    }

    public void addItems(View view){
        EditText txt = (EditText) findViewById(R.id.addNewItem);
        String task = txt.getText().toString();

        adapter.add(task);
        saveFile();
        Log.i("Add Item info", "Item added");
    }

    private void saveFile(){

        File filesDire = getFilesDir();
        File todoFile = new File(filesDire, "todo.txt");
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(todoFile));
            br.flush();
            for(String line : items){
                br.write(line);
                br.newLine();
            }
            br.close();
            Log.i("Save File Info", "File Saved");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readItems() {
        File filesDire = getFilesDir();
        File todoFile = new File(filesDire, "todo.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(todoFile));
            String line = br.readLine();
            while (line != null) {
                items.add(line);
                line = br.readLine();
            }
            br.close();
            Log.i("Read File Info", "File Read");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupListViewListener(){
        viewList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                saveFile();
                adapter.notifyDataSetChanged();
                Log.i("item delete info", "item deleted");
                return true;
            }
        });
    }

}