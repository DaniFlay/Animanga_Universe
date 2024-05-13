package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animanga_universe.R;

import com.example.animanga_universe.models.User;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.extras.PasswordEncryption;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * La actividad Login, donde el usuario puede entrar en la aplicacion con sus datos, registrarse o cambiar la contraseña en el caso
 * de que se le haya olvidado
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class Login extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    TextInputLayout usuario, password;
    Button login, registro;
    DatabaseReference ref;
    TextView cambioContraseña;
    int contador;
    SQLiteDatabase db;
    Helper helper;
    BottomSheetDialog bottomSheetDialog;
    String usuarioDummy, passwordDummy;


    /**
     * El método onCreate sobreescrito, donde conecto todos los elementos necesarios del xml para poder darles funcionalidad en java
     * Y también me conecto a Firebase
     * @param savedInstanceState En el caso de que se reinicie la actividad, es el bundle que contiene la última instancia guardada
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contador = 0;
        usuario = findViewById(R.id.user);
        password = findViewById(R.id.password);
        login = findViewById(R.id.entrar);
        registro = findViewById(R.id.registrarse);
        cambioContraseña = findViewById(R.id.contraseñaOlvidada);
        ref = FirebaseDatabase.getInstance().getReference("Usuario");
        login.setOnClickListener(this);
        registro.setOnClickListener(this);
        cambioContraseña.setOnClickListener(this);
        Objects.requireNonNull(usuario.getEditText()).setOnFocusChangeListener(this);

    }

    /**
     * Método onClick sobreescrito para los botones y el textview de la contraseña olvidada
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //En el caso de registro inicio una nueva actividad
        if (v.getId() == registro.getId()) {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);

        }
        //En el caso del login compruebo que todos los campos estén rellenos, y si es así compruebo si existe el usuario en la base de datos, y si
        //la contraseña introducida es correcta
        else if (v.getId() == login.getId()) {
            if (Objects.requireNonNull(usuario.getEditText()).getText().toString().trim().equals("")) {
                Snackbar.make(v, getString(R.string.campoUsuarioVacio), Snackbar.LENGTH_SHORT).show();
                usuario.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else if (Objects.requireNonNull(password.getEditText()).getText().toString().trim().equals("")) {
                Snackbar.make(v, getString(R.string.campoContraseñaVacio), Snackbar.LENGTH_SHORT).show();
                password.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            } else {
                ref.addValueEventListener(new ValueEventListener() {
                    /**
                     * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
                     * @param snapshot Es la tabla a la que se accede
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {
                            User u = d.getValue(User.class);
                            if (Objects.requireNonNull(u).getUsername().equals(usuario.getEditText().getText().toString().trim())) {
                                contador++;
                                if (u.getPassword().equals(PasswordEncryption.hashPassword(password.getEditText().getText().toString().trim()))) {
                                    entrar(u);
                                } else {
                                    Snackbar.make(v, getString(R.string.contraseñaIncorrecta), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if (contador == 0) {
                            Snackbar.make(v, getString(R.string.usuarioNoExiste), Snackbar.LENGTH_SHORT).show();
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
        //En el caso de la contraseña olvidada inicio una nueva actividad
        else if (v.getId() == cambioContraseña.getId()) {
            Intent intent = new Intent(Login.this, ChangePasswordMail.class);
            startActivity(intent);
        }
    }

    /**
     * Esta función crea un intent y entra en la actividad del menú prinicpal mandando como extra el usuario
     * @param u el usuario que se manda como extra
     */
    public void entrar(User u) {

        Intent intent = new Intent(Login.this, MainMenu.class);
        intent.putExtra("usuario", (Parcelable) u);
        startActivity(intent);
    }

    /**
     * Esta función busca si existe el usuario en la base de datos, y en el caso de existir se hace el uso de la función entrar para entrar en la aplicación
     * @param nombre el nombre del usuario para buscar en la base de datos
     */
    public void buscarUsuario(String nombre) {
        ref = FirebaseDatabase.getInstance().getReference("Usuario");
        ref.addValueEventListener(new ValueEventListener() {
            /**
             * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
             * @param snapshot Es la tabla a la que se accede
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    User user = d.getValue(User.class);
                    if (nombre.equals(Objects.requireNonNull(user).getUsername())) {
                        entrar(user);
                        break;
                    }
                }
            }
            /**
             * Este método se llama si hay un error, ya sea por las reglas de Firebase o por no tener conexión a internet
             * @param error El error de firebase
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * La función para el escuchador del enfoque para el campo del usuario, se usa para que cuando se quite el enfoque del campo usuario, se
     * compruebe en la base de datos interna si existe el usuario introducido, en el caso de existir ofrece la función de entrar sin introducir la contraseña
     * ya que ya se encuentra guardada en la base de datos interna y así acelerar el proceso
     * @param v The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @SuppressLint("PrivateResource")
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            helper = new Helper(Login.this, "bbdd", null, 1);
            db = helper.getWritableDatabase();
            assert usuario.getEditText()!=null;
            String[] usuarioIntroducido = {usuario.getEditText().getText().toString().trim()};
            Cursor cursor = db.rawQuery("select * from usuario where usuario=?", usuarioIntroducido);
            if (cursor.moveToFirst()) {
                usuarioDummy = cursor.getString(0);
                passwordDummy = cursor.getString(1);
                bottomSheetDialog = new BottomSheetDialog(Login.this, com.google.android.material.R.style.Base_ThemeOverlay_Material3_BottomSheetDialog);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.modal_sheet, (LinearLayout) findViewById(R.id.modalsheet));
                bottomSheetView.findViewById(R.id.no).setOnClickListener(v12 -> {
                    bottomSheetDialog.dismiss();
                    cursor.close();
                });
                bottomSheetView.findViewById(R.id.si).setOnClickListener(v1 -> {
                    buscarUsuario(usuarioDummy);
                    cursor.close();
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        }
    }
}