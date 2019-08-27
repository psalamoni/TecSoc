package com.example.tecsocapp.pesquisaempresa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tecsocapp.EmpresaMain;
import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Pesquisa;

import java.util.List;

public class listPesquisaAdapter extends ArrayAdapter<Pesquisa> {

    Fragment context;
    private List<Pesquisa> pesquisaList;  // lista para armazenar os artitas

    public listPesquisaAdapter(Fragment context, List<Pesquisa> listPesquisa) {

        super(context.getContext(), R.layout.lista_pesquisa, listPesquisa);

        this.context = context;
        this.pesquisaList = listPesquisa;
    }

    @Override
    public int getCount() {
        return pesquisaList.size();
    }

    // método que é chamado para fornecer cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // criando um objeto "inflador"
        LayoutInflater inflater = context.getLayoutInflater();
        EmpresaMain empresa = new EmpresaMain();

        // usando o inflador para criar uma View a partir do arquivo de layout
        // que fizemos definindo os itens da lista
        View listViewItem = inflater.inflate(R.layout.lista_pesquisa, null, true);

        // pegando referências para as views que definimos dentro do item da lista,
        // isto é, os 2 textviews
        TextView textViewName = listViewItem.findViewById(R.id.txtNome);
        TextView textViewUni = listViewItem.findViewById(R.id.item_uni);
        TextView textViewArea = listViewItem.findViewById(R.id.item_area);
        TextView textViewGenre = listViewItem.findViewById(R.id.item_descricao);

        // a posição do artista na lista (armazenamento) é a mesma na lista (listview)
        // então usamos esse valor (position) para acessar o objeto "Artist" correto
        // dentro da lista artistList
        Pesquisa pesquisa = pesquisaList.get(position);

        // finalmente, colocamos os valores do objeto artista recuperado
        // nas views que formam nosso item da lista
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empresa.setIdviewpesquisa(pesquisa.getId_pesquisa());
                empresa.setViewPager(2);
            }
        });
        textViewName.setText(pesquisa.getTitulo());
        textViewGenre.setText(pesquisa.getDescricao());
        textViewUni.setText(pesquisa.getUniversidade());
        textViewArea.setText(pesquisa.getGrande_area());

        // a view está pronta! É só devolver para quem pediu
        return listViewItem;
    }


}