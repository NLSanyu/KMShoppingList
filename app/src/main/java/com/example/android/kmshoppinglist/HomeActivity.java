package com.example.android.kmshoppinglist;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private ListAdapter mAdapter;
    //public static final String SHARED_PREFS = "SharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn_add_item = findViewById(R.id.btn_add_item);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddItemIntent = new Intent(HomeActivity.this, HomeActivity.class); //change this
                startActivity(AddItemIntent);
            }
        });


        ListDBHelper dbHelper = new ListDBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();

        RecyclerView mRecyclerView = findViewById(R.id.rv_all_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListAdapter(this, getAllItems());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(DocListActivity.this, "Click from HomeActivity", Toast.LENGTH_LONG).show();
                //Intent HomeIntent = new Intent(HomeActivity.this, HomeActivity.class);
                //startActivity(HomeIntent);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItemId((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    private void removeItemId(long id){
        mDatabase.delete(ListContract.ItemsList.TABLE_NAME,
                ListContract.ItemsList._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent HomeIntent = new Intent(HomeActivity.this, HomeActivity.class); //change this
        startActivity(HomeIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch(itemSelected){
            case R.id.item_add_list:
                Intent ProfileIntent = new Intent(HomeActivity.this, HomeActivity.class); //change this
                startActivity(ProfileIntent);
                break;

            default: break;
        }
        return super.onOptionsItemSelected(item);
    }


    private Cursor getAllItems() {
        return mDatabase.query(
                ListContract.ItemsList.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListContract.ItemsList.COLUMN_ITEM_NAME
        );

    }

    private void addToItemsTable(){

        String item_name = " ", item_amount = " ", item_unit_price = " ", item_total_price = " ";

        ContentValues cv = new ContentValues();
        cv.put(ListContract.ItemsList.COLUMN_ITEM_NAME, item_name);
        cv.put(ListContract.ItemsList.COLUMN_ITEM_AMOUNT, item_amount);
        cv.put(ListContract.ItemsList.COLUMN_ITEM_UNIT_PRICE, item_unit_price);
        cv.put(ListContract.ItemsList.COLUMN_ITEM_TOTAL_PRICE, item_total_price);

        mDatabase.insert(ListContract.ItemsList.TABLE_NAME, null, cv);
        //mAdapter.swapCursor(getAllDoctors());

    }


}

