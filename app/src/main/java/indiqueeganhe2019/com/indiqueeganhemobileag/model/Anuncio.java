package indiqueeganhe2019.com.indiqueeganhemobileag.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import indiqueeganhe2019.com.indiqueeganhemobileag.dialog.SingleChoiceDialogFragment;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;

public class Anuncio implements Serializable {
    private String idAnuncio;
    private String estado;//parceiro
    private String categoria;// tipo tv fixo etc...
    private String titulo;//aqui nome
    private String valor; //AQUI É CPF OU CNPJ
    private String telefone;//telefone de quem indicou
    private String telefoneCliente;
    private String descricao;// este virou cep
    private String nResidencia;
    private String complemento;
    private String operadoraAtual;
    private String data;
    private String retorno1;
    private String retorno2;
    private String retorno3;
    private String nomeUsuario;
    private String numeroUsuario;
    private String emailUsuario;
    private String valorComissao;
    private String statusComissao;
    private String dataPagamento;

    public String getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(String valorComissao) {
        this.valorComissao = valorComissao;
    }

    public String getStatusComissao() {
        return statusComissao;
    }

    public void setStatusComissao(String statusComissao) {
        this.statusComissao = statusComissao;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNumeroUsuario() {
        return numeroUsuario;
    }

    public void setNumeroUsuario(String numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getRetorno1() {
        return retorno1;
    }

    public void setRetorno1(String retorno1) {
        this.retorno1 = retorno1;
    }

    public String getRetorno2() {
        return retorno2;
    }

    public void setRetorno2(String retorno2) {
        this.retorno2 = retorno2;
    }

    public String getRetorno3() {
        return retorno3;
    }

    public void setRetorno3(String retorno3) {
        this.retorno3 = retorno3;
    }

    public String getnResidencia() {
        return nResidencia;
    }

    public void setnResidencia(String nResidencia) {
        this.nResidencia = nResidencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getOperadoraAtual() {
        return operadoraAtual;
    }

    public void setOperadoraAtual(String operadoraAtual) {
        this.operadoraAtual = operadoraAtual;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    //  private List<String> fotos; //aqui tirei

    public Anuncio() {
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase() //aqui no contrutor peguei id
                .child("meus_anuncios");
        setIdAnuncio(anuncioRef.push().getKey());

    }
    public void salvar(){
        String idUsuario= ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("meus_anuncios");
        anuncioRef.child(idUsuario).child(getIdAnuncio()).setValue(this);
        salvarAnuncioPublico();


    }
    // salvar relatorio
    public void salvarRelatorio(){
        String idUsuario= ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorio");
        anuncioRef.child(idUsuario)
                .child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio())
                .setValue(this);



    }
    //editar relatorio
    public void updateRetorno(){
        String idUsuario= ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("relatorio")
                .child(idUsuario)
                .child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio())
                .child("retorno1");

            anuncioRef.setValue("");


    }





    public void salvarAnuncioPublico(){

        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios");
        anuncioRef.child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio())
                .setValue(this);


    }
    public void remover(){

        String idUsuario= ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("meus_anuncios")
                .child(idUsuario)
                .child(getIdAnuncio());
        anuncioRef.removeValue();

    }

    public void removerAnuncioPublico(){
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios")
                .child(getEstado())
                .child(getCategoria())
                .child(getIdAnuncio());
        anuncioRef.removeValue();
    }


    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   // public List<String> getFotos() {
       // return fotos;
   // }

 //   public void setFotos(List<String> fotos) {
    //    this.fotos = fotos;
  //  }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }



}
