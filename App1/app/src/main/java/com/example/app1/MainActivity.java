package com.example.app1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    android.widget.EditText ImieEditText;
    android.widget.EditText NazwiskoEditText;
    android.widget.Button OcenyButton;
    android.widget.EditText GradeNumberEditText;
    android.widget.TextView SredniaText;
    android.widget.Button ExitButton;
    Double srednia = 0.0;

    public Boolean allTrue(Boolean [] tab){
        for (Boolean i:tab)
            if(!i)
                return false;
        return true;
    }

    public boolean isInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bundle pakunek = data.getExtras();
            srednia = pakunek.getDouble("srednia");

            SredniaText.setText("Średnia: " + String.valueOf(Math.round(srednia*100.0)/100.0));
            if(srednia >= 3) {
                ExitButton.setText("Super :)");
                ExitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Gratulacje! Otrzymujesz zaliczenie!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
            else {
                ExitButton.setText("Tym razem mi nie poszło");
                ExitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Wysyłam podanie o zaliczenie warunkowe", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }

            ExitButton.setVisibility(View.VISIBLE);
            SredniaText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImieEditText = findViewById(R.id.name);
        NazwiskoEditText = findViewById(R.id.surname);
        GradeNumberEditText = findViewById(R.id.grade_number);
        OcenyButton = findViewById(R.id.oceny);
        SredniaText = findViewById(R.id.srednia_text);
        ExitButton = findViewById(R.id.exit_button);

        if(savedInstanceState != null){
            Double foo = savedInstanceState.getDouble("srednia");
            if(foo != 0.0){
                srednia = foo;
                SredniaText.setText("Średnia: " + String.valueOf(Math.round(srednia*100.0)/100.0));
                if(srednia >= 3) {
                    ExitButton.setText("Super :)");
                    ExitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "Gratulacje! Otrzymujesz zaliczenie!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
                else {
                    ExitButton.setText("Tym razem mi nie poszło");
                    ExitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "Wysyłam podanie o zaliczenie warunkowe", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }

                ExitButton.setVisibility(View.VISIBLE);
                SredniaText.setVisibility(View.VISIBLE);
            }
        }

        
        Boolean flags[] = new Boolean[3];
        for(int i=0; i<3; i++)
            flags[i]=false;
        OcenyButton.setVisibility(View.INVISIBLE);


        ImieEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(ImieEditText.getText().toString().isEmpty()){
                   flags[0]=false;
                }else
                    flags[0]=true;

                if(allTrue(flags))
                    OcenyButton.setVisibility(View.VISIBLE);
                else
                    OcenyButton.setVisibility(View.INVISIBLE);



                //ImieEditText.setError("aaaa");
                //OcenyButton.setVisibility(View.INVISIBLE);
                //ImieEditText.getText().toString();
                //TextUtils.isEmpty();
                ///ImieEditText.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

        ImieEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(TextUtils.isEmpty(ImieEditText.getText().toString())){
                        ImieEditText.setError("Imie nie moze byc puste");
                        Toast.makeText(MainActivity.this, "Imie nie moze byc puste", Toast.LENGTH_SHORT).show();
                        flags[0]=false;
                    }else
                        flags[0]=true;

                    if(allTrue(flags))
                        OcenyButton.setVisibility(View.VISIBLE);
                    else
                        OcenyButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        NazwiskoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(NazwiskoEditText.getText().toString().isEmpty()){
                    flags[1]=false;
                }else
                    flags[1]=true;

                if(allTrue(flags))
                    OcenyButton.setVisibility(View.VISIBLE);
                else
                    OcenyButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        NazwiskoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(TextUtils.isEmpty(NazwiskoEditText.getText().toString())){
                        NazwiskoEditText.setError("Nazwisko nie moze byc puste");
                        Toast.makeText(MainActivity.this, "Nazwisko nie moze byc puste", Toast.LENGTH_SHORT).show();
                        flags[1]=false;
                    }else
                        flags[1]=true;

                    if(allTrue(flags))
                        OcenyButton.setVisibility(View.VISIBLE);
                    else
                        OcenyButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        GradeNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isInt(GradeNumberEditText.getText().toString())){
                    int grade_c = Integer.parseInt(GradeNumberEditText.getText().toString());
                    if(grade_c>=5 && grade_c<=15)
                        flags[2]=true;
                    else
                        flags[2]=false;
                }else
                    flags[2]=false;

                if(allTrue(flags))
                    OcenyButton.setVisibility(View.VISIBLE);
                else
                    OcenyButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        GradeNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    if (isInt(GradeNumberEditText.getText().toString())) {
                        int grade_c = Integer.parseInt(GradeNumberEditText.getText().toString());
                        if (grade_c >= 5 && grade_c <= 15)
                            flags[2] = true;
                        else {
                            flags[2] = false;
                            GradeNumberEditText.setError("Liczba musi byc z zakresu [5,15]");
                            Toast.makeText(MainActivity.this, "Liczba musi byc z zakresu [5,15]", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        flags[2] = false;
                        GradeNumberEditText.setError("Podaj liczbę!");
                    }
                    if (allTrue(flags))
                        OcenyButton.setVisibility(View.VISIBLE);
                    else
                        OcenyButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        OcenyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, OcenyAktywnosc.class);
                intencja.putExtra("liczba_ocen", Integer.parseInt(GradeNumberEditText.getText().toString()));
                //startActivity(intencja);
                ///Start activity jest depricated na stacku jest rozwiazanie jak bedzie potrzebne
                startActivityForResult(intencja,0);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putDouble("srednia", srednia);

        super.onSaveInstanceState(outState);
    }

}