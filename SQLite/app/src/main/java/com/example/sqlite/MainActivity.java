package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText t1,t2;
    Button b1,b2,b3;
    SQLiteDatabase bbdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1= findViewById(R.id.text1);
        t2= findViewById(R.id.text2);
        b1= findViewById(R.id.boton);
        b2= findViewById(R.id.botonAct);
        b3= findViewById(R.id.botonCons);
        Helper bbddAlumnos= new Helper(this, "bbddalumno",null,1);
        bbdd=bbddAlumnos.getWritableDatabase();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre= String.valueOf(t1.getText());
                String ciclo= String.valueOf(t2.getText());
                ContentValues contenido= new ContentValues();
                contenido.put("nombre",nombre);
                contenido.put("ciclo",ciclo);
                bbdd.insert("alumno",null,contenido);
                
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bbdd.execSQL("update alumno set ciclo='DAM' where ciclo='dam'");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c= bbdd.rawQuery("select _id, nombre from alumno",null);
                String cadena="";

                if(c.moveToFirst()){
                    do{
                        int id= c.getInt(0);
                        String nombre= c.getString(1);
                        cadena += "id: "+id+"; Nombre: "+nombre;
                    }while(c.moveToNext());
                }
                c.close();

            }
        });
    }
}