package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.maskedittext.MaskEditText;

import org.intellij.lang.annotations.PrintFormat;

import java.text.SimpleDateFormat;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.dialog.SingleChoiceDialogFragment;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.DateCustom;
import indiqueeganhe2019.com.indiqueeganhemobileag.model.Anuncio;

public class DetalhesRelatorioActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener {
    private FirebaseAuth autenticacao= ConfiguracaoFirebase.getReferenciaAutenticacao();
    private TextView nomeCompleto;
    private TextView cpfOuCnpj;
    private TextView fone1;
    private TextView fone2;
    private TextView cep;
    private TextView numeroResidencia;
    private TextView complemento;
    private TextView operadora;
    private TextView parceiro;
    private TextView categoria;
    private TextView dataAtualizada;
    private Anuncio anuncioSelecionado;
    private TextView retorno1;
    private TextView retorno2;
    private TextView retorno3;
    private TextView nomeUser;
    private TextView emailUser;
    private TextView numeroUser;
    private TextView valorComissao;
    private TextView statusComissao;
    private TextView dataComissao;
    private MaskEditText formataDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_relatorio);
        inicializarComponentes();
        //recupera para exibir
        anuncioSelecionado =(Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");
        if (anuncioSelecionado !=null){
            nomeCompleto.setText(anuncioSelecionado.getTitulo());
            cpfOuCnpj.setText(anuncioSelecionado.getValor());
            fone1.setText(anuncioSelecionado.getTelefoneCliente());
            fone2.setText(anuncioSelecionado.getTelefone());
            cep.setText(anuncioSelecionado.getDescricao());
            numeroResidencia.setText(anuncioSelecionado.getnResidencia());
            complemento.setText(anuncioSelecionado.getComplemento());
            operadora.setText(anuncioSelecionado.getOperadoraAtual());
            parceiro.setText(anuncioSelecionado.getEstado());
            categoria.setText(anuncioSelecionado.getCategoria());
            dataAtualizada.setText(anuncioSelecionado.getData());
            retorno1.setText(anuncioSelecionado.getRetorno1());
            retorno2.setText(anuncioSelecionado.getRetorno2());
            retorno3.setText(anuncioSelecionado.getRetorno3());



            nomeUser.setText(anuncioSelecionado.getNomeUsuario());
            emailUser.setText(anuncioSelecionado.getEmailUsuario());
            numeroUser.setText(anuncioSelecionado.getNumeroUsuario());


            valorComissao.setText(anuncioSelecionado.getValorComissao());
            statusComissao.setText(anuncioSelecionado.getStatusComissao());
            dataComissao.setText(anuncioSelecionado.getDataPagamento());


        }


    }

    public void atualizarRetorno1(View view){

        DialogFragment singleChoiceDialog= new SingleChoiceDialogFragment();
        singleChoiceDialog.setCancelable(false);
        singleChoiceDialog.show(getSupportFragmentManager(),"single choice dialog");


        //anuncioSelecionado.updateRetorno();
    }

    private void inicializarComponentes(){
        nomeCompleto = findViewById(R.id.textNomeCompleto);
        cpfOuCnpj= findViewById(R.id.textCpfCnpj);
        fone1=findViewById(R.id.textFone1);
        fone2=findViewById(R.id.textFone2);
        cep=findViewById(R.id.textCep);
        numeroResidencia=findViewById(R.id.textResidencia);
        complemento= findViewById(R.id.textComplemento);
        operadora= findViewById(R.id.textOperadora);
        parceiro= findViewById(R.id.textParceiro);
        categoria=findViewById(R.id.textCategoria);
        retorno1=findViewById(R.id.textRetorno1);
        retorno2=findViewById(R.id.textViewStatusAnterior);
        retorno3=findViewById(R.id.textViewData2);
        dataAtualizada= findViewById(R.id.textViewIdDataAtualizada);

        nomeUser= findViewById(R.id.textViewNomeParceiro);
        emailUser=findViewById(R.id.textViewEmailParceiro);
        numeroUser = findViewById(R.id.textViewNumeroParceiro);

        valorComissao= findViewById(R.id.textValorComissao);
        statusComissao= findViewById(R.id.textStatusComissao);
        dataComissao= findViewById(R.id.textDataComissao);


    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        salvarDataAnterior();
        salvarStatus();

        //se eu tivesse uma variavel aqui .setText(list[positioon])  é dois  oo mesmo
        String texto=(list [position]);


            String idUsuario = ConfiguracaoFirebase.getIdUsuario();
            DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                    .child("relatorio")
                    .child(idUsuario)
                    .child(anuncioSelecionado.getEstado())
                    .child(anuncioSelecionado.getCategoria())
                    .child(anuncioSelecionado.getIdAnuncio())
                    .child("retorno1");

            anuncioRef.setValue(texto);

            atualizarData();
            finish();






    }

    @Override
    public void onNevativeButtonClicked() {

    }


    public void atualizarData(){


        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorio")
                .child(idUsuario)
                .child(anuncioSelecionado.getEstado())
                .child(anuncioSelecionado.getCategoria())
                .child(anuncioSelecionado.getIdAnuncio())
                .child("data");

        anuncioRef.setValue(DateCustom.dataAtual());



    }

    public void salvarStatus(){
        String statusAnterior= anuncioSelecionado.getRetorno1();

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorio")
                .child(idUsuario)
                .child(anuncioSelecionado.getEstado())
                .child(anuncioSelecionado.getCategoria())
                .child(anuncioSelecionado.getIdAnuncio())
                .child("retorno2");


        anuncioRef.setValue(statusAnterior);
    }

    public void salvarDataAnterior(){
        String dataAnterior=anuncioSelecionado.getData();

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorio")
                .child(idUsuario)
                .child(anuncioSelecionado.getEstado())
                .child(anuncioSelecionado.getCategoria())
                .child(anuncioSelecionado.getIdAnuncio())
                .child("retorno3");


        anuncioRef.setValue(dataAnterior);

    }


    public  void fecharRelatorio (View view){
        finish();
    } //este  é  o botao acho q vou remover pra fazer pra fazer ultima parte



    //aqui vao as dialog

    public void abrirDialog1(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalhesRelatorioActivity.this);

        alertDialog.setTitle("Valor");
        alertDialog.setMessage("insira o valor");
        alertDialog.setCancelable(false);
        final EditText editTextValor= new EditText(DetalhesRelatorioActivity.this);


        editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);

        alertDialog.setView(editTextValor);


        alertDialog.setPositiveButton("salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String valorDigitado = editTextValor.getText().toString();

                if(valorDigitado.isEmpty()){
                    Toast.makeText(DetalhesRelatorioActivity.this,"Preencha o campo para salvar",Toast.LENGTH_LONG).show();

                }else{

                    String idUsuario = ConfiguracaoFirebase.getIdUsuario();
                    DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                            .child("relatorio")
                            .child(idUsuario)
                            .child(anuncioSelecionado.getEstado())
                            .child(anuncioSelecionado.getCategoria())
                            .child(anuncioSelecionado.getIdAnuncio())
                            .child("valorComissao");
                    anuncioRef.setValue(valorDigitado);

                    finish();




                }


            }
        });

        alertDialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }




    //dialog 2

    public void abrirDialog2(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalhesRelatorioActivity.this);

        alertDialog.setTitle("Pagamento");
        alertDialog.setMessage("Status do pagamento");
        alertDialog.setCancelable(false);
        final EditText editTextValor= new EditText(DetalhesRelatorioActivity.this);


        editTextValor.setInputType(InputType.TYPE_CLASS_TEXT);

        alertDialog.setView(editTextValor);


        alertDialog.setPositiveButton("salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String valorDigitado = editTextValor.getText().toString();

                if(valorDigitado.isEmpty()){
                    Toast.makeText(DetalhesRelatorioActivity.this,"Preencha o campo para salvar",Toast.LENGTH_LONG).show();

                }else{

                    String idUsuario = ConfiguracaoFirebase.getIdUsuario();
                    DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                            .child("relatorio")
                            .child(idUsuario)
                            .child(anuncioSelecionado.getEstado())
                            .child(anuncioSelecionado.getCategoria())
                            .child(anuncioSelecionado.getIdAnuncio())
                            .child("statusComissao");
                    anuncioRef.setValue(valorDigitado);
                    finish();




                }


            }
        });

        alertDialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }


    //aqui a dialog 3

    public void abrirDialog3(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetalhesRelatorioActivity.this);

        alertDialog.setTitle("Data");
        alertDialog.setMessage("insira a data");
        alertDialog.setCancelable(false);
        final EditText editTextValor= new EditText(DetalhesRelatorioActivity.this);


       // editTextValor.setInputType(InputType.TYPE_CLASS_DATETIME); //aqui era pra setar numeros apenas


        alertDialog.setView(editTextValor);


        alertDialog.setPositiveButton("salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String valorDigitado = editTextValor.getText().toString();

                if(valorDigitado.isEmpty()){
                    Toast.makeText(DetalhesRelatorioActivity.this,"Preencha o campo para salvar",Toast.LENGTH_LONG).show();

                }else{

                    String idUsuario = ConfiguracaoFirebase.getIdUsuario();
                    DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                            .child("relatorio")
                            .child(idUsuario)
                            .child(anuncioSelecionado.getEstado())
                            .child(anuncioSelecionado.getCategoria())
                            .child(anuncioSelecionado.getIdAnuncio())
                            .child("dataPagamento");
                    anuncioRef.setValue(valorDigitado);
                    finish();




                }


            }
        });

        alertDialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }



    public void atualizarActivity(){


       // finish();
      //  startActivity(getIntent()); // teste recarregar activity

    }

}
