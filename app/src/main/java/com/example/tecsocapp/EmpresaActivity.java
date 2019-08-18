package com.example.tecsocapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Empresa;
import com.example.tecsocapp.modelo.Pesquisa;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class EmpresaActivity extends Activity {
    private List<Pesquisa> listpesquisa = new ArrayList<Pesquisa>();
    private FirebaseAuth auth;
    private FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ListView  listViewPesquisa;
    private int[]mImages = new int[]{
      R.drawable.agro,R.drawable.quimica,R.drawable.fisica,R.drawable.universia
    };
    private String[ ]MImageTitle = new String[]{
            "Agronomia","Quimica","Fisica","Universidade"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        iniciarcomponentes();
        inicializarFirebase();
        eventoDatabase();

        CarouselView carouselView = findViewById(R.id.imageView3);
        carouselView.setPageCount(mImages.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(EmpresaActivity.this,MImageTitle[position], Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void requisito_pesquisa(View view){

        Intent intent = new Intent(this, requisito_empresa.class);
        startActivity(intent);
    }

    private void iniciarcomponentes() {
        listViewPesquisa = findViewById(R.id.lista_pesquisa);
    }

    private void eventoDatabase() {

    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        verificarUser(); //Verificar se ele é um usuario
        listpesquisa.clear();
        pesquisarPalavra("");
    }

    private void verificarUser() {
        if(user==null) {
            finish();
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(EmpresaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pesquisarPalavra("");
    }

    private void pesquisarPalavra(String s) {
        Query query;
        if(s.equals("")){
            query = databaseReference.child("pesquisa").orderByChild("titulo");
        }else{
            query = databaseReference.child("pesquisa").orderByChild("titulo").startAt(s).endAt(s+"\uf8ff"); // Pesquisar qualquer coisa no final
        }
        listpesquisa.clear();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Pesquisa p = objSnapshot.getValue(Pesquisa.class);
                    listpesquisa.add(p);
                }
                listPesquisaAdapter adapter= new listPesquisaAdapter(EmpresaActivity.this, listpesquisa);
                listViewPesquisa.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
