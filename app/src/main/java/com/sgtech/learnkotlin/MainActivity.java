package com.sgtech.learnkotlin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;



import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FbRecycler fbRecycler;
    ArrayList<DataHolder> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData();
        recyclerView = findViewById(R.id.recycler);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Learn Kotlin - A to Z Tutorial");
        setData();
    }

    private void setData() {
        fbRecycler = new FbRecycler(arraylist, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(fbRecycler);
    }

    private void addData() {
        arraylist.add(new DataHolder(getString(R.string.kotlin_introduction)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_syntax)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_comment)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_variables)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_data_types)));
        arraylist.add(new DataHolder("Kotlin Numbers"));
        arraylist.add(new DataHolder(getString(R.string.kotlin_boolean)));
        arraylist.add(new DataHolder("Kotlin Char"));
        arraylist.add(new DataHolder(getString(R.string.kotlin_string)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_if_else)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_when)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_while_loop)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_break_continue)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_array)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_for_loop)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_ranges)));
        arraylist.add(new DataHolder(getString(R.string.kotlin_functions)));
    }


}