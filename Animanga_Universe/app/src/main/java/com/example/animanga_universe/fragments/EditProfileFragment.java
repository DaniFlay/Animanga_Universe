package com.example.animanga_universe.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.animanga_universe.R;
import com.example.animanga_universe.activities.MainMenu;
import com.example.animanga_universe.classes.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

/**
 * Fragment para la edición de perfil del usuario
 * @author Daniel Seregin Kozlov
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {
    String usuarioAntiguo;
    TextInputLayout usuario, correo, fecha, sexo;
    Button elegirFecha, guardar;
    View view;
    User user;
    MainMenu mainMenu;
    ImageView back;


    public EditProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mainMenu = (MainMenu) getActivity();
        user= Objects.requireNonNull(mainMenu).devolverUser();
        back= mainMenu.getBack();
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
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
        } else if (v.getId() == back.getId()){
            //Si se pulsa el botón atrás se vuelve al fragment de Perfil
            mainMenu.reemplazarFragment(new ProfileFragment());
            back.setVisibility(View.GONE);
        }

    }
}