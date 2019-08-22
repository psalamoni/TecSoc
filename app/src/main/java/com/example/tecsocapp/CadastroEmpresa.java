package com.example.tecsocapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecsocapp.modelo.Empresa;
import com.example.tecsocapp.modelo.TipoPerfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.UUID;

public class CadastroEmpresa extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editEmail, editSenha;
    private EditText razaoSocial;
    private EditText cnpj;
    private EditText telefone;
    private EditText endereco;
    private EditText representante;
    private String area_atuacao;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private  Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);
        spinner = findViewById(R.id.area_atuacao_empresa);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.area_de_atuacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        inicializaComponentes();
        inicializarFirebase();
        eventoClicks();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastroEmpresa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    private void eventoClicks() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                criarUser(email,senha);
            }
        });
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(CadastroEmpresa.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String usuarioId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                    Empresa empresa = new Empresa();
                    empresa.setId_empresa(UUID.randomUUID().toString());
                    empresa.setId_usuario(usuarioId);
                    empresa.setRazaoSocial(razaoSocial.getText().toString());
                    empresa.setCnpj(cnpj.getText().toString());
                    empresa.setEndereco(endereco.getText().toString());
                    empresa.setRepresentante(representante.getText().toString());
                    empresa.setArea_atuacao(area_atuacao);

                    TipoPerfil tipoPerfil = new TipoPerfil();
                    tipoPerfil.setUsuarioId(usuarioId);
                    tipoPerfil.setPerfil(TipoPerfil.PERFIL_EMPRESA);

                    databaseReference.child("empresa").child(empresa.getId_empresa()).setValue(empresa);
                    databaseReference.child("tipoperfil").child(usuarioId).setValue(tipoPerfil);

                    alert("Usuario Cadastrado com sucesso");
                    Intent i = new Intent (CadastroEmpresa.this, EmpresaActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    alert("Erro de Cadastro");
                }
            }
        });
    }
    private void alert(String msg){
        Toast.makeText(CadastroEmpresa.this, msg,Toast.LENGTH_SHORT).show();
    }
    private void inicializaComponentes() {
        editEmail = findViewById(R.id.cadastroEmail);
        editSenha = findViewById(R.id.cadastrosenha);
        btnRegistrar = findViewById(R.id.btn_cadastro_empresa_registrar);
        btnVoltar = findViewById(R.id.btn_cadastro_empresa_voltar);
        razaoSocial = findViewById(R.id.cadastro_empresa_razao_social);
        cnpj = findViewById(R.id.cadastro_empresa_cnpj);
        endereco = findViewById(R.id.cadastro_empresa_endereco);
        representante = findViewById(R.id.cadastro_empresa_representante);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        area_atuacao = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), area_atuacao, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
