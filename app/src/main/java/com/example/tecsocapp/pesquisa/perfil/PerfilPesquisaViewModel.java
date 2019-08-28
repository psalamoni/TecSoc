package com.example.tecsocapp.pesquisa.perfil;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tecsocapp.modelo.Pesquisa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilPesquisaViewModel extends ViewModel {
    private String mUsuarioId;
    private MutableLiveData<Pesquisa> mPesquisa;

    LiveData<Pesquisa> getPesquisa() {
        if (mPesquisa == null) {
            mPesquisa = new MutableLiveData<>();
            loadPesquisaFromDb();
        }

        return mPesquisa;
    }

    private void loadPesquisaFromDb() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("pesquisa")
                .orderByChild("id_usuario")
                .equalTo(mUsuarioId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot snap = dataSnapshot.getChildren().iterator().next();
                        Pesquisa pesquisa = snap.getValue(Pesquisa.class);

                        mPesquisa.setValue(pesquisa);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(this.getClass().getSimpleName(), "Falha ao buscar a pesquisa selecionada");
                    }
                });
    }

    public String getUsuarioId() {
        return mUsuarioId;
    }

    public void setUsuarioId(String mUsuarioId) {
        this.mUsuarioId = mUsuarioId;
    }
}
