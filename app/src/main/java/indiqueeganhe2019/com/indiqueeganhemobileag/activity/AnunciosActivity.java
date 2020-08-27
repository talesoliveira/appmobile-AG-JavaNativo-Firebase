package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

public class AnunciosActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private RecyclerView recyclerAnunciosPublicos;
    private AdapterAnuncios adapterAnuncios;
    private List<Anuncio> listaAnuncios = new ArrayList<>();
    private DatabaseReference anunciosPublicosRef;
    private AlertDialog dialog;
    private String filtroEstado= "";
    private String filtroCategoria= "";
    private Boolean filtranPorEstado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("acesso restrito");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicializarComponentes();
        //configuracoes iniciais
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios");

        recyclerAnunciosPublicos.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnunciosPublicos.setHasFixedSize(true);
        adapterAnuncios= new AdapterAnuncios(listaAnuncios,this);
        recyclerAnunciosPublicos.setAdapter(adapterAnuncios);

        recuperarAnunciosPublicos();
        //aplicar evento de click
        recyclerAnunciosPublicos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerAnunciosPublicos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Anuncio anuncioSelecionado = listaAnuncios.get(position);
                                Intent i = new Intent(AnunciosActivity.this, DetalhesProdutoActivity.class);
                                i.putExtra("anuncioSelecionado",anuncioSelecionado);
                                startActivity(i);
                                finish(); //aqui fiz pra recarregar a tela
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Anuncio anuncioSelecionado = listaAnuncios.get(position);
                               // anuncioSelecionado.removerAnuncioPublico();
                                adapterAnuncios.notifyDataSetChanged();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


    }

    public void filtrarPorEstado(View view){

        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("selecione o parceiro!");
        //configurar spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
        //Configura spinner de estados
        final Spinner spinnerEstado = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerEstado.setAdapter( adapter );

        dialogEstado.setView(viewSpinner);

        dialogEstado.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtroEstado= spinnerEstado.getSelectedItem().toString();

                recuperarAnunciosPorEstado();
                filtranPorEstado= true;


            }


        });



        dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog=dialogEstado.create();
        dialog.show();




    }

    public void filtrarPorCategoria(View view){
        if (filtranPorEstado== true){
            AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
            dialogEstado.setTitle("selecione a categoria!");
            //configurar spinner
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
            //Configura spinner de categorias
            final Spinner spinnerCategoria = viewSpinner.findViewById(R.id.spinnerFiltro);
            String[] estados = getResources().getStringArray(R.array.categorias);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item,
                    estados
            );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            spinnerCategoria.setAdapter( adapter );

            dialogEstado.setView(viewSpinner);

            dialogEstado.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filtroCategoria= spinnerCategoria.getSelectedItem().toString();

                    recuperarAnunciosPorCategoria();
                }


            });



            dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog=dialogEstado.create();
            dialog.show();

        }else{
            Toast.makeText(this, "Escolha primeiro o parceiro!", Toast.LENGTH_SHORT).show();
        }




    }

    public void recuperarAnunciosPorCategoria(){//recuperar por categoria

        //configura nó por estado

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios")
                .child(filtroEstado)
                .child(filtroCategoria);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAnuncios.clear();
                for (DataSnapshot anuncios: dataSnapshot.getChildren()){
                    Anuncio anuncio = anuncios.getValue(Anuncio.class);
                    listaAnuncios.add(anuncio);



                }
                Collections.reverse(listaAnuncios);
                adapterAnuncios.notifyDataSetChanged();


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void recuperarAnunciosPorEstado(){

        //configura  por estado

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios")
                .child(filtroEstado);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAnuncios.clear();
                for(DataSnapshot categorias:dataSnapshot.getChildren()){
                    for (DataSnapshot anuncios: categorias.getChildren()){
                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        listaAnuncios.add(anuncio);



                    }

                }
                Collections.reverse(listaAnuncios);
                adapterAnuncios.notifyDataSetChanged();


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void recuperarAnunciosPublicos(){
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Aguarde Carregando Novos Leads")
                .setCancelable(false)
                .build();
        dialog.show();
        listaAnuncios.clear();
        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot estados: dataSnapshot.getChildren()){

                    for(DataSnapshot categorias:estados.getChildren()){
                        for (DataSnapshot anuncios: categorias.getChildren()){
                            Anuncio anuncio = anuncios.getValue(Anuncio.class);
                            listaAnuncios.add(anuncio);

                        }

                    }

                }
                Collections.reverse(listaAnuncios);
                adapterAnuncios.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }







    public void inicializarComponentes (){

        recyclerAnunciosPublicos= findViewById(R.id.recyclerAnunciosPublicos);
    }
}
