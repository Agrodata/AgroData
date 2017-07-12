package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SubdivisionList extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdivision_list);
        //Shows subdivision
        showList();
    }
    private void showList()
    {
        //Listview that will hold all the subdivisions
        ListView list = (ListView) findViewById(R.id.SLP_subdivision_list_ListView);

        //Gets info about the list that will be displayed
        Bundle listDivisions= getIntent().getExtras();

        //Holds the array with the subdivisions of the icon pressed
        String subdivision[] = getResources().getStringArray(listDivisions.getInt(getString(R.string.subdivision_key)));

        //Hold the type of the sublist
        final String type = listDivisions.getString(getString(R.string.list_type));

        ArrayList<HashMap<String,String >> sublist = new ArrayList<HashMap<String, String>>();

        for(int i =0;i<subdivision.length;i++)
        {
            HashMap<String,String> name = new HashMap<String,String>();

            name.put("name",subdivision[i]);
            sublist.add(name);
            if(i==subdivision.length-1)
            {
                name.put("name",getString(R.string.see_all));
            }
        }


        ListAdapter adapter = new SimpleAdapter(this,sublist,R.layout.subdivision_layout,
                new String[]{"name"},
                new int[] {R.id.SL_sudivision_name_TextView});

        list.setAdapter(adapter);
        //perform listView item click event
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView sublist = (TextView) view.findViewById(R.id.SL_sudivision_name_TextView);

                Intent product_list = new Intent(SubdivisionList.this,ProductList.class);
                product_list.putExtra(getString(R.string.list_type),type);
                product_list.putExtra(getString(R.string.subdivision_key),sublist.getText().toString());
                startActivity(product_list);
            }
        });

    }
}
