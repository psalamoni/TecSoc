package com.example.tecsocapp.pesquisaempresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tecsocapp.EmpresaMain;
import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Pesquisa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PesquisasEmpresa extends Fragment {

    View PesquisasView;
    private List<Pesquisa> listpesquisa = new ArrayList<Pesquisa>();
    EmpresaMain perfilempresa = new EmpresaMain();
    DatabaseReference databaseReference = perfilempresa.inicializarFirebase();
    FirebaseDatabase firebaseDatabase;


    public ListView getListViewPesquisa() {
        return listViewPesquisa;
    }

    public ListView  listViewPesquisa;

    public PesquisasEmpresa() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        PesquisasView = inflater.inflate(R.layout.fragment_pesquisas, container, false);

        listViewPesquisa = (ListView)PesquisasView.findViewById(R.id.lista_pesquisass);

        String s = "";

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
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()) {
                    Pesquisa p = objSnapshot.getValue(Pesquisa.class);
                    listpesquisa.add(p);
                }
                listPesquisaAdapter adapter= new listPesquisaAdapter(PesquisasEmpresa.this, listpesquisa);
                listViewPesquisa.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return PesquisasView;

    }
}
