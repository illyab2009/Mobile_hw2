package com.illyabalakin.hw2;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences savedInfo;
    private TableLayout myTL;
    private static final String SAVED_INFO_NAME = "TagInfo";
    EditText editSearchQuery, editSearchTag;
    Button btnSave, btnClear;


    //TODO: Do a TableLayout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        savedInfo = getSharedPreferences(SAVED_INFO_NAME, MODE_PRIVATE);

        editSearchQuery = (EditText) findViewById(R.id.editTextSearchQuery);
        editSearchTag = (EditText) findViewById(R.id.editTextTag);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnSaveListener();
        btnClearListener();

        //to save

        SharedPreferences.Editor myEditor = savedInfo.edit();
        myEditor.putString("key", "value");
        myEditor.apply();
        /**if(!tagAlreadySaved) {
            refreshButton(tab, false);
        } */
    }


    private void btnSaveListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editSearchQuery.length() == 0) {
                    alert();
                }

                else {

                }
            }
        });
    }


    private void btnClearListener() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    /**
     * AlertDialog
     */
    private void alert() {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Missing Text");
        bld.setMessage("Please enter a search query and tag it.");
        bld.setPositiveButton("OK", null);
        AlertDialog missingDialog = bld.create();
        missingDialog.show();
    }


}
