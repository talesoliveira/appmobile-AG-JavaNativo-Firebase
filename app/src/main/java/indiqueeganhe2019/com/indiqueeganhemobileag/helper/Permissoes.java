package indiqueeganhe2019.com.indiqueeganhemobileag.helper;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissoes {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode){

        if (Build.VERSION.SDK_INT >= 23 ){

            List<String> listaPermissoes = new ArrayList<>();

            /*Percorre as permissÃµes passadas,
            verificando uma a uma
            * se jÃ¡ tem a permissao liberada */
            for ( String permissao : permissoes ){
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if ( !temPermissao ) listaPermissoes.add(permissao);
            }

            /*Caso a lista esteja vazia, nÃ£o Ã© necessÃ¡rio solicitar permissÃ£o*/
            if ( listaPermissoes.isEmpty() ) return true;
            String[] novasPermissoes = new String[ listaPermissoes.size() ];
            listaPermissoes.toArray( novasPermissoes );

            //Solicita permissÃ£o
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode );


        }

        return true;

    }

}
