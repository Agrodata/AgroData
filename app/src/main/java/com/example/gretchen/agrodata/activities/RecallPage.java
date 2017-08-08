package com.example.gretchen.agrodata.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gretchen.agrodata.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecallPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recall_page);

        setDate();
    }

    private void setDate()
    {
        //Current Time
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String current_date = dateFormat.format(date);

        //Date TextView
        TextView dateText = (TextView) findViewById(R.id.RP_date_TextView);

        dateText.setText(current_date);
    }
    private void showWarningMessage()
    {
        //Warning that all text must be filled
        AlertDialog.Builder warning = new AlertDialog.Builder(this);
        warning.setMessage(R.string.all_must_be_filled_msg)
                //If yes user account is deleted
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        warning.create();
        warning.show();
    }
    public void submitRecall(View view)
    {
        EditText amount = (EditText) findViewById(R.id.RP_amount_EditText);
        EditText message = (EditText) findViewById(R.id.RP_reason_EditText);

        if(amount.getText().toString().isEmpty()||message.getText().toString().isEmpty())
        {
            showWarningMessage();
        }
    }
}
