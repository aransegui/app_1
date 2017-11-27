package appservice.aransegui.br.prpjetofirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import appservice.aransegui.br.prpjetofirebase.DAO.ConfiguracaoFirebase;
import appservice.aransegui.br.prpjetofirebase.Entidades.Usuarios;
import appservice.aransegui.br.prpjetofirebase.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private TextView tvabreCadastro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogar = findViewById(R.id.btnLogar);
        tvabreCadastro= findViewById(R.id.tvAbreCadastro);


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmail.getText().toString().equals("")&& !edtSenha.getText().toString().equals("")){

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());

                    usuarios.setSenha(edtSenha.getText().toString());
                    validarLogin();



                }else{


                    Toast.makeText(LoginActivity.this,"Preencha os campos com email e senha",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvabreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastroUsuario();
            }
        });

    }



    private void validarLogin(){

        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this,"Login Efetuado com Sucesso",Toast.LENGTH_SHORT).show();


                }else{

                    Toast.makeText(LoginActivity.this,"Usuario ou Senha invalidos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
        
    }

    public void abrirTelaPrincipal(){

        Intent intentAbrirTelaPrincipal=new Intent(LoginActivity.this,PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);

    }

    public void abrirCadastroUsuario(){


        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}
