package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.DateCustom;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.Permissoes;
import indiqueeganhe2019.com.indiqueeganhemobileag.model.Anuncio;
import indiqueeganhe2019.com.indiqueeganhemobileag.model.Usuario;

public class CadastrarAnuncioActivity extends AppCompatActivity
         {

    private EditText campoTitulo, campoDescricao;
    private ImageView imagem1, imagem2, imagem3;
    private Spinner campoEstado, campoCategoria;
    private EditText campoValor;
    private MaskEditText campoTelefone;// tel 2
    private MaskEditText campoTelefoneCliente;//este  vai ser tel 1
    private Anuncio anuncio;
    private AlertDialog dialog;
    private EditText campoResidencia;
    private EditText campoComplemento;
    private EditText campoOperadora;
    private String nomeRecuperado;
    private String emailRecuperado;
    private String numeroRecuperado;



    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncio);


        //Configuracoes iniciais
        recuperarDadosUsuario();


        //Validar permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();
        carregarDadosSpinner();

    }

    public void salvarAnuncio(){ //este é chamado apos  a validacao

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Salvando sua Indicação")
                .setCancelable(false)
                .build();
        dialog.show();

        anuncio.salvar();
        dialog.dismiss();
        finish();


    }



    private Anuncio configurarAnuncio(){

        String estado = campoEstado.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String valor = campoValor.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String telefone2= campoTelefoneCliente.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String residencia= campoResidencia.getText().toString();
        String complemento= campoComplemento.getText().toString();
        String operadora= campoOperadora.getText().toString();
        String datahora= DateCustom.dataAtual();

        Anuncio anuncio = new Anuncio();
        anuncio.setEstado( estado );
        anuncio.setCategoria(categoria);
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);
        anuncio.setTelefone( telefone );
        anuncio.setTelefoneCliente(telefone2);
        anuncio.setDescricao(descricao);
        anuncio.setnResidencia(residencia);
        anuncio.setComplemento(complemento);
        anuncio.setOperadoraAtual(operadora);
        anuncio.setData(datahora);
        anuncio.setRetorno1("sem status");
        anuncio.setRetorno2("sem status");
        anuncio.setRetorno3("sem status");
        anuncio.setValorComissao("indefinido");
        anuncio.setDataPagamento("sem data");
        anuncio.setStatusComissao("indefinido");
        anuncio.setNomeUsuario(nomeRecuperado);
        anuncio.setEmailUsuario(emailRecuperado);
        anuncio.setNumeroUsuario(numeroRecuperado);

        return anuncio;

    }


    public void validarDadosAnuncio(View view){

        anuncio = configurarAnuncio();
        String valor = String.valueOf(campoValor.getText());

            if( !anuncio.getEstado().isEmpty() ){
                if( !anuncio.getCategoria().isEmpty() ){
                    if( !anuncio.getTitulo().isEmpty() ){
                        if( !anuncio.getValor().isEmpty() ){
                            if( !anuncio.getTelefone().isEmpty()  ){
                                if( !anuncio.getDescricao().isEmpty() ){

                                    if(!anuncio.getTelefoneCliente().isEmpty()){

                                        if(!anuncio.getnResidencia().isEmpty()){

                                            if(!anuncio.getComplemento().isEmpty()){

                                                if(!anuncio.getOperadoraAtual().isEmpty()){

                                                    salvarAnuncio();

                                                }else{
                                                   exibirMensagemErro("Preencha a operadora do cliente");
                                                }

                                            }else {
                                                exibirMensagemErro("Preencha o campo complemento");
                                            }

                                        }else{
                                           exibirMensagemErro("Preencha o numero de residência");
                                        }


                                    }else{
                                        exibirMensagemErro("Preencha o campo telefone do cliente");
                                    }

                                }else {
                                    exibirMensagemErro("Preencha o campo descrição");
                                }
                            }else {
                                exibirMensagemErro("Preencha o campo telefone");
                            }
                        }else {
                            exibirMensagemErro("Preencha o campo Email do Cliente");
                        }
                    }else {
                        exibirMensagemErro("Preencha o campo nome do cliente");
                    }
                }else {
                    exibirMensagemErro("Preencha o campo categoria");
                }
            }else {
                exibirMensagemErro("Preencha o campo estado");
            }


    }







    private void exibirMensagemErro(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void carregarDadosSpinner(){

        /*String[] estados = new String[]{
          "SP", "MT"
        };*/

        //Configura spinner de estados
        String[] estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        campoEstado.setAdapter( adapter );

        //Configura spinner de categorias
        String[] categorias = getResources().getStringArray(R.array.categorias);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                categorias
        );
        adapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        campoCategoria.setAdapter( adapterCategoria );


    }

    private void inicializarComponentes(){
        campoEstado = findViewById(R.id.spinnerEstado);
        campoCategoria = findViewById(R.id.spinnerCategoria);
        campoTitulo = findViewById(R.id.editTitulo);//nome
        campoValor = findViewById(R.id.editValor);//sobrenome
        campoDescricao = findViewById(R.id.editDescricao);//este agora é cep:
        campoTelefoneCliente= findViewById(R.id.editTelefoneCliente); // tel 1
        campoTelefone = findViewById(R.id.editTelefone);//tel 2
        campoResidencia = findViewById(R.id.editResidencia);
        campoComplemento= findViewById(R.id.editComplemento);
        campoOperadora= findViewById(R.id.editOperadora);
        //Configura localidade para pt -> portugues BR -> Brasil
        Locale locale = new Locale("pt", "BR");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int permissaoResultado : grantResults ){
            if( permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public void  sairCadastro(View view){
        startActivity(new Intent(CadastrarAnuncioActivity.this,HomeActivity.class));
        finish();

    }


    public void recuperarDadosUsuario (){


        String idUsuario= ConfiguracaoFirebase.getIdUsuario();

        DatabaseReference usuarioRef = ConfiguracaoFirebase.getFirebase()
                .child("usuarios")
                .child(idUsuario);
            usuarioRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    nomeRecuperado = usuario.getNomeCompleto();
                    emailRecuperado= usuario.getEmailUser();
                    numeroRecuperado= usuario.getNumeroUser();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






    }






}





