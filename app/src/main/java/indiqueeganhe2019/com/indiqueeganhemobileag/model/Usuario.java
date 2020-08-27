package indiqueeganhe2019.com.indiqueeganhemobileag.model;

import com.google.firebase.database.DatabaseReference;

import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;

public class Usuario {

    private String idUser;
    private String nomeCompleto;
    private String numeroUser;
    private String emailUser;
    private String senhaUser;


    public Usuario() {

    }

    public void salvarUsuario(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("usuarios").child(getIdUser()).setValue(this);
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNumeroUser() {
        return numeroUser;
    }

    public void setNumeroUser(String numeroUser) {
        this.numeroUser = numeroUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getSenhaUser() {
        return senhaUser;
    }

    public void setSenhaUser(String senhaUser) {
        this.senhaUser = senhaUser;
    }
}
