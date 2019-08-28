package com.example.tecsocapp.pesquisa;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.example.tecsocapp.pesquisa.perfil.PerfilPesquisaFragment;
import com.example.tecsocapp.pesquisa.requisicao.RequisicaoDetalhesFragment;
import com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment;

import java.util.Objects;

import static com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment.OnEditarPesquisaListener;
import static com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment.OnListFragmentInteractionListener;
import static com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment.newInstance;

public class PesquisaMainActivity extends AppCompatActivity
        implements OnListFragmentInteractionListener, OnEditarPesquisaListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_main);

        ActionBar actionBar = Objects.requireNonNull(this.getSupportActionBar());
        actionBar.setTitle(actionBar.getTitle() + " - Pesquisa");

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            RequisicaoPesquisaFragment requisicaoListaFragment = newInstance();
            requisicaoListaFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, requisicaoListaFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(RequisitoPesquisa requisitoPesquisa) {
        RequisicaoDetalhesFragment newFragment = RequisicaoDetalhesFragment.newInstance(requisitoPesquisa.getId_requisito_pesquisa(),
                requisitoPesquisa.getId_usuario());

        changeFragment(newFragment);
    }

    @Override
    public void onEditarPesquisa() {
        PerfilPesquisaFragment frag = PerfilPesquisaFragment.newInstance();
        changeFragment(frag);
    }

    private void changeFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
