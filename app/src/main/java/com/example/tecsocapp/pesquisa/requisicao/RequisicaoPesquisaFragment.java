package com.example.tecsocapp.pesquisa.requisicao;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tecsocapp.Login;
import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisicaoFavoritada;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequisicaoPesquisaFragment extends Fragment {

    private OnListFragmentInteractionListener mListListener;
    private OnEditarPesquisaListener mEditarPesquisaListener;
    private RequisicaoPesquisaRecyclerViewAdapter mAdapter;

    private List<ValueEventListener> mDbListeners = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequisicaoPesquisaFragment() {
    }

    public static RequisicaoPesquisaFragment newInstance() {
        return new RequisicaoPesquisaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requisicaopesquisa_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            mAdapter = new RequisicaoPesquisaRecyclerViewAdapter(new ArrayList<>(), mListListener);
            recyclerView.setAdapter(mAdapter);

            exibirTodosRequisitos();
        }

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        if (context instanceof OnEditarPesquisaListener) {
            mEditarPesquisaListener = (OnEditarPesquisaListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEditarPesquisaListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_requisicao, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exibir_todos:
                exibirTodosRequisitos();
                item.setChecked(true);
                return true;

            case R.id.menu_apenas_favoritos:
                exibirApenasFavoritos();
                item.setChecked(true);
                return true;

            case R.id.menu_editar_pesquisa:
                mEditarPesquisaListener.onEditarPesquisa();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListListener = null;
    }

    private void alert(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void loadRequisitosFromDb(OnRequisicoesCarregadasListener reqCarregadasListener) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        ValueEventListener dbListener = db.child("requisitoPesquisa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RequisitoPesquisa> requisitos = new ArrayList<>();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    requisitos.add(objSnapshot.getValue(RequisitoPesquisa.class));
                }

                reqCarregadasListener.onRequisicoesCarregadas(requisitos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                alert("Falha ao buscar os requisitos de pesquisa");
            }
        });

        mDbListeners.add(dbListener);
    }

    private void loadFavoritadasFromDb(OnFavoritosCarregadosListener favCarregadosListener) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        ValueEventListener dbListener = db.child("requisicaoFavoritada")
                .orderByChild("usuarioId")
                .equalTo(Login.sUsuarioId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<RequisicaoFavoritada> requisicoesFavoritas = new ArrayList<>();

                        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                            RequisicaoFavoritada reqFav = objSnapshot.getValue(RequisicaoFavoritada.class);
                            requisicoesFavoritas.add(reqFav);
                        }

                        favCarregadosListener.onFavoritosCarregados(requisicoesFavoritas);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        alert("Falha ao buscar os requisitos de pesquisa favoritados");
                    }
                });

        mDbListeners.add(dbListener);
    }

    private void exibirTodosRequisitos() {
        if (!mDbListeners.isEmpty())
            removeDbListeners();

        loadRequisitosFromDb(mAdapter::updateData);
    }

    private void exibirApenasFavoritos() {
        if (!mDbListeners.isEmpty())
            removeDbListeners();

        loadRequisitosFromDb(requisitos -> loadFavoritadasFromDb(favoritadas -> {
            List<RequisitoPesquisa> reqsRemover = new ArrayList<>();

            for (RequisitoPesquisa req : requisitos) {
                boolean isFav = false;

                for (RequisicaoFavoritada reqFav : favoritadas) {
                    if (reqFav.getRequisicaoId().equals(req.getId_requisito_pesquisa())) {
                        isFav = true;
                        break;
                    }
                }

                if (!isFav)
                    reqsRemover.add(req);
            }

            requisitos.removeAll(reqsRemover);
            mAdapter.updateData(requisitos);
        }));
    }

    private void removeDbListeners() {
        for (ValueEventListener listener : mDbListeners)
            FirebaseDatabase.getInstance().getReference().removeEventListener(listener);

        mDbListeners.clear();
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RequisitoPesquisa requisitoPesquisa);
    }

    public interface OnEditarPesquisaListener {
        void onEditarPesquisa();
    }

    private interface OnRequisicoesCarregadasListener {
        void onRequisicoesCarregadas(List<RequisitoPesquisa> requisitos);
    }

    private interface OnFavoritosCarregadosListener {
        void onFavoritosCarregados(List<RequisicaoFavoritada> favoritadas);
    }
}
