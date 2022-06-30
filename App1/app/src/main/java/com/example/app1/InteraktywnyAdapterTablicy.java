package com.example.app1;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InteraktywnyAdapterTablicy extends RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder> {
    //lista przechowujące modele ocen
    private List<ModelOceny> mListaOcen;

    //odwołanie do layoutu
    private LayoutInflater mPompka;

    //konstruktor
    public InteraktywnyAdapterTablicy(Activity kontekst, List<ModelOceny> listaocen){
        mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = listaocen;
    }

    //tworzy główny element layout i tworzy pojemnik (holder) dla danego wiersza

    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        //utworzenie layoutu wiersza na podstawie XMLa
        View wiersz = mPompka.inflate(R.layout.wiersz_oceny, viewGroup, false);
        //zwrócenie nowego obiektu holdera
        return new OcenyViewHolder(wiersz);
    }

    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder ocenyViewHolder, int numerWiersza) {
        //powiązanie grupy przycisków radiowych z wierszem listy
        ModelOceny value= mListaOcen.get(numerWiersza);
        ocenyViewHolder.mGrupaPrzyciskow.setTag(numerWiersza);
        //ustawienie nazwy przedmiotu
        ocenyViewHolder.mPoleTekstowe.setText(value.getNazwa());
        //zaznaczenie odpowiedniego przycisku radiowego
        int id_radio = R.id.ocena_2;
        switch (value.getOcena()){
            case 3:
                id_radio = R.id.ocena_3;
                break;
            case 4:
                id_radio = R.id.ocena_4;
                break;
            case 5:
                id_radio = R.id.ocena_5;
                break;
        }

        ocenyViewHolder.mGrupaPrzyciskow.check(id_radio);
    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount()
    {
        //w zależności od kolekcji, w której przechowywane są elementy
        return mListaOcen.size();
    }


    //pojemnik przechowujący referencje do potrzebnych elementów wiersza
    //nadaje się też jako obiekt implementujący listenery - każdy wiersz ma własny holder
    public class OcenyViewHolder extends  RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener{
        //pola przechowujące referencje do elementów wiersza
        android.widget.TextView mPoleTekstowe;
        android.widget.RadioGroup mGrupaPrzyciskow;

        //odczytuje referencje do elementów i ustawia listenery
        public OcenyViewHolder(@NonNull View glownyElementWiersza) {
            super(glownyElementWiersza);
            mPoleTekstowe = glownyElementWiersza.findViewById(R.id.nazwa_przedmiotu);
            mGrupaPrzyciskow = glownyElementWiersza.findViewById(R.id.radioGroup);
            mGrupaPrzyciskow.setOnCheckedChangeListener(this);
            //odczytanie referencji do elementów wiersza
            //...
            //ustawienie obsługi zdarzeń w komponentach znajdujących się w wierszu
            //...
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch(i){
                case R.id.ocena_2:
                    mListaOcen.get((Integer) mGrupaPrzyciskow.getTag()).setOcena(2);
                    break;
                case R.id.ocena_3:
                    mListaOcen.get((Integer) mGrupaPrzyciskow.getTag()).setOcena(3);
                    break;
                case R.id.ocena_4:
                    mListaOcen.get((Integer) mGrupaPrzyciskow.getTag()).setOcena(4);
                    break;
                case R.id.ocena_5:
                    mListaOcen.get((Integer) mGrupaPrzyciskow.getTag()).setOcena(5);
                    break;
            }
        }
        //implementacje interfejsów obsługujących zdarzenia
        //...
    }
}
