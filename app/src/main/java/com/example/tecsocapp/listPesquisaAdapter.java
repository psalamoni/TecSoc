package com.example.tecsocapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Pesquisa;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class listPesquisaAdapter extends ArrayAdapter<Pesquisa> {

    private Activity context;
    private List<Pesquisa> pesquisaList;  // lista para armazenar os artitas
    private int[]mImages = new int[]{
            R.drawable.agro,R.drawable.quimica,R.drawable.fisica,R.drawable.universia
    };
    private String[ ]MImageTitle = new String[]{
            "Agronomia","Quimica","Fisica","Universidade"
    };
    public listPesquisaAdapter(Activity context, List<Pesquisa> listPesquisa) {

        super(context, R.layout.lista_pesquisa, listPesquisa);

        this.context = context;
        this.pesquisaList = listPesquisa;
    }


    // método que é chamado para fornecer cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        // criando um objeto "inflador"
        LayoutInflater inflater = context.getLayoutInflater();

        // usando o inflador para criar uma View a partir do arquivo de layout
        // que fizemos definindo os itens da lista
        View listViewItem = inflater.inflate(R.layout.lista_pesquisa, null, true);

        // pegando referências para as views que definimos dentro do item da lista,
        // isto é, os 2 textviews
        TextView textViewName = listViewItem.findViewById(R.id.txtNome);
        TextView textViewGenre = listViewItem.findViewById(R.id.txtNome);

        // a posição do artista na lista (armazenamento) é a mesma na lista (listview)
        // então usamos esse valor (position) para acessar o objeto "Artist" correto
        // dentro da lista artistList
        Pesquisa pesquisa = pesquisaList.get(position);

        // finalmente, colocamos os valores do objeto artista recuperado
        // nas views que formam nosso item da lista
        textViewName.setText(pesquisa.getTitulo());
        textViewGenre.setText(pesquisa.getDescricao());

        // a view está pronta! É só devolver para quem pediu
        return listViewItem;
    }
}