package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PhoneListAdapter.OnNoteListener {
    public static final int INSERT_CODE = 5;
    public static final int UPDATE_CODE = 6;

    private PhoneViewModel mPhoneViewModel;
    private PhoneListAdapter mAdapter;
    private FloatingActionButton addFab;
    private List<Phone> mPhoneList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == INSERT_CODE){
                Bundle pakunek = data.getExtras();
                Phone phone = new Phone(
                        pakunek.getString("phone_producent"),
                        pakunek.getString("phone_model"),
                        pakunek.getString("phone_version"),
                        pakunek.getString("phone_website")
                );
                mPhoneViewModel.insert(phone);
            }
            if(requestCode == UPDATE_CODE){
                Bundle pakunek = data.getExtras();
                Phone phone = (Phone) pakunek.get("editedPhone");
                mPhoneViewModel.update(phone);
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PhoneListAdapter(this, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPhoneViewModel = new ViewModelProvider(this)
                .get(PhoneViewModel.class);
        //Observer::onChanged -> lambda
//        mPhoneViewModel.getAllWordsPhones().observe(this, phones -> {
//            mAdapter.setPhoneList(phones);
//        });
        mPhoneViewModel.getAllWordsPhones().observe(this, phones -> {
            mAdapter.setPhoneList(phones);
            mPhoneList = phones;
        });





        addFab = findViewById(R.id.addFAB);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, InsertActivity.class);
                startActivityForResult(intencja, INSERT_CODE);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data)
        {
            Toast.makeText(this,"Clearing the data...",
                    Toast.LENGTH_SHORT).show();
            mPhoneViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(Phone phone) {
        Intent intencja  = new Intent(MainActivity.this, InsertActivity.class);
        intencja.putExtra("phonetoEdit", phone);
        startActivityForResult(intencja, UPDATE_CODE);
    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mPhoneViewModel.deletePhone(mPhoneList.get(viewHolder.getAdapterPosition()));
            //mAdapter.notifyDataSetChanged();
        }
    };
}