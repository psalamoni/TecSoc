package com.example.tecsocapp.pesquisa;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment;

public class PesquisaMainActivity extends AppCompatActivity
        implements RequisicaoPesquisaFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_main);
    }

    @Override
    public void onListFragmentInteraction(RequisitoPesquisa requisitoPesquisa) {
        Log.d(this.getClass().getSimpleName(), "Intereção com item registrada: " + requisitoPesquisa);
    }
}
