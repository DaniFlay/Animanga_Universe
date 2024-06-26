package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.animanga_universe.R;
import com.example.animanga_universe.models.User;
import com.example.animanga_universe.extras.MailAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Esta actividad le pide el correo al usuario con el que ha sido registrado para luego poder cambiar la contraseña
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class ChangePasswordMail extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout correo;
    Button verificar;
    DatabaseReference ref;
    String mail, codigo="", subject;
    int contador;
    Random rand;
    User u;

    /**
     * Este metodo se utilliza para crear la actividad
     * @param savedInstanceState En el caso de que se reinicie la actividad, después de haberse apagadom este Bundle contiene
     * la informacion mas reciente, en el caso contrario el valor es nulo
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_password_correo);
        rand= new Random();
        correo= findViewById(R.id.correo);
        verificar= findViewById(R.id.botonConfirmar);
        ref= FirebaseDatabase.getInstance().getReference("Usuario");
        verificar.setOnClickListener(this);
    }

    /**
     * El metodo on click realzia las funciones necesarias al pulsarse un boton
     * @param v La vista que ha sido pulsada
     */
    @Override
    public void onClick(View v) {
        contador=0;//El contador para comporbar que existe el correo en la base de datos
        if(correo.getEditText()!=null){
            if(correo.getEditText().getText().toString().trim().equals("")){
                Snackbar.make(v,getString(R.string.campoCorreoVacio),Snackbar.LENGTH_SHORT).show();
            }
            else {
                mail = correo.getEditText().getText().toString().trim();
                ref.addValueEventListener(new ValueEventListener() {
                    //Se comprueba si existe el usuario

                    /**
                     * Se llama a este método para recorrer la base de datos en Firebase, para poder realizar los cambios
                     * @param snapshot Es la tabla a la que se accede
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d: snapshot.getChildren()){
                            u= d.getValue(User.class);
                            if(u!=null){
                                if(u.getCorreo().equals(mail)){
                                    contador++;
                                    break;
                                }
                            }else {
                                Snackbar.make(v,getString(R.string.bbddvacia),Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        //Si existe, se crea el código de verificación, y se envía por correo
                            if(contador>0){
                                for(int i=0; i<6; i++){
                                    codigo+=rand.nextInt(10);
                                }
                                subject= getString(R.string.codigoVerificacion);
                                MailAPI api= new MailAPI(ChangePasswordMail.this,mail,subject,codigo);
                                api.execute();
                                Intent intent = new Intent(ChangePasswordMail.this, VerificationCode.class);
                                intent.putExtra("usuario",(Parcelable)u);
                                intent.putExtra("codigo",codigo);
                                startActivity(intent);
                            //En caso ccontrario se avisa al usuario de ello
                            }else{
                                Snackbar.make(v,getString(R.string.usuarioNoExiste),Snackbar.LENGTH_SHORT).show();
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
        }else{
            Snackbar.make(v,getString(R.string.errorRellenoDatos),Snackbar.LENGTH_SHORT).show();
        }


    }
}