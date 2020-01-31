package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModificarParada_datos extends AppCompatActivity {
    private Button btn_registrar;
    private EditText etxt_new_lat, etxt_new_long;
    private String str_new_lat, str_new_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_parada_datos);
        // inicializo los Views
        btn_registrar= (Button) findViewById(R.id.btn_modify_parada);
        etxt_new_lat= (EditText) findViewById( R.id.etxt_new_latitud);
        etxt_new_long= (EditText) findViewById(R.id.etxt_new_longitud);
        btn_registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                str_new_lat= btn_registrar.getText().toString();
                str_new_long= btn_registrar.getText().toString();
            }
        });

    }
}
