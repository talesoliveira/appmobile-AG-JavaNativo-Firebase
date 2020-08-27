package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;

public class AdminActivity extends AppCompatActivity {
   FirebaseAuth firebaseRef= ConfiguracaoFirebase.getReferenciaAutenticacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("acesso restrito");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_entrar_admin = (Button) findViewById(R.id.btn_entrar_admin);
        btn_entrar_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    TextView texto_admin = (TextView) findViewById(R.id.texto_admin);
                TextView texto_senha = (TextView) findViewById(R.id.texto_senha);
                String adimin = texto_admin.getText().toString();
                String senha = texto_senha.getText().toString();
                if (adimin.equals("12345")&& senha.equals("12345")){
                    abrirHomeAdmin();

                }*/

            //novo sistema teste
                String chave1= firebaseRef.getUid();

                String chave2= "KworoHSpfgXgKXFokmDcwFbpN292";

                if ( chave1.equals(chave2)&&chave2.equals(chave1)){
                    abrirHomeAdmin();

                }else{
                    Toast.makeText(AdminActivity.this,"Você não está autorizado!",Toast.LENGTH_SHORT).show();

                    finish();
                }







            }
        });

    }




    private void abrirHomeAdmin(){
        Intent intent = new Intent(AdminActivity.this,HomeDaAdminActivity.class);
        startActivity(intent);
        finish();
    }







}
