package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class lease extends AppCompatActivity {
    ListView listView;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease);
        listView=findViewById(R.id. mainLeaseListview);
        String[] value=new  String[]{
                "Lease Agreement View","Go Back"
        };

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,value);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position==0) {
                    Intent intent = new Intent(view.getContext(), leaseagreementshowprofile.class);
                    startActivity(intent);
                }
                if(position==1) {
                    Intent intent = new Intent(view.getContext(), query.class);
                    startActivity(intent);

                }

            }



        });

    }
}
