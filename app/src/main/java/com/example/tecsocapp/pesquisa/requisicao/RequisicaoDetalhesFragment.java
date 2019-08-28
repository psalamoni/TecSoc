package com.example.tecsocapp.pesquisa.requisicao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tecsocapp.Login;
import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Empresa;
import com.example.tecsocapp.modelo.RequisicaoFavoritada;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RequisicaoDetalhesFragment extends Fragment {
    private String mIdRequisicao;
    private String mIdUsuarioCriadorReq;

    private TextView mReqNomeView;
    private TextView mReqAreaView;
    private TextView mReqDescricaoView;
    private TextView mReqValorView;
    private TextView mReqObsAdicionalView;

    private TextView mEmpRazaoSocialView;
    private TextView mEmpTelefoneView;
    private TextView mEmpEnderecoView;
    private TextView mEmpRepresentanteView;
    private TextView mEmpAreaAtuacaoView;

    private ShareActionProvider mShareActionProvider;
    private String mFavoritadoId = null;

    public static RequisicaoDetalhesFragment newInstance(String requisicaoId, String usuarioCriadorReqId) {
        RequisicaoDetalhesFragment fragment = new RequisicaoDetalhesFragment();
        Bundle args = new Bundle();
        args.putString("requisicaoId", requisicaoId);
        args.putString("usuarioCriadorReqId", usuarioCriadorReqId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIdRequisicao = getArguments().getString("requisicaoId");
            mIdUsuarioCriadorReq = getArguments().getString("usuarioCriadorReqId");
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.requisicao_detalhes_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RequisicaoDetalhesViewModel mViewModel = ViewModelProviders.of(this).get(RequisicaoDetalhesViewModel.class);
        mViewModel.setIdRequisicao(mIdRequisicao);
        mViewModel.setIdUsuarioCriadorReq(mIdUsuarioCriadorReq);

        mViewModel.getRequisitoPesquisa().observe(this, this::setTextViewsRequisito);
        mViewModel.getEmpresa().observe(this, this::setTextViewsEmpresa);

        addFavoritoDbListener();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detalhes_requisicao, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_compartilhar);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if (mFavoritadoId != null) {
            menu.findItem(R.id.menu_favoritado).setVisible(true);
            menu.findItem(R.id.menu_favoritar).setVisible(false);
        } else {
            menu.findItem(R.id.menu_favoritado).setVisible(false);
            menu.findItem(R.id.menu_favoritar).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favoritar:
                favoritarAtual();
                return true;

            case R.id.menu_favoritado:
                desfavoritarAtual();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeViews(View view) {
        mReqNomeView = view.findViewById(R.id.item_nome);
        mReqAreaView = view.findViewById(R.id.item_area);
        mReqValorView = view.findViewById(R.id.item_valor);
        mReqDescricaoView = view.findViewById(R.id.item_descricao);
        mReqObsAdicionalView = view.findViewById(R.id.item_observacao);

        mEmpRazaoSocialView = view.findViewById(R.id.item_nomeEmpresa);
        mEmpAreaAtuacaoView = view.findViewById(R.id.item_areaEmpresa);
        mEmpEnderecoView = view.findViewById(R.id.item_enderecoEmpresa);
        mEmpRepresentanteView = view.findViewById(R.id.item_representanteEmpresa);
        mEmpTelefoneView = view.findViewById(R.id.item_telefoneEmpresa);
    }

    private void setTextViewsRequisito(RequisitoPesquisa requisito) {
        mReqNomeView.setText(requisito.getNome());
        mReqAreaView.setText(requisito.getArea());
        mReqDescricaoView.setText(requisito.getDescricao());
        mReqValorView.setText(String.format("R$ %s,00", requisito.getValor()));
        mReqObsAdicionalView.setText(requisito.getObsAdicional());
    }

    private void setTextViewsEmpresa(Empresa empresa) {
        mEmpRazaoSocialView.setText(empresa.getRazaoSocial() != null ? empresa.getRazaoSocial() : "-- Desconhecido --");
        mEmpTelefoneView.setText(empresa.getTelefone());
        mEmpEnderecoView.setText(empresa.getEndereco());
        mEmpRepresentanteView.setText(empresa.getRepresentante());
        mEmpAreaAtuacaoView.setText(empresa.getArea_atuacao());

        setMenuCompartilhar();
    }

    private void setMenuCompartilhar() {
        String texto = "Saca só essa requisição maneira!" +
                "\n\nNome: " + mReqNomeView.getText() +
                "\nDescrição: " + mReqDescricaoView.getText() +
                "\nValor: " + mReqValorView.getText() +
                "\nEmpresa: " + mEmpRazaoSocialView.getText() +
                "";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, texto);

        mShareActionProvider.setShareIntent(shareIntent);
    }

    private void alert(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void favoritarAtual() {
        Log.d("Favorito", "Favoritar item atual");

        RequisicaoFavoritada reqFav = new RequisicaoFavoritada();
        reqFav.setRequisicaoId(mIdRequisicao);
        reqFav.setUsuarioId(Login.sUsuarioId);
        reqFav.setId(Login.sUsuarioId + "_" + mIdRequisicao);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("requisicaoFavoritada").child(reqFav.getId()).setValue(reqFav);
    }

    private void desfavoritarAtual() {
        Log.d("Favorito", "Desfavoritar item atual");

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("requisicaoFavoritada").child(mFavoritadoId).setValue(null);
    }

    private void addFavoritoDbListener() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("requisicaoFavoritada")
                .orderByKey()
                .equalTo(Login.sUsuarioId + "_" + mIdRequisicao)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
                        mFavoritadoId = dataSnapshot.getKey();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        //NOOP
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
                        mFavoritadoId = null;
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        //NOOP
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(this.getClass().getSimpleName(), "Falha ao buscar informação da requisição favoritada");
                    }
                });
    }
}
