package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.w3c.dom.Text;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.adapter.AdapterAnuncios;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;
import indiqueeganhe2019.com.indiqueeganhemobileag.model.Anuncio;
import okhttp3.internal.Util;

public class DetalhesProdutoActivity extends AppCompatActivity {

   // private CarouselView carouselView;
    private TextView  titulo;
    private TextView preco;
    private TextView descricao;
    private Anuncio anuncioSelecionado;
    private DatabaseReference databaseReference;   //aqui não sei






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        inicializarComponentes();
        //recupera anuncios  para  exibição

        anuncioSelecionado= (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");
        if(anuncioSelecionado !=null){

            titulo.setText(anuncioSelecionado.getTitulo());
            descricao.setText(anuncioSelecionado.getDescricao());
            preco.setText(anuncioSelecionado.getValor());

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                   // String urlString= anuncioSelecionado.getFotos().get(position);
                   // Picasso.get().load(urlString).into(imageView);

                }
            };

                  //  carouselView.setPageCount(anuncioSelecionado.getFotos().size());
                 //   carouselView.setImageListener(imageListener);


        }


    }


    private  void inicializarComponentes(){

        //carouselView= findViewById(R.id.carouselView);
        titulo= findViewById(R.id.textTituloDetalhe);
        preco= findViewById(R.id.textPrecoDetalhe);
        descricao= findViewById(R.id.textDescricaoDetalhe);




    }

               //metodos pega contatos do parceiro

   /* public void visualizarTelefone(View view){

        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefone(),null));
        startActivity(i);

    }

    public void abrirWhatsParceiro(View view){

        String numero =  anuncioSelecionado.getTelefone();
        Intent whatsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.whatsapp.com/send?1=pt_BR&phone=55 " +numero));
        startActivity(whatsIntent);

    }

        //metodos contatos cliente

    public void visualizarTelefoneCliente(View view){

        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefoneCliente(),null));
        startActivity(i);

    }

    public void abrirWhatsCliente(View view){

        String numero =  anuncioSelecionado.getTelefoneCliente();
        Intent whatsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.whatsapp.com/send?1=pt_BR&phone=55 " +numero));
        startActivity(whatsIntent);

    }*/

    public void aceitarIndicacao(View view){
        String parceiro= anuncioSelecionado.getEstado();
        String categoria= anuncioSelecionado.getCategoria();
        String nomeCliente=anuncioSelecionado.getTitulo();
        String email=anuncioSelecionado.getValor();
        String telCliente=anuncioSelecionado.getTelefoneCliente();
        String seuTel=anuncioSelecionado.getTelefone();
        String descricao=anuncioSelecionado.getDescricao();
        String residencia= anuncioSelecionado.getnResidencia();
        String complemento= anuncioSelecionado.getComplemento();
        String operadora= anuncioSelecionado.getOperadoraAtual();
        String data=anuncioSelecionado.getData();
        String retorno1=anuncioSelecionado.getRetorno1();
        String retorno2=anuncioSelecionado.getRetorno2();
        String retorno3=anuncioSelecionado.getRetorno3();
        String nomeUsuario= anuncioSelecionado.getNomeUsuario();
        String emailUsuario=anuncioSelecionado.getEmailUsuario();
        String numeroUsuario=anuncioSelecionado.getNumeroUsuario();
        String valorComissao= anuncioSelecionado.getValorComissao();
        String dataPagamento= anuncioSelecionado.getDataPagamento();
        String statusPagamento= anuncioSelecionado.getStatusComissao();

        Anuncio anuncio= new Anuncio();
        anuncio.setEstado(parceiro);
        anuncio.setCategoria(categoria);
        anuncio.setTitulo(nomeCliente);
        anuncio.setValor(email);
        anuncio.setTelefoneCliente(telCliente);
        anuncio.setTelefone(seuTel);
        anuncio.setDescricao(descricao);
        anuncio.setnResidencia(residencia);
        anuncio.setComplemento(complemento);
        anuncio.setOperadoraAtual(operadora);
        anuncio.setData(data);
        anuncio.setRetorno1(retorno1);
        anuncio.setRetorno2(retorno2);
        anuncio.setRetorno3(retorno3);
        anuncio.setNomeUsuario(nomeUsuario);
        anuncio.setEmailUsuario(emailUsuario);
        anuncio.setNumeroUsuario(numeroUsuario);
        anuncio.setValorComissao(valorComissao);
        anuncio.setDataPagamento(dataPagamento);
        anuncio.setStatusComissao(statusPagamento);
        anuncio.salvarRelatorio();//depois  de salvar  preciso excluir o selecionado para não repetir o relatorio e limpar ele de indicação
        anuncioSelecionado.removerAnuncioPublico();
        finish();







    }


    public void excluirRecusarIndicacao(View view){
        anuncioSelecionado.removerAnuncioPublico();
        finish();

    }

    public void sairFechar(View view){
        finish();
    }








}
