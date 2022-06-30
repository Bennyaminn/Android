package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OcenyAktywnosc extends AppCompatActivity {
    Button SredniaButton;
    List<ModelOceny> oceny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oceny_aktywnosc);

        //Odczyt danych jesli sa
        if(savedInstanceState == null){
            //Odczytanie przesłanych danych
            Bundle bundle = getIntent().getExtras();
            int liczba_ocen = bundle.getInt("liczba_ocen");

            //Wypelnienie listy przedmiotow
            String[] nazwyPrzedmiotów = getResources().getStringArray(R.array.przedmioty);
            oceny = new ArrayList<ModelOceny>();
            for(int i=0; i<liczba_ocen; i++){
                oceny.add(new ModelOceny(nazwyPrzedmiotów[i], 2));
            }
        }else{
            int liczba_ocen = savedInstanceState.getInt("size");
            oceny = new ArrayList<ModelOceny>();
            int [] oceny_ = savedInstanceState.getIntArray("oceny");

            String[] nazwyPrzedmiotów = savedInstanceState.getStringArray("name");
//            for(int i = 0; i<liczba_ocen; i++){
//                Log.v("Tablice", String.valueOf(nazwyPrzedmiotów[i]));
//            }
            for(int i = 0; i<liczba_ocen; i++){
                oceny.add(new ModelOceny(nazwyPrzedmiotów[i],oceny_[i]));
            }
        }




        //Obsluga przycisku
        SredniaButton = findViewById(R.id.button_srednia);
        SredniaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double srednia = 0.0;
                for(ModelOceny model : oceny){
                    srednia += model.getOcena();
                }
                srednia /= oceny.size();

                Intent intencja = new Intent();
                intencja.putExtra("srednia",srednia);
                setResult(RESULT_OK,intencja);
                finish();
            }
        });

        //Inicjalizacja RecyclerView
        RecyclerView ocenyRecyclerView = findViewById(R.id.ocenyRecyclerView);
        InteraktywnyAdapterTablicy myAdapter = new InteraktywnyAdapterTablicy(this, oceny);
        ocenyRecyclerView.setAdapter(myAdapter);
        ocenyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        String [] names = new String[oceny.size()];
        int [] oceny_ = new int[oceny.size()];

        for(int i  = 0; i< oceny.size(); i++){
            names[i] = oceny.get(i).getNazwa();
            oceny_[i] = oceny.get(i).getOcena();
        }

        outState.putInt("size", oceny.size());
        outState.putStringArray("name", names);
        outState.putIntArray("oceny", oceny_);

        super.onSaveInstanceState(outState);
    }

}