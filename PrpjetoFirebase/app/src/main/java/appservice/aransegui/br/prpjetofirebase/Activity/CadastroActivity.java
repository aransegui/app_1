package appservice.aransegui.br.prpjetofirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import appservice.aransegui.br.prpjetofirebase.DAO.ConfiguracaoFirebase;
import appservice.aransegui.br.prpjetofirebase.Entidades.Usuarios;
import appservice.aransegui.br.prpjetofirebase.Helper.Base64Custom;
import appservice.aransegui.br.prpjetofirebase.Helper.PreferidaAndroid;
import appservice.aransegui.br.prpjetofirebase.R;




public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadSenha;
    private EditText edtCadConfirmarSenha;
    private EditText edtCadAniversario;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button btnGravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadEmail = findViewById(R.id.edtCadEmail);
        edtCadNome= findViewById(R.id.edtCadNome);
        edtCadSenha= findViewById(R.id.edtCadSenha);
        edtCadSobrenome= findViewById(R.id.edtCadSobrenome);
        edtCadConfirmarSenha= findViewById(R.id.edtCadConfirmaSenha);
        edtCadAniversario= findViewById(R.id.edtAniversario);
        rbFeminino= findViewById(R.id.rbFeminino);
        rbMasculino = findViewById(R.id.rbMasculino);
        btnGravar = findViewById(R.id.btnGravar);


        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtCadSenha.getText().toString().equals(edtCadConfirmarSenha.getText().toString())){
                  usuarios = new Usuarios();
                  usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setSobrenome(edtCadSobrenome.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());
                    usuarios.setAniversario(edtCadAniversario.getText().toString());
                    if(rbFeminino.isChecked()){
                        usuarios.setSexo("Feminino");

                    }else{
                        usuarios.setSexo("Masculino");


                    } cadastrarUsuario();
                }else{

                    Toast.makeText(CadastroActivity.this,"As senhas não correspondem",Toast.LENGTH_LONG).show();


                }

            }
        });


    }

    private void  cadastrarUsuario () {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        final Task<AuthResult> authResultTask = autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(), usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(CadastroActivity.this, "Usuario Cadastrado com Sucesso", Toast.LENGTH_LONG).show();
                    String identificadorUsuario = Base64Custom.
                            decodificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebas= task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();
                    PreferidaAndroid preferidaAndroid = new PreferidaAndroid(CadastroActivity.this);
                    preferidaAndroid.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());
                    abrirLoginUsuario();
                                            } else
                                                {

                    String erroExcessao = "";

                    try {

                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {


                        erroExcessao = "Digite uma senha mais forte";

                    } catch (FirebaseAuthInvalidCredentialsException e) {

                        erroExcessao = "O email digitado é errado";

                    } catch (FirebaseAuthUserCollisionException e) {

                        erroExcessao = "Email já cadastrado no sistema";
                    } catch (Exception e) {

                        erroExcessao = "Erro ao Efetuar o Cadastro";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            "Erro:" + erroExcessao, Toast.LENGTH_LONG).show();


                }
            }
        });


    }

    public void abrirLoginUsuario (){

        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
       finish();

    }
}
