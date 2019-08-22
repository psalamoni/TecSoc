package com.example.tecsocapp.pesquisa.requisicao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tecsocapp.modelo.Empresa;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequisicaoDetalhesViewModel extends ViewModel {
    private String mIdRequisicao;
    private String mIdUsuario;
    private MutableLiveData<RequisitoPesquisa> mRequisitoPesquisa;
    private MutableLiveData<Empresa> mEmpresa;

    LiveData<RequisitoPesquisa> getRequisitoPesquisa() {
        if (mRequisitoPesquisa == null) {
            mRequisitoPesquisa = new MutableLiveData<>();
            loadRequisitoFromDb();
        }

        return mRequisitoPesquisa;
    }

    LiveData<Empresa> getEmpresa() {
        if (mEmpresa == null) {
            mEmpresa = new MutableLiveData<>();
            loadEmpresaFromDb();
        }

        return mEmpresa;
    }

    private void loadRequisitoFromDb() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("requisitoPesquisa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot.child(mIdRequisicao);
                RequisitoPesquisa requisito = snap.getValue(RequisitoPesquisa.class);

                mRequisitoPesquisa.setValue(requisito);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(this.getClass().getSimpleName(), "Falha ao buscar o requisito de pesquisa selecionado");
            }
        });
    }

    private void loadEmpresaFromDb() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("empresa").orderByChild("id_usuario").equalTo(mIdUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot.getChildren().iterator().next();
                Empresa empresa = snap.getValue(Empresa.class);

                mEmpresa.setValue(empresa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(this.getClass().getSimpleName(), "Falha ao buscar informação da empresa ligada ao requisito");
            }
        });
    }

    void setIdRequisicao(String idRequisicao) {
        this.mIdRequisicao = idRequisicao;
    }

    void setIdUsuario(String idUsuario) {
        this.mIdUsuario = idUsuario;
    }
}
