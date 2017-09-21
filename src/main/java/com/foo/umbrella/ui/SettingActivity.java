package com.foo.umbrella.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.foo.umbrella.R;

import java.util.Arrays;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText zipcode;
    private EditText units;

    EditText input;

    String currentItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        toolbar = (Toolbar)findViewById(R.id.toolbars);
        zipcode = (EditText)findViewById(R.id.enter_zip);
        units = (EditText)findViewById(R.id.enter_units);

        toolbar.setBackgroundColor(Color.parseColor("#757575"));
        toolbar.setTitle("Umbrella");
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_action_name));

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setMessage("Enter your zip code");

        input = new EditText(SettingActivity.this);
        builder.setView(input);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = input.getText().toString();
                zipcode.setText(txt);
            }
        });

        AlertDialog ad = builder.create();
        zipcode.setClickable(true);
        zipcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });



        AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
        builder2.setTitle("Choose the unit");

        // String array for alert dialog multi choice items
        String[] two_units = new String[]{
                "Fahrenheit ",
                "Celsius"
        };

        // Boolean array for initial selected items
        final boolean[] checkedUnit = new boolean[]{
                false, // Fahrenheit
                false // Celsius
        };

        final List<String> UnitsList = Arrays.asList(two_units);

        builder2.setMultiChoiceItems(two_units, checkedUnit, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                currentItem = UnitsList.get(which);
                units.setText(currentItem);
                dialog.dismiss();
            }
        });

        AlertDialog ad2 = builder2.create();
        units.setClickable(true);
        units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad2.show();
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("zipCode",zipcode.getText().toString());
                i.putExtra("unit",units.getText().toString());
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.putExtra("zipCode",zipcode.getText().toString());
        i.putExtra("unit",units.getText().toString());
        startActivity(i);
    }
}
