package com.example.tecsocapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilEmpresa extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_empresa);
        inicializaComponentes();
        eventosonClick();
    }

    private void eventosonClick() {
    }

    private void inicializaComponentes() {

    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        verificarUser();
    }

    private void verificarUser() {
        if(user==null){
            finish();
        }else{
            
        }
    }
}
