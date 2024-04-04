package com.example.animanga_universe.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MenuPrincipal;
import com.example.animanga_universe.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarPerfilFragment extends Fragment implements View.OnClickListener {
    String usuarioAntiguo;
    TextInputLayout usuario, correo, fecha, sexo;
    Button elegirFecha, guardar;
    View view;
    Usuario user;
    MenuPrincipal menuPrincipal;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public EditarPerfilFragment() {

    }

    public static EditarPerfilFragment newInstance(String param1, String param2) {
        EditarPerfilFragment fragment = new EditarPerfilFragment();
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

        view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        menuPrincipal= (MenuPrincipal) getActivity();
        user=menuPrincipal.devolverUser();
        usuario= view.findViewById(R.id.usuario);
        correo= view.findViewById(R.id.correo);
        fecha= view.findViewById(R.id.fechaNacimiento);
        sexo= view.findViewById(R.id.sexo);
        elegirFecha= view.findViewById(R.id.botonFecha);
        guardar= view.findViewById(R.id.botonGuardar);
        usuario.getEditText().setText(user.getUsername());
        usuarioAntiguo= usuario.getEditText().getText().toString();
        correo.getEditText().setText(user.getCorreo());
        fecha.getEditText().setText(user.getFecha_de_nacimiento());
        guardar.setOnClickListener(this);
        elegirFecha.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==guardar.getId()){
            user.setUsername(usuario.getEditText().getText().toString());
            user.setCorreo(correo.getEditText().getText().toString());
            user.setFecha_de_nacimiento(fecha.getEditText().getText().toString());
            user.setSexo(sexo.getEditText().getText().toString());

            Snackbar.make(v, getString(R.string.cambiosGuardados), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    menuPrincipal.guardarUsuarioNuevo(user,usuarioAntiguo);
                                    menuPrincipal.actualizarUsuario(user.getUsername(),usuarioAntiguo);
                                    menuPrincipal.reemplazarFragment(new PerfilFragment());
                                }
                            }).show();

        }else if(v.getId()== elegirFecha.getId()){
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

    }
}