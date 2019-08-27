package com.example.tecsocapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.tecsocapp.modelo.Pesquisa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DescricaoPesquisasEmpresa extends Fragment {

    private TextView titulo;
    private TextView descricao;
    private TextView pequena_area;
    private TextView pesquisa_endereco;
    private TextView professor;
    private TextView contatos;
    private Button btnVoltar;
    View DescricaoPesquisasView;
    EmpresaMain empresamain = new EmpresaMain();
    DatabaseReference databaseReference = empresamain.inicializarFirebase();

    public DescricaoPesquisasEmpresa() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DescricaoPesquisasView = inflater.inflate(R.layout.fragment_descricao_pesquisa, container, false);
        String id = empresamain.getIdviewpesquisa();

        inicializaComponentes();

        Query query;
        query = databaseReference.child("pesquisa");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()) {
                    Pesquisa pesquisa = objSnapshot.getValue(Pesquisa.class);
                    if (pesquisa.getId_pesquisa().equals(id)) {
                        titulo.setText(pesquisa.getTitulo());
                        descricao.setText(pesquisa.getDescricao());
                        pequena_area.setText(pesquisa.getPequena_area());
                        pesquisa_endereco.setText(pesquisa.getInstituto().concat(" - ").concat(pesquisa.getUniversidade()));
                        professor.setText(pesquisa.getProfessor());
                        contatos.setText(pesquisa.getContatos());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eventoClicks();

        return DescricaoPesquisasView;

    }

    private void eventoClicks() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empresamain.setViewPager(0);
            }
        });
    }

    private void inicializaComponentes() {
        titulo = (TextView)DescricaoPesquisasView.findViewById(R.id.pesquisa_titulo);
        descricao = (TextView)DescricaoPesquisasView.findViewById(R.id.pesquisa_descricao);
        pequena_area = (TextView)DescricaoPesquisasView.findViewById(R.id.pesquisa_area);
        pesquisa_endereco = (TextView)DescricaoPesquisasView.findViewById(R.id.pesquisa_endereco);
        professor = (TextView)DescricaoPesquisasView.findViewById(R.id.pesquisa_professor);
        contatos = (TextView)DescricaoPesquisasView.findViewById(R.id.pesquisa_contato);
        btnVoltar = (Button)DescricaoPesquisasView.findViewById(R.id.voltar_descricao);
    }
}
