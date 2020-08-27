package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;

public class HomeDaAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_da_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ADMIN");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }



    public void abrirClientesIndicados(View view){
        Intent intent = new Intent(HomeDaAdminActivity.this,AnunciosActivity.class);
        startActivity(intent);

    }


    public void abrirRelatorio(View view){
        Intent intent = new Intent(HomeDaAdminActivity.this,RelatorioActivity.class);
        startActivity(intent);
    }


}
