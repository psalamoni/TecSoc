package com.example.tecsocapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tecsocapp.modelo.Empresa;
import com.example.tecsocapp.modelo.Pesquisa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CadastroPesquisa extends AppCompatActivity {
    private EditText titulo,grande_area,descricao,email,universidade,instituicao,professor,contato,senha,pequena_area;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pesquisa);
        inicializaComponentes();
        inicializarFirebase();
        eventoClicks();
    }

    private void eventoClicks() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2,senha2;
                email2 = email.getText().toString().trim();
                senha2 = senha.getText().toString().trim();
                criarUser(email2,senha2);
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void criarUser(String email2, String senha2) {
        auth.createUserWithEmailAndPassword(email2,senha2).addOnCompleteListener(CadastroPesquisa.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Pesquisa pesquisa = new Pesquisa();
                    pesquisa.setTitulo(titulo.getText().toString());
                    pesquisa.setGrande_area(titulo.getText().toString());
                    pesquisa.setPequena_area(pequena_area.getText().toString());
                    pesquisa.setDescricao(descricao.getText().toString());
                    pesquisa.setUniversidade(universidade.getText().toString());
                    pesquisa.setInstituto(instituicao.getText().toString());
                    pesquisa.setProfessor(professor.getText().toString());
                    pesquisa.setContatos(contato.getText().toString());
                    pesquisa.setId_pesquisa(UUID.randomUUID().toString());
                    pesquisa.setId_usuario(auth.getCurrentUser().getUid());




                   // titulo,grande_area,descricao,email,universidade,instituicao,professor,contato,senha
                    databaseReference.child("pesquisa").child(pesquisa.getId_pesquisa()).setValue(pesquisa);

                    alert("Usuario Cadastrado com sucesso");
                    Intent i = new Intent (CadastroPesquisa.this, Login.class);
                    startActivity(i);
                    finish();
                }else{
                    alert("Erro de Cadastro");
                }
            }
        });
    }

    private void alert(String msg) {
        Toast.makeText(CadastroPesquisa.this, msg,Toast.LENGTH_SHORT).show();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastroPesquisa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializaComponentes() {
        titulo= findViewById(R.id.cadastro_pesquisa_titulo);
        grande_area = findViewById(R.id.cadastro_pesquisa_grande_area);
        descricao = findViewById(R.id.cadastro_pesquisa_descricao);
        email = findViewById(R.id.cadastro_pesquisa_email);
        universidade = findViewById(R.id.cadastro_pesquisa_universidade);
        instituicao = findViewById(R.id.cadastro_pesquisa_instituto);
        professor = findViewById(R.id.cadastro_pesquisa_professor);
        contato = findViewById(R.id.cadastro_pesquisa_contato);
        senha = findViewById(R.id.cadastro_pesquisa_senha);
        btnRegistrar = findViewById(R.id.btn_cadastro_pesquisa_registrar);
        btnVoltar = findViewById(R.id.btn_cadastro_pesquisa_voltar);
        pequena_area = findViewById(R.id.cadastro_pesquisa_pequena_area);
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
