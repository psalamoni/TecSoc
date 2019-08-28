package com.example.tecsocapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecsocapp.modelo.TipoPerfil;
import com.example.tecsocapp.pesquisa.PesquisaMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button btnLogar, btnNovo, btnNovoPesquisa;
    private TextView txtResetSenha;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static String sUsuarioId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();
        inicarFirebase();
        eventoClicks();
    }

    private void inicarFirebase() {
        FirebaseApp.initializeApp(Login.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
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
                login(email, senha);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Query query;
                    String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                    databaseReference.child("tipoperfil").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                                TipoPerfil p = objSnapshot.getValue(TipoPerfil.class);
                                if (p.getUsuarioId().equals(id)) {
                                    sUsuarioId = p.getUsuarioId();
                                    String tipoPerfilUsuario = p.getPerfil();

                                    if (tipoPerfilUsuario.equals(TipoPerfil.PERFIL_EMPRESA)) {
                                        Intent i = new Intent(Login.this, EmpresaMain.class);
                                        startActivity(i);
                                    } else if (tipoPerfilUsuario.equals(TipoPerfil.PERFIL_PESQUISA)) {
                                        Intent i = new Intent(Login.this, PesquisaMainActivity.class);
                                        startActivity(i);
                                    } else {
                                        alert("Tipo de Perfil desconhecido: " + tipoPerfilUsuario);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });

                } else {
                    alert("E-mail ou senha incorretos");
                }
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(Login.this, s, Toast.LENGTH_LONG).show();
    }

    private void inicializaComponentes() {
        editEmail = findViewById(R.id.email);
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
