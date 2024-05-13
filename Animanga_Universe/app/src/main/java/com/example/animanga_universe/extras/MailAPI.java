package com.example.animanga_universe.extras;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * API para poder mandar correos para el cambio de contraseña
 * @author Daniel Seregin Kozlov
 * @noinspection ALL
 */
public class MailAPI extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    final
    Context context;
    private final String recipient;
    private final String subject;
    private final String message;

    /**
     * constructor para el mailapi
     * @param context el contexto
     * @param recipient la persona que recibe el emsnaje
     * @param subject el tema del correoo
     * @param message el mensaje del correo
     */
    public MailAPI(Context context,  String recipient, String subject, String message) {
        this.context = context;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    /**
     * lo que se hace "por detras de las cortinas"
     * @param voids The parameters of the task
     * @return
     */
    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties= new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");

        //Session para el inicio de sesion
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Utils.email, Utils.password);
            }

        });
        //el mensaje a mandar
        MimeMessage mimeMessage= new MimeMessage(session);
        try{
            mimeMessage.setFrom(new InternetAddress(Utils.email));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(recipient)));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
