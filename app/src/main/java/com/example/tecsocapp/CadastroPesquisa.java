package com.example.tecsocapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecsocapp.modelo.Pesquisa;
import com.example.tecsocapp.modelo.TipoPerfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.UUID;

public class CadastroPesquisa extends AppCompatActivity {
    private TextInputEditText titulo, grande_area, descricao, email, universidade, instituicao, professor, contato, senha, pequena_area;
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
        btnRegistrar.setOnClickListener(view -> {
            String email2, senha2;
            email2 = email.getText().toString().trim();
            senha2 = senha.getText().toString().trim();
            criarUser(email2, senha2);
        });

        btnVoltar.setOnClickListener(view -> finish());
    }

    private void criarUser(String email2, String senha2) {
        if (!isCamposPreenchidos())
            return;

        auth.createUserWithEmailAndPassword(email2, senha2).addOnCompleteListener(CadastroPesquisa.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String usuarioId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                    Pesquisa pesquisa = new Pesquisa();
                    pesquisa.setTitulo(titulo.getText().toString());
                    pesquisa.setGrande_area(grande_area.getText().toString());
                    pesquisa.setPequena_area(pequena_area.getText().toString());
                    pesquisa.setDescricao(descricao.getText().toString());
                    pesquisa.setUniversidade(universidade.getText().toString());
                    pesquisa.setInstituto(instituicao.getText().toString());
                    pesquisa.setProfessor(professor.getText().toString());
                    pesquisa.setContatos(contato.getText().toString());
                    pesquisa.setId_pesquisa(UUID.randomUUID().toString());
                    pesquisa.setId_usuario(usuarioId);

                    // titulo,grande_area,descricao,email,universidade,instituicao,professor,contato,senha
                    databaseReference.child("pesquisa").child(pesquisa.getId_pesquisa()).setValue(pesquisa);

                    TipoPerfil tipoPerfil = new TipoPerfil();
                    tipoPerfil.setUsuarioId(usuarioId);
                    tipoPerfil.setPerfil(TipoPerfil.PERFIL_PESQUISA);

                    databaseReference.child("tipoperfil").child(usuarioId).setValue(tipoPerfil);

                    alert("Usuario Cadastrado com sucesso");
                    Intent i = new Intent(CadastroPesquisa.this, Login.class);
                    startActivity(i);
                    finish();
                } else {
                    alert("Erro de Cadastro");
                }
            }
        });
    }

    private void alert(String msg) {
        Toast.makeText(CadastroPesquisa.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializaComponentes() {
        titulo = findViewById(R.id.cadastro_pesquisa_titulo);
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

    private boolean isCamposPreenchidos() {

        boolean preenchidos = mostrarObrigatorioSeNaoPreenchido(titulo);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(grande_area);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(descricao);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(email);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(universidade);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(instituicao);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(professor);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(contato);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(senha);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(pequena_area);

        return preenchidos;
    }

    private boolean mostrarObrigatorioSeNaoPreenchido(TextInputEditText editText) {
        boolean erroExibido = false;

        if (editText.getText().toString().isEmpty()) {
            ((TextInputLayout) editText.getParent().getParent()).setError("Campo obrigatório");
            erroExibido = true;
        } else {
            ((TextInputLayout) editText.getParent().getParent()).setError(null);
        }

        return !erroExibido;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
