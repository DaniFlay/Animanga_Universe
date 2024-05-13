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
import android.widget.ImageView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.models.User;
import com.example.animanga_universe.extras.Helper;
import com.example.animanga_universe.extras.PasswordEncryption;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


/**
 * Fragment para el cambio de contraseña
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

TextInputLayout passwordAntigua, passwordNueva, passwordNueva2;
Button guardar;
User user;
View view;
SQLiteDatabase db;
MainMenu mainMenu;
Helper helper;
ImageView back;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ChangePasswordFragment() {

    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_change_password, container, false);

        mainMenu = (MainMenu) getActivity();
        assert mainMenu != null;
        back= mainMenu.getBack();
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        user= Objects.requireNonNull(mainMenu).devolverUser();
        passwordAntigua= view.findViewById(R.id.contraseñaAntigua);
        passwordNueva= view.findViewById(R.id.contraseñaNueva);
        passwordNueva2= view.findViewById(R.id.contraseñaNueva2);
        guardar= view.findViewById(R.id.botonGuardar);
        try{
            helper= new Helper(getContext(), "bbdd", null,1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            //Se hace la comprobacion de si la contraseña antigua es correcta, si los campos están rellenos, etc
            if(!c.getString(0).equals(PasswordEncryption.hashPassword(Objects.requireNonNull(passwordAntigua.getEditText()).getText().toString()))){
                Snackbar.make(v,getString(R.string.contraseñaIncorrecta),Snackbar.LENGTH_SHORT).show();
                passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
            }else{
                if(passwordAntigua.getEditText().getText().toString().equals("")){
                    Snackbar.make(v,getString(R.string.contraseñaAvacio),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                } else if (Objects.requireNonNull(passwordNueva.getEditText()).getText().toString().equals("")) {
                    Snackbar.make(v,getString(R.string.contraseñaNvacio),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                } else if (Objects.requireNonNull(passwordNueva2.getEditText()).getText().toString().equals("")) {
                    Snackbar.make(v,getString(R.string.campoContraseña2Vacio),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));
                } else if (!passwordNueva.getEditText().getText().toString().equals(passwordNueva2.getEditText().getText().toString())) {
                    Snackbar.make(v,getString(R.string.camposContraseñaNoCoinciden),Snackbar.LENGTH_SHORT).show();
                    passwordAntigua.setBoxStrokeColorStateList(ColorStateList.valueOf(Color.RED));

                }else {
                    user.setPassword(PasswordEncryption.hashPassword(passwordNueva.getEditText().getText().toString()));
                    Snackbar.make(v, getString(R.string.cambiosGuardados), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.ok), v1 -> {
                                mainMenu.guardarUsuario(user);
                                mainMenu.actualizarPassword(user.getUsername(), user.getPassword());
                                mainMenu.reemplazarFragment(new ProfileFragment());
                            }).show();

                }

            }
            c.close();
        } else if (v.getId()== back.getId()) {
            back.setVisibility(View.GONE);
            mainMenu.reemplazarFragment(new ProfileFragment());
        }

    }
}