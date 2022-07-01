package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    private Button websiteButton;
    private Button cancelButton;
    private Button saveButton;

    private EditText manudacturerTextEdit;
    private EditText modelTextEdit;
    private EditText versionTextEdit;
    private EditText websiteTextEdit;

    private boolean validation(EditText manudacturerTextEdit, EditText modelTextEdit, EditText versionTextEdit, EditText websiteTextEdit){
        boolean flag = true;
        if(TextUtils.isEmpty(manudacturerTextEdit.getText().toString())){
            manudacturerTextEdit.setError("Cant be empty!");
            flag = false;
        }
        if(TextUtils.isEmpty(modelTextEdit.getText().toString())){
            modelTextEdit.setError("Cant be empty!");
            flag = false;
        }
        if(TextUtils.isEmpty(versionTextEdit.getText().toString())){
            versionTextEdit.setError("Cant be empty!");
            flag = false;
        }
        if(TextUtils.isEmpty(websiteTextEdit.getText().toString())){
            websiteTextEdit.setError("Cant be empty!");
            flag = false;
        }

        return  flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        websiteButton = findViewById(R.id.webisteButton);
        cancelButton = findViewById(R.id.cancelButton);;
        saveButton = findViewById(R.id.saveButton);

        manudacturerTextEdit = findViewById(R.id.manudacturerTextEdit);
        modelTextEdit = findViewById(R.id.modelTextEdit);
        versionTextEdit = findViewById(R.id.versionTextEdit);
        websiteTextEdit = findViewById(R.id.websiteTextEdit);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            if(bundle.containsKey("phonetoEdit")){
                Phone phone = (Phone) bundle.get("phonetoEdit");
                manudacturerTextEdit.setText(phone.getProducent());
                modelTextEdit.setText(phone.getModel());
                versionTextEdit.setText(phone.getVersion());
                websiteTextEdit.setText(phone.getWebsite());


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(validation(manudacturerTextEdit, modelTextEdit, versionTextEdit, websiteTextEdit)){
                            phone.setModel(modelTextEdit.getText().toString());
                            phone.setProducent(manudacturerTextEdit.getText().toString());
                            phone.setVersion(versionTextEdit.getText().toString());
                            phone.setWebsite(websiteTextEdit.getText().toString());

                            Intent intent = new Intent();
                            intent.putExtra("editedPhone", phone);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });

            }
        }else {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validation(manudacturerTextEdit, modelTextEdit, versionTextEdit, websiteTextEdit)) {
                        Intent intent = new Intent();
                        intent.putExtra("phone_producent", manudacturerTextEdit.getText().toString());
                        intent.putExtra("phone_model", modelTextEdit.getText().toString());
                        intent.putExtra("phone_version", versionTextEdit.getText().toString());
                        intent.putExtra("phone_website", websiteTextEdit.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adres = websiteTextEdit.getText().toString();
                if(adres.startsWith("http://")) {
                    Intent zamiarPrzegladarki = new Intent("android.intent.action.VIEW", Uri.parse(adres));
                    startActivity(zamiarPrzegladarki);
                }else{
                    Toast.makeText(InsertActivity.this, "Zły adres strony", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}