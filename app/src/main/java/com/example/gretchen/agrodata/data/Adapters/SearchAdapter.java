package com.example.gretchen.agrodata.data.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;
import com.example.gretchen.agrodata.data.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gretchen on 6/1/2017.
 */

public class SearchAdapter extends ArrayAdapter<HashMap<String, String>> {


    public SearchAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Get HashMap from this position
        HashMap<String,String> map = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.search_results_layout, parent, false);
        }

        //TextViews from product layout
        TextView type = (TextView) view.findViewById(R.id.SRL_object_type_TextView);
        TextView id = (TextView) view.findViewById(R.id.SRL_id_TextView);
        TextView name = (TextView) view.findViewById(R.id.SRL_object_name_TextView);
        TextView info = (TextView) view.findViewById(R.id.SRL_info_TextView);
        TextView info2 = (TextView) view.findViewById(R.id.SRL_info2_TextView);
        TextView info3 = (TextView) view.findViewById(R.id.SRL_info3_TextView);
        TextView info_title = (TextView) view.findViewById(R.id.SRL_info_title_TextView);
        TextView info_title2 = (TextView) view.findViewById(R.id.SRL_info_title2_TextView);
        TextView info_title3 = (TextView) view.findViewById(R.id.SRL_info_title3_TextView);


        if(map.get("uniqueID")!=null)
        {
            //This is a product on the list
            type.setText("p");
            //Assigning values to the TextViews so that it may appear in the list
            id.setText(map.get(Product.KEY_ID));
            name.setText(map.get(Product.KEY_name));
            info.setText(map.get(Product.KEY_price));
            info2.setText(map.get(Product.KEY_amount));
            info3.setText(map.get(Product.KEY_dateAdded));
            info_title.setText(R.string.product_price);
            info_title2.setText(R.string.product_amount);
            info_title3.setText(R.string.date_product_added);

            info_title3.setVisibility(View.VISIBLE);
            info3.setVisibility(View.VISIBLE);

        }
        else
       {



           //this is a user on the list
            type.setText("u");
           //Assigning values to the TextViews so that it may appear in the list
           //We are using the product layout so the TextView values must be changed
            id.setText(map.get("id"));
            info.setText(map.get("email"));
            name.setText(map.get("name"));
            info2.setText(map.get("phone"));
            info_title.setText(R.string.email);
            info_title2.setText(R.string.phone);

            info3.setVisibility(View.INVISIBLE);
            info_title3.setVisibility(View.INVISIBLE);
       }
        return view;
    }

}
