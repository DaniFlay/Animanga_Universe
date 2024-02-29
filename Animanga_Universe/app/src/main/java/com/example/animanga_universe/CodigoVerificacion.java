package com.example.animanga_universe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.animanga_universe.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

/**
 * El usuario deberia haber recibido un correo con un codigo de verificacion, en esta actividad se introduce el codigo, y si coincide, se le
 * permite realizar el cambio de contraseña
 * @author Daniel Seregin Kozlov
 */
public class CodigoVerificacion extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout codigo;
    Button confirmar;
    String codigoVerificacion;
    Usuario user;

    /**
     * Crea la actividad
     * @param savedInstanceState En el caso de que se reinicie la actividad, después de haberse apagadom este Bundle contiene
     * la informacion mas reciente, en el caso contrario el valor es nulo
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_verificacion);
        codigo = findViewById(R.id.codigoVerificacion);
        confirmar= findViewById(R.id.botonConfirmar);
        codigoVerificacion= getIntent().getStringExtra("codigo");
        user= getIntent().getParcelableExtra("usuario");
        confirmar.setOnClickListener(this);
    }

    /**
     * Realiza las acciones necesarias al pulsar un boton
     * @param v La vista que ha sido pulsada
     */
    @Override
    public void onClick(View v) {
        if(codigo.getEditText()!=null) {
            if (codigo.getEditText().getText().toString().trim().equals(codigoVerificacion)){
                Intent intent= new Intent(CodigoVerificacion.this, CambioPassword.class);
                intent.putExtra("usuario",(Parcelable) user);
                startActivity(intent);
            }else{
                Snackbar.make(v,getString(R.string.codigoNoCoincide),Snackbar.LENGTH_SHORT).show();
            }
        }else{
            Snackbar.make(v,getString(R.string.errorRellenoDatos),Snackbar.LENGTH_SHORT).show();
        }
    }
}