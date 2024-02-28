package com.example.animanga_universe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.animanga_universe.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * La actividad Login, donde el usuario puede entrar en la aplicacion con sus datos, registrarse o cambiar la contraseña en el caso
 * de que se le haya olvidado
 * @author Daniel Seregin Kozlov
 */
public class Login extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout usuario, password;
    Button login,registro;
    DatabaseReference ref;
    TextView cambioContraseña;
    int contador;

    /**
     * El método onCreate sobreescrito, donde conecto todos los elementos necesarios del xml para poder darles funcionalidad en java
     * Y también me conecto a Firebase
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contador=0;
        usuario= findViewById(R.id.usuario);
        password= findViewById(R.id.password);
        login= findViewById(R.id.entrar);
        registro= findViewById(R.id.registrarse);
        cambioContraseña= findViewById(R.id.contraseñaOlvidada);
        ref= FirebaseDatabase.getInstance().getReference("Usuario");
        login.setOnClickListener(this);
        registro.setOnClickListener(this);
        cambioContraseña.setOnClickListener(this);

    }

    /**
     * Método onClick sobreescrito para los botones y el textview de la contraseña olvidada
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //En el caso de registro inicio una nueva actividad
        if(v.getId()==registro.getId()){
            Intent intent= new Intent(Login.this, Registro.class);
            startActivity(intent);

        }
        //En el caso del login compreubo que todos los campos estén rellenos, y si es así compruebo si existe el usuario en la base de datos, y si
        //la contraseña introducida es correcta
        else if(v.getId()==login.getId()) {
            if (usuario.getEditText().getText().toString().trim().equals("")) {
                Snackbar.make(v, getString(R.string.campoUsuarioVacio), Snackbar.LENGTH_SHORT).show();
                usuario.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else if (password.getEditText().getText().toString().trim().equals("")) {
                Snackbar.make(v, getString(R.string.campoContraseñaVacio), Snackbar.LENGTH_SHORT).show();
                password.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {
                            Usuario u = d.getValue(Usuario.class);
                            if(u.getUsername().equals(usuario.getEditText().getText().toString().trim())){
                                contador++;
                                if(u.getPassword().equals(password.getEditText().getText().toString().trim())){
                                    entrar(u);
                                }else{
                                    Snackbar.make(v, getString(R.string.contraseñaIncorrecta), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }if(contador==0){
                            Snackbar.make(v, getString(R.string.usuarioNoExiste), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        //En el caso de la contraseña olvidada inicio una nueva actividad
        else{

        }
    }
    public void entrar(Usuario u){
        Intent intent;
    }
}