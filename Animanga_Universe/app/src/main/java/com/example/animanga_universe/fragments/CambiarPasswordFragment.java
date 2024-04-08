package com.example.animanga_universe.fragments;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MenuPrincipal;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.extras.PasswordEncryption;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


public class CambiarPasswordFragment extends Fragment implements View.OnClickListener {

TextInputLayout passwordAntigua, passwordNueva, passwordNueva2;
Button guardar;
Usuario user;
View view;
SQLiteDatabase db;
MenuPrincipal menuPrincipal;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public CambiarPasswordFragment() {

    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static CambiarPasswordFragment newInstance(String param1, String param2) {
        CambiarPasswordFragment fragment = new CambiarPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_cambiar_password, container, false);

        menuPrincipal = (MenuPrincipal) getActivity();
        user= menuPrincipal.devolverUser();
        passwordAntigua= view.findViewById(R.id.contraseñaAntigua);
        passwordNueva= view.findViewById(R.id.contraseñaNueva);
        passwordNueva2= view.findViewById(R.id.contraseñaNueva2);
        guardar= view.findViewById(R.id.botonGuardar);
        Helper helper= new Helper(getContext(), "bbdd", null,1);
        db= helper.getReadableDatabase();
        guardar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== guardar.getId()){
            String[] usuario= {user.getUsername()};
            Cursor c = db.rawQuery("select password from usuario where usuario =?",usuario);
            c.moveToFirst();
            if(!c.getString(0).equals(PasswordEncryption.hashPassword(passwordAntigua.getEditText().getText().toString()))){
                Snackbar.make(v,getString(R.string.contraseñaIncorrecta),Snackbar.LENGTH_SHORT).show();
                passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
            }else{
                if(passwordAntigua.getEditText().getText().toString().equals("")){
                    Snackbar.make(v,getString(R.string.contraseñaAvacio),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                } else if (passwordNueva.getEditText().getText().toString().equals("")) {
                    Snackbar.make(v,getString(R.string.contraseñaNvacio),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                } else if (passwordNueva2.getEditText().getText().toString().equals("")) {
                    Snackbar.make(v,getString(R.string.campoContraseña2Vacio),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                } else if (!passwordNueva.getEditText().getText().toString().equals(passwordNueva2.getEditText().getText().toString())) {
                    Snackbar.make(v,getString(R.string.camposContraseñaNoCoinciden),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));

                }else {
                    user.setPassword(PasswordEncryption.hashPassword(passwordNueva.getEditText().getText().toString()));
                    Snackbar.make(v, getString(R.string.cambiosGuardados), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    menuPrincipal.guardarUsuario(user);
                                    menuPrincipal.actualizarPassword(user.getUsername(), user.getPassword());
                                    menuPrincipal.reemplazarFragment(new PerfilFragment());
                                }
                            }).show();

                }

            }
        }
    }
}