package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import indiqueeganhe2019.com.indiqueeganhemobileag.PoliticaPrivacidadeActivity;
import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        switch (item.getItemId()){
            case R.id.menu_indicar:
                startActivity( new Intent(getApplicationContext(),CadastrarAnuncioActivity.class));
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                startActivity(new Intent(HomeActivity.this,LoginAppActivity.class));

                finish(); //neste era oque estava

                break;
            case R.id.menu_minhas_indicacoes:
                startActivity(new Intent (getApplicationContext(),MeusAnunciosActivity.class));

                break;

            case R.id.menu_adimin:
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));

                break;

            case R.id.menu_quem_somos:
                startActivity(new Intent(getApplicationContext(),QuemSomosActivity.class));

                break;



            case R.id.menu_contato:
                //Intent whatsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.whatsapp.com/send?1=pt_BR&phone=554888354664"));
               // startActivity(whatsIntent);
                startActivity(new Intent(getApplicationContext(),PoliticaPrivacidadeActivity.class));

                break;


        }

        return super.onOptionsItemSelected(item);
    }




    public void  abrirCadastroHome(View view){
        startActivity( new Intent(getApplicationContext(),CadastrarAnuncioActivity.class));
        finish();


    }
}
