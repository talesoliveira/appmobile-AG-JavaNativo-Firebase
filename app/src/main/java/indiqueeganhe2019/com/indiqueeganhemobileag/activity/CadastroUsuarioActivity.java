package indiqueeganhe2019.com.indiqueeganhemobileag.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.santalu.maskedittext.MaskEditText;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;
import indiqueeganhe2019.com.indiqueeganhemobileag.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private MaskEditText numero;
    private EditText email;
    private EditText senha;
    private Button buttonCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("cadastre-se");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();



        //criar usuario e dar get nos campos  de texto do cadastro

       /* buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });  */



    }


    public  Usuario configurarUsuario(){
        String nomeUser =nome.getText().toString();
        String numeroUser= numero.getText().toString();
        String emailUser= email.getText().toString();
        String senhaUser= senha.getText().toString();


        Usuario usuario = new Usuario(); //importante que seja instanciado assim e return
        usuario.setNomeCompleto(nome.getText().toString());
        usuario.setNumeroUser(numero.getText().toString());
        usuario.setEmailUser(email.getText().toString());
        usuario.setSenhaUser(senha.getText().toString());

        return usuario;
    }

    private void cadastrarUsuario(){
        autenticacao= ConfiguracaoFirebase.getReferenciaAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmailUser(),
                usuario.getSenhaUser()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser= task.getResult().getUser();
                    usuario.setIdUser(firebaseUser.getUid());
                    usuario.salvarUsuario();
                    autenticacao.signOut();
                    finish();

                    Toast.makeText(CadastroUsuarioActivity.this,"Cadastro Criado com Sucesso!",Toast.LENGTH_SHORT).show();

                }else{

                    String erroExececao = "";
                     try {
                         throw task.getException();
                     } catch (FirebaseAuthWeakPasswordException e) {
                         erroExececao="Digite uma senha mais forte!";
                     } catch (FirebaseAuthInvalidCredentialsException e) {
                         erroExececao="O e-mail digitado é inválido!";

                     } catch (FirebaseAuthUserCollisionException e) {
                         erroExececao="O e-mail já esta em uso no app!";
                     } catch (Exception e) {
                         erroExececao="ao cadastrar usuário!";
                     }


                    Toast.makeText(CadastroUsuarioActivity.this,"Erro: " +erroExececao,Toast.LENGTH_SHORT).show();

                }

            }
        });
    }



    public void validarDadosUsuario(View view){
        usuario= configurarUsuario(); //importante aqui config usuario
        if(!usuario.getNomeCompleto().isEmpty()){
            if(!usuario.getNumeroUser().isEmpty()){
                if(!usuario.getEmailUser().isEmpty()){
                    if(!usuario.getSenhaUser().isEmpty()){
                        cadastrarUsuario();

                    }else{
                        exibirMensagemErro("Preencha a senha!");
                    }

                }else{
                    exibirMensagemErro("Preencha o email!");
                }

            }else{
                exibirMensagemErro("Preencha o seu numero ou whatsApp!");
            }

        }else{
            exibirMensagemErro("Preencha o nome completo!");
        }
    }

    public void inicializarComponentes(){

        nome= findViewById(R.id.editCadastroNome);
        numero= findViewById(R.id.editCadastroNumero);
        email= findViewById(R.id.editCadastroEmail);
        senha=findViewById(R.id.editCadastroSenha);
        buttonCadastrar=findViewById(R.id.btnCadastrar);

    }


    private void exibirMensagemErro(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

}
