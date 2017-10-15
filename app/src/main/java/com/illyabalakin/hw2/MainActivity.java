/*
 * @author: Illya Balakin
 * Created on 10/10/2017
 * CS4322
 * HW 2
 */

package com.illyabalakin.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SAVED_INFO_NAME = "TagInfo";
    EditText editSearchQuery, editSearchTag;
    Button btnSave, btnClear;

    boolean inEditMode, deleteSelected;
    int editIndex, deleteIndex;

    // Venue listItem
    private List<SearchItem> queryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(SAVED_INFO_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("QUERY_SIZE", queryList.size());


        for (int i = 0; i < queryList.size(); i++) {
            String tagIdent = "tag" + Integer.toString(i);
            editor.putString(tagIdent, queryList.get(i).getTag());

            String queryIdent = "query" + Integer.toString(i);
            editor.putString(queryIdent, queryList.get(i).getSearchQuery());
        }

        // Commit the edits!
        editor.commit();
    }

    private void init() {
        queryList = new ArrayList<>();

        editSearchQuery = (EditText) findViewById(R.id.editTextSearchQuery);
        editSearchTag = (EditText) findViewById(R.id.editTextTag);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnClear = (Button) findViewById(R.id.btnClear);

        inEditMode = false;
        editIndex = -1;

        deleteSelected = false;
        deleteIndex = -1;

        btnSaveListener();
        btnClearListener();

        setSavedQueryListItems();
        createRecyclerList();
    }

    private void setSavedQueryListItems() {
        SharedPreferences settings = getSharedPreferences(SAVED_INFO_NAME, 0);
        int listSize = settings.getInt("QUERY_SIZE", 0);

        for (int i = 0; i < listSize; i++) {
            String tagIdent = "tag" + Integer.toString(i);
            String queryIdent = "query" + Integer.toString(i);

            String tag = settings.getString(tagIdent, "");
            String query = settings.getString(queryIdent, "");

            queryList.add(new SearchItem(tag, query));
        }

    }


    private void btnSaveListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!inEditMode) {
                    if (editSearchQuery.length() == 0 || editSearchTag.length() == 0) {
                        missingTextAlert();
                    } else {
                        queryList.add(new SearchItem(editSearchTag.getText().toString(), editSearchQuery.getText().toString()));

                        createRecyclerList();
                    }
                } else {
                    queryList.remove(editIndex);
                    queryList.add(editIndex, new SearchItem(editSearchTag.getText().toString(), editSearchQuery.getText().toString()));

                    inEditMode = false;
                    editIndex = -1;

                    createRecyclerList();
                }

                // clear previous edit text
                editSearchTag.setText("");
                editSearchQuery.setText("");
            }
        });
    }

    /**
     * clears query list
     */
    private void btnClearListener() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllAlert();
            }
        });
    }


    /**
     * creates RecyclerView
     */
    private void createRecyclerList() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        MyAdapter adapter = new MyAdapter(queryList, this);
        rv.setAdapter(adapter);

    }

    protected void editBtnClicked(int position) {
        inEditMode = true;
        editIndex = position;

        editSearchTag.setText(queryList.get(position).getTag());
        editSearchQuery.setText(queryList.get(position).getSearchQuery());
    }

    protected void tagBtnClicked(int position) {
        String query = queryList.get(position).getSearchQuery();

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getURL(query)));
        startActivity(i);
    }

    protected void deleteBtnClicked(int position) {
        deleteAlert(position);
    }

    protected void deleteIndex(int position) {
        queryList.remove(position);
        createRecyclerList();
    }

    /**
     * creates URL string from passed search query
     * Sample URL: http://twitter.com/search?q=TAG1%20TAG2&src=typd
     * @param query - search query
     * @return URL string
     */
    protected String getURL(String query) {
        String start = "http://twitter.com/search?q=";
        String inter = query.replace(" ", "%20");
        String end = "&src=typd";

        return (start+inter+end);
    }


    /**
     * Missing Text AlertDialog
     */
    private void missingTextAlert() {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Missing Text");
        bld.setMessage("Please enter a search query and tag it.");
        bld.setPositiveButton("OK", null);
        AlertDialog missingDialog = bld.create();
        missingDialog.show();
    }


    /**
     * Clear All AlertDialog
     */
    private void clearAllAlert() {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Are you sure?");
        bld.setMessage("This will delete all saved sea");
        bld.setPositiveButton("Erase", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                queryList.clear();

                createRecyclerList();
            }
        });

        bld.setNegativeButton("Cancel", null);
        AlertDialog missingDialog = bld.create();
        missingDialog.show();
    }

    private void deleteAlert(final int position) {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Are you sure?");
        bld.setMessage("This will delete the selected query");
        bld.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteIndex(position);
            }
        });

        bld.setNegativeButton("Cancel", null);
        AlertDialog deleteDialog = bld.create();
        deleteDialog.show();
    }


}
