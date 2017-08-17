package com.example.gretchen.agrodata.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.gretchen.agrodata.ParentActivity;
import com.example.gretchen.agrodata.R;

public class MainPage extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_page);


        addListenerToIcons();
    }
    //When returning to this activity after back the it updates its info
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    //Places onClick Listeners to the icons in the store
    private void addListenerToIcons()
    {
        //Icons for products
        ImageButton poultry = (ImageButton) findViewById(R.id.MP_poultry_button);
        ImageButton dairy = (ImageButton) findViewById(R.id.MP_dairy_button);
        ImageButton faniceous = (ImageButton) findViewById(R.id.MP_farinaceuos_button);
        ImageButton vegetable = (ImageButton) findViewById(R.id.MP_vegetable_button);
        ImageButton fruit = (ImageButton) findViewById(R.id.MP_fruit_button);
        ImageButton grain = (ImageButton) findViewById(R.id.MP_grain_button);
        ImageButton meat = (ImageButton) findViewById(R.id.MP_meat_button);
        ImageButton apiculture = (ImageButton) findViewById(R.id.MP_apiculture_button);

        //The activity that will have the list we want to see
        final Intent showSublist = new Intent(this,SubdivisionList.class);

        //Depending on which icon is pressed is the list that has to be displayed
        View.OnClickListener listener = new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                switch (arg0.getId())
                {
                    case R.id.MP_poultry_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.poultry_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.poultry));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_fruit_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.fruit_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.fruit));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_grain_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.grain_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.grain));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_vegetable_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.vegetable_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.vegetable));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_dairy_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.dairy_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.dairy));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_farinaceuos_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.farinaceous_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.farinaceous));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_meat_button:

                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.meat_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.meat));

                        startActivity(showSublist);
                        break;
                    case R.id.MP_apiculture_button:
                        showSublist.putExtra(getString(R.string.subdivision_key),R.array.apiculture_list);
                        showSublist.putExtra(getString(R.string.list_type),getString(R.string.apiary));

                }
            }

        };

        poultry.setOnClickListener(listener);
        dairy.setOnClickListener(listener);
        faniceous.setOnClickListener(listener);
        meat.setOnClickListener(listener);
        fruit.setOnClickListener(listener);
        vegetable.setOnClickListener(listener);
        grain.setOnClickListener(listener);


    }


   //Goes to add product activity
   public void goToAddProduct(View v)
   {
       Intent intent = new Intent(this, AddProduct.class);
       startActivity(intent);

   }

}
