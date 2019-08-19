package com.example.tecsocapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class requisito_empresa extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Button btnCadastrar;
    private EditText nome;
    private String area;
    private EditText descricao;
    private EditText valor;
    private EditText obsAdicional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisito_empresa);
        Spinner spinner = findViewById(R.id.area_pesquisa);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.area_de_atuacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        inicializaComponentes();
        inicializarFirebase();
        eventoClicks();

    }

    public void cadastrarRequisito(){
        RequisitoPesquisa requisitoPesquisa = new RequisitoPesquisa();
        requisitoPesquisa.setId_requisito_pesquisa(UUID.randomUUID().toString());
        requisitoPesquisa.setId_usuario(auth.getCurrentUser().getUid());
        requisitoPesquisa.setArea(this.area);
        requisitoPesquisa.setDescricao(this.descricao.getText().toString());
        requisitoPesquisa.setNome(this.nome.getText().toString());
        requisitoPesquisa.setObsAdicional(this.obsAdicional.getText().toString());
        requisitoPesquisa.setValor(this.valor.getText().toString());
        databaseReference.child("requisitoPesquisa").child(requisitoPesquisa.getId_requisito_pesquisa()).setValue(requisitoPesquisa);

    }
    private void eventoClicks() {
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarRequisito();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(requisito_empresa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializaComponentes() {
        btnCadastrar = findViewById(R.id.requisitoPesquisaCadastro);
        nome = findViewById(R.id.editNomeRequisicao);
        descricao = findViewById(R.id.editDescricao);
        valor = findViewById(R.id.editValor);
        obsAdicional = findViewById(R.id.editObs);

    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.area = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), this.area, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
