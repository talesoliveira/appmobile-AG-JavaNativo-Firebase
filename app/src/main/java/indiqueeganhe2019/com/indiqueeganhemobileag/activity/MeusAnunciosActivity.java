package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;
import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.adapter.AdapterAnuncios;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.RecyclerItemClickListener;
import indiqueeganhe2019.com.indiqueeganhemobileag.model.Anuncio;

public class MeusAnunciosActivity extends AppCompatActivity {
    private RecyclerView recyclerAnuncios;
    private List<Anuncio> anuncios = new ArrayList<>();
    private AdapterAnuncios adapterAnuncios;
    private DatabaseReference anuncioUsuarioRef;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);
        //configuracoes iniciais
        anuncioUsuarioRef= ConfiguracaoFirebase.getFirebase() //este passa  a chave primeiro está na classe la em cima
                .child("meus_anuncios")
                .child(ConfiguracaoFirebase.getIdUsuario());

        //opa
        inicializarComponentes();
        //sopa
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("meus clientes indicados");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastrarAnuncioActivity.class
                ));

            }
        });
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnuncios.setHasFixedSize(true);
        adapterAnuncios= new AdapterAnuncios(anuncios,this);
        recyclerAnuncios.setAdapter(adapterAnuncios);
        //recupera anuncios para usuario
        recuperarAnuncios();
        //adiciona evento de click no recyclerView
        recyclerAnuncios.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerAnuncios,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Anuncio anuncioSelecionado = anuncios.get(position);
                                anuncioSelecionado.remover();
                                adapterAnuncios.notifyDataSetChanged();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    private void recuperarAnuncios(){
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Aguarde Carregando")
                .setCancelable(false)
                .build();
        dialog.show();

        anuncioUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                anuncios.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    anuncios.add(ds.getValue(Anuncio.class));

                }
                Collections.reverse(anuncios);
                adapterAnuncios.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void inicializarComponentes (){

        recyclerAnuncios= findViewById(R.id.recyclerAnuncios);
    }
}
