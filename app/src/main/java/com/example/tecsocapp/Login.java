package com.example.tecsocapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class Login extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button btnLogar, btnNovo,btnNovoPesquisa;
    private TextView txtResetSenha;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadastroEmpresa.class);
                startActivity(i);
            }
        });
        btnNovoPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadastroPesquisa.class);
                startActivity(i);
            }
        });
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Login
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                login(email,senha);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Query query = databaseReference.child("tipoperfil").orderByChild("usuarioId").equalTo(auth.getCurrentUser().getUid());
                    if(){ // Ver na documentação como fazer a seleção de dados para verificar o Perfil. Faça, Pascal
                        Intent i = new Intent(Login.this, EmpresaActivity.class);

                    }else{

                    }
                    startActivity(i);
                }else{
                    alert("E-mail ou senha incorretos");
                }
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(Login.this,s, Toast.LENGTH_LONG).show();
    }

    private void inicializaComponentes(){
        editEmail =findViewById(R.id.email);
        editSenha = findViewById(R.id.senha);
        btnLogar = findViewById(R.id.logar);
        btnNovo = findViewById(R.id.cadastro_empresa);
        btnNovoPesquisa = findViewById(R.id.cadastro_pesquisa);

    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
