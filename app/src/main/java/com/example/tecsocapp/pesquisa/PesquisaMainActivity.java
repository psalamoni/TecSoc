package com.example.tecsocapp.pesquisa;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.example.tecsocapp.pesquisa.requisicao.RequisicaoDetalhesFragment;
import com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment;

public class PesquisaMainActivity extends AppCompatActivity
        implements RequisicaoPesquisaFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            RequisicaoPesquisaFragment requisicaoListaFragment = RequisicaoPesquisaFragment.newInstance();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            requisicaoListaFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, requisicaoListaFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(RequisitoPesquisa requisitoPesquisa) {
        Log.d(this.getClass().getSimpleName(), "Intereção com item registrada: " + requisitoPesquisa);

        // Create fragment and give it an argument specifying the article it should show
        RequisicaoDetalhesFragment newFragment = RequisicaoDetalhesFragment.newInstance(requisitoPesquisa.getId_requisito_pesquisa(), requisitoPesquisa.getId_usuario());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
