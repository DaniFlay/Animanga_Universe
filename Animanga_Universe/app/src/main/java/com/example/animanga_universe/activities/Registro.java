package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.animanga_universe.R;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.extras.PasswordEncryption;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.util.Calendar;

/**
 * Esta es la actividad de registro, donde el usuario rellenará los campos con los datos, y creará su cuenta
 * @author Daniel Seregin Kozlov
 */
public class Registro extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout usuario, correo, contraseña,contraseña2, fecha, sexo;
    Button elegirFecha, guardar;
    DatabaseReference ref;
    String usuario2,correo2,sexo2,fechaNacimiento,password,password2, hashedPassword ;
    boolean existe= false;
    SQLiteDatabase bbdd;


    /**
     * El metodo on create crea la actividad, y conecto todos los elementos del xml para darles funcionalidad
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ref= FirebaseDatabase.getInstance().getReference("Usuario");
        usuario= findViewById(R.id.usuario);
        correo= findViewById(R.id.correo);
        contraseña= findViewById(R.id.password);
        contraseña2= findViewById(R.id.password2);
        fecha= findViewById(R.id.fechaNacimiento);
        sexo= findViewById(R.id.sexo);
        elegirFecha= findViewById(R.id.botonFecha);
        guardar= findViewById(R.id.botonGuardar);
        elegirFecha.setOnClickListener(this);
        guardar.setOnClickListener(this);

    }

    /**
     * El método on click que dependiendo del boton pulsado realiza una acción u otra
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //En el caso de que se pulse el boton de fecha aparece un Dialog donde el usuario podrá elegir la
        //fecha de nacimiento, y con esa fecha se rellena el campo de fecha de nacimiento
        if(v.getId()==elegirFecha.getId()){
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String mes="";
                    if(month<10){
                        mes= "0"+(month+1);
                    }else{
                        mes= month+1+"";
                    }
                    String fecha1= dayOfMonth+"/"+mes+"/"+year;
                    if(fecha.getEditText()!=null) {
                        fecha.getEditText().setText(fecha1);
                    }
                    else{
                        Snackbar.make(v,getString(R.string.errorRellenoFecha),Snackbar.LENGTH_SHORT).show();
                    }
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        }
        //En el caso de que se pulse el boton guardar, se comprueba que ningun campo ha quedado vacío, y guarda los datos
        //en la base de datos y abre de nuevo la actividad del login
        //En el caso de que hubiera algún campo vacío, el campo aparecerá en rojo y se informa al usuario con un snackbar
        else{
            if(usuario.getEditText()!=null&&correo.getEditText()!=null&&sexo.getEditText()!=null&&fecha.getEditText()!=null&&contraseña.getEditText()!=null&&contraseña2.getEditText()!=null) {
                usuario2 = usuario.getEditText().getText().toString().trim();
                correo2 = correo.getEditText().getText().toString().trim();
                sexo2 = sexo.getEditText().getText().toString().trim();
                fechaNacimiento = fecha.getEditText().getText().toString().trim();
                password = contraseña.getEditText().getText().toString().trim();
                password2 = contraseña2.getEditText().getText().toString().trim();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d: snapshot.getChildren()){
                            Usuario u= d.getValue(Usuario.class);
                            if(u!=null){
                                if(u.getUsername().equals(usuario2)){
                                    existe= true;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                Snackbar.make(v,getString(R.string.errorRellenoDatos),Snackbar.LENGTH_SHORT).show();
            }
            if(usuario2.equals("")){
                Snackbar.make(v,getString(R.string.campoUsuarioVacio),Snackbar.LENGTH_SHORT).show();
                usuario.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(usuario);
            } else if (correo2.equals("")) {
                Snackbar.make(v,getString(R.string.campoCorreoVacio),Snackbar.LENGTH_SHORT).show();
                correo.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(correo);
            } else if (sexo2.equals("")) {
                Snackbar.make(v,getString(R.string.camporSexoVacio),Snackbar.LENGTH_SHORT).show();
                sexo.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(sexo);
            } else if (fechaNacimiento.equals("")) {
                Snackbar.make(v,getString(R.string.campoFechaNacimientoVacio),Snackbar.LENGTH_SHORT).show();
                fecha.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(fecha);
            } else if (password.equals("")) {
                Snackbar.make(v,getString(R.string.campoContraseñaVacio),Snackbar.LENGTH_SHORT).show();
                contraseña.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(contraseña);
            } else if (password2.equals("")) {
                Snackbar.make(v,getString(R.string.campoContraseña2Vacio),Snackbar.LENGTH_SHORT).show();
                contraseña2.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(contraseña2);
            } else if (!password.equals(password2)) {
                Snackbar.make(v,getString(R.string.camposContraseñaNoCoinciden),Snackbar.LENGTH_SHORT).show();
                contraseña.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                requestFocus(contraseña);
                contraseña.getEditText().setText("");
                contraseña2.getEditText().setText("");
            }else if (existe) {
                Snackbar.make(v,getString(R.string.usuarioExiste),Snackbar.LENGTH_SHORT).show();
                requestFocus(usuario);
            }else{
                hashedPassword= PasswordEncryption.hashPassword(password);
                Usuario user= new Usuario(usuario2,hashedPassword,sexo2,correo2,fechaNacimiento);
                ref.push().setValue(user);
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.guardarpassword))
                        .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Helper helper= new Helper(Registro.this, "bbdd",null,1);
                                bbdd= helper.getWritableDatabase();
                                ContentValues cv= new ContentValues();
                                cv.put("usuario", usuario2);
                                cv.put("password",hashedPassword);
                                bbdd.insert("usuario",null, cv);
                                Intent intent= new Intent(Registro.this, Login.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent= new Intent(Registro.this, Login.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();

            }
        }
    }

    /**
     * Selecciona la vista que recibe
     * @param view la vista recibida
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}