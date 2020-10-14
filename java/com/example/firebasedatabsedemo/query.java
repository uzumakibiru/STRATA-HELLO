package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class query extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        listView=findViewById(R.id. listview);




        String[] value=new  String[]{
                "Cleaning","Lease Agreement","Issues","Go Back"
        };

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,value);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                   Intent intent=new Intent(view.getContext(),cleaner.class);
                   startActivity(intent);

                }
                if(position==1) {
                    Intent intent = new Intent(view.getContext(), lease.class);
                    startActivity(intent);
                }
                if(position==2) {
                    Intent intent = new Intent(view.getContext(), issueoption.class);
                    startActivity(intent);
                }
                if(position==3) {
                    Intent intent = new Intent(view.getContext(), Homescree.class);
                    startActivity(intent);
                }
            }



        });

    }
}
