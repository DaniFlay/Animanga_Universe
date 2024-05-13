package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.animanga_universe.R;
import com.example.animanga_universe.models.User;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.extras.PasswordEncryption;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * En esta actividad se realiza el cambio de contraseña
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout password, password2;
    Button guardar;
    DatabaseReference ref;
    User user;
    String contraseña, contraseña2;
    SQLiteDatabase db;

    /**
     * En el método on create se conectan los elementos del xml para darlres funcionalidad
     * @param savedInstanceState En el caso de que se reinicie la actividad, después de haberse apagadom este Bundle contiene
     * la informacion mas reciente, en el caso contrario el valor es nulo
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password= findViewById(R.id.password);
        password2= findViewById(R.id.password2);
        guardar= findViewById(R.id.botonGuardar);
        ref= FirebaseDatabase.getInstance().getReference("Usuario");
        user= getIntent().getParcelableExtra("usuario");
        guardar.setOnClickListener(this);
    }

    /**
     * El metodo on click es para que se realicen ciertas acciones al pulsar un boton
     * Se comprueba que ambos campos coinciden, y si no están vacíos, en el caso contrario se avisa al usuario
     * @param v La vista que ha sido pulsada
     */
    @Override
    public void onClick(View v) {
        if(password.getEditText()!=null&&password2.getEditText()!=null){
            contraseña= password.getEditText().getText().toString().trim();
            contraseña2 = password2.getEditText().getText().toString().trim();
            //Se hace la comprobación de los campos, si están vacios, si no coinciden, etc.
            if(!contraseña.equals(contraseña2.trim())){
                Snackbar.make(v,getString(R.string.camposContraseñaNoCoinciden),Snackbar.LENGTH_SHORT).show();

            } else if (contraseña.equals("")) {
                Snackbar.make(v,getString(R.string.campoContraseñaVacio),Snackbar.LENGTH_SHORT).show();
                password.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(password);

            } else if (contraseña2.equals("")) {
                Snackbar.make(v,getString(R.string.campoContraseña2Vacio),Snackbar.LENGTH_SHORT).show();
                password2.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(password2);
            //Em el caso de que los campos coinicdan y no estén vacíos, se procede al cambio de la contraseña
            } else{

                ref.addValueEventListener(new ValueEventListener() {
                    /**
                     * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
                     * @param snapshot Es la tabla a la que se accede
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d: snapshot.getChildren()){
                            User user = d.getValue(User.class);
                            if(user !=null){
                                //Se comprueba todos los usuarios de la base de datos con el usuario de la aplicación
                                if(user.equals(ChangePassword.this.user)){
                                    //En el caso de que coincida, se encripta la contraseña, y se actualiza en la base de datos de Firebase y en la base de datos interna SQLite
                                    Helper helper= new Helper(ChangePassword.this,"bbdd",null,1);
                                    db= helper.getWritableDatabase();
                                    PasswordEncryption passwordEncryption= new PasswordEncryption();
                                    String encryptedPassword= passwordEncryption.hashPassword(password.getEditText().getText().toString());
                                    ChangePassword.this.user.setPassword(encryptedPassword);
                                    db.execSQL("update usuario set password= '"+encryptedPassword+"'where usuario ='"+user.getUsername()+"'");
                                    d.getRef().setValue(ChangePassword.this.user);
                                    Snackbar.make(v,getString(R.string.contraseñaCambiada),Snackbar.LENGTH_SHORT)
                                            .setAction(getString(R.string.aceptar), v1 -> {
                                                Intent intent= new Intent(ChangePassword.this, Login.class);
                                                startActivity(intent);
                                            })
                                            .show();

                                }
                            }else{
                                Snackbar.make(v,getString(R.string.bbddvacia),Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }

                    /**
                     * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
                     * @param error El error de firebase
                     */
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Snackbar.make(v, getString(R.string.errorServidor), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    /**
     * Este metodo selecciona un campo, se utiliza para indicar que campo está vacío ya que también el campo aparecerá en rojo
     * @param view la vista que se va a seleccionar
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}