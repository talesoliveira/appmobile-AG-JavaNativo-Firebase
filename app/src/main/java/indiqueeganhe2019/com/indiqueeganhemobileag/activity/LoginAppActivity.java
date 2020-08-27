package indiqueeganhe2019.com.indiqueeganhemobileag.activity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import indiqueeganhe2019.com.indiqueeganhemobileag.R;
import indiqueeganhe2019.com.indiqueeganhemobileag.helper.ConfiguracaoFirebase;


public class LoginAppActivity extends AppCompatActivity {

    private Button botaoEntrar;
    private EditText campoEmail, campoSenha;

    private FirebaseAuth autenticacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha= campoSenha.getText().toString();
                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(LoginAppActivity.this,"Logado Com Sucesso!",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        finish();

                                    }else{
                                        Toast.makeText(LoginAppActivity.this,"Erro ao Fazer Login!"+task.getException(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                    }else{
                        Toast.makeText(LoginAppActivity.this,"preencha a senha!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginAppActivity.this,"preencha o E-mail!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        botaoEntrar= findViewById(R.id.btnEntrar);

    }


    public void abrirCadastroUser(View view){

        startActivity(new Intent(LoginAppActivity.this,CadastroUsuarioActivity.class));


    }





}
