package indiqueeganhe2019.com.indiqueeganhemobileag.helper;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import indiqueeganhe2019.com.indiqueeganhemobileag.model.Usuario;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;

    public static String getIdUsuario(){
        FirebaseAuth autenticacao = getReferenciaAutenticacao();
        return autenticacao.getCurrentUser().getUid();
    }



    //retorna referencia
    public static DatabaseReference getFirebase() {
        if(referenciaFirebase==null){
            referenciaFirebase= FirebaseDatabase.getInstance().getReference();

        }else{

        }
        return referenciaFirebase;
    }

    //retorna firebase auth
    public static FirebaseAuth getReferenciaAutenticacao(){
        if(referenciaAutenticacao==null){
            referenciaAutenticacao= FirebaseAuth.getInstance();

        }

        return referenciaAutenticacao;

    }

    //retorna storage

    public static StorageReference getFirebaseStorage(){
        if (referenciaStorage==null){
            referenciaStorage= FirebaseStorage.getInstance().getReference();

        }
        return referenciaStorage;
    }
}

