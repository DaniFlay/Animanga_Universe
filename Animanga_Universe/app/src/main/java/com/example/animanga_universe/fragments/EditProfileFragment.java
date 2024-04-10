package com.example.animanga_universe.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

/**
 * Frgament para la edición de perfil del usuario
 * @author Daniel Seregin Kozlov
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {
    String usuarioAntiguo;
    TextInputLayout usuario, correo, fecha, sexo;
    Button elegirFecha, guardar;
    View view;
    User user;
    MainMenu mainMenu;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public EditProfileFragment() {

    }
    /**
     * Se crea la instancia del fragment
     * @param param1 Parameter 1 creado automáticamente
     * @param param2 Parameter 2 creado automáticamente
     * @return Nueva instancia del Fragment
     */
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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

        view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        mainMenu = (MainMenu) getActivity();
        user= Objects.requireNonNull(mainMenu).devolverUser();
        usuario= view.findViewById(R.id.user);
        correo= view.findViewById(R.id.correo);
        fecha= view.findViewById(R.id.fechaNacimiento);
        sexo= view.findViewById(R.id.sexo);
        elegirFecha= view.findViewById(R.id.botonFecha);
        guardar= view.findViewById(R.id.botonGuardar);
        Objects.requireNonNull(usuario.getEditText()).setText(user.getUsername());
        usuarioAntiguo= usuario.getEditText().getText().toString();
        Objects.requireNonNull(correo.getEditText()).setText(user.getCorreo());
        Objects.requireNonNull(fecha.getEditText()).setText(user.getFecha_de_nacimiento());
        guardar.setOnClickListener(this);
        elegirFecha.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //Si se pulsa el botón de guardar se recogen los datos, y aparece un SnackBar indicando que se han guardado con éxito
        if(v.getId()==guardar.getId()){
            user.setUsername(Objects.requireNonNull(usuario.getEditText()).getText().toString());
            user.setCorreo(Objects.requireNonNull(correo.getEditText()).getText().toString());
            user.setFecha_de_nacimiento(Objects.requireNonNull(fecha.getEditText()).getText().toString());
            user.setSexo(Objects.requireNonNull(sexo.getEditText()).getText().toString());

            //En el caso de pulsar el boton que aparece en el SnackBar se guardan los datos en las de datos y se cambia de fragemnt
            Snackbar.make(v, getString(R.string.cambiosGuardados), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.ok), v1 -> {
                                mainMenu.guardarUsuarioNuevo(user,usuarioAntiguo);
                                mainMenu.actualizarUsuario(user.getUsername(),usuarioAntiguo);
                                mainMenu.reemplazarFragment(new ProfileFragment());
                            }).show();

        }else if(v.getId()== elegirFecha.getId()){
            //En el caso de que se pulse el boton de elegir fecha, se abre un DatePciker para elegir la fecha de nacimiento
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                String mes1;
                if(month<10){
                    mes1 = "0"+(month+1);
                }else{
                    mes1 = String.valueOf(month+1);
                }
                String fecha1= dayOfMonth+"/"+ mes1 +"/"+year;
                if(fecha.getEditText()!=null) {
                    fecha.getEditText().setText(fecha1);
                }
                else{
                    Snackbar.make(v,getString(R.string.errorRellenoFecha),Snackbar.LENGTH_SHORT).show();
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        }

    }
}