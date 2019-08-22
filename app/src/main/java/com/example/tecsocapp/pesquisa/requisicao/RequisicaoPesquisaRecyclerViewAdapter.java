package com.example.tecsocapp.pesquisa.requisicao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.example.tecsocapp.pesquisa.requisicao.RequisicaoPesquisaFragment.OnListFragmentInteractionListener;

import java.util.List;

public class RequisicaoPesquisaRecyclerViewAdapter extends RecyclerView.Adapter<RequisicaoPesquisaRecyclerViewAdapter.ViewHolder> {

    private final List<RequisitoPesquisa> mValues;
    private final OnListFragmentInteractionListener mListener;

    RequisicaoPesquisaRecyclerViewAdapter(List<RequisitoPesquisa> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_requisicaopesquisa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setFields(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    void updateData(List<RequisitoPesquisa> requisitos) {
        mValues.clear();
        mValues.addAll(requisitos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNomeView;
        final TextView mAreaView;
        final TextView mValorView;
        final TextView mDescricaoView;
        final TextView mEmpresaView;
        RequisitoPesquisa mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNomeView = view.findViewById(R.id.item_nome);
            mAreaView = view.findViewById(R.id.item_area);
            mValorView = view.findViewById(R.id.item_valor);
            mDescricaoView = view.findViewById(R.id.item_descricao);
            mEmpresaView = view.findViewById(R.id.item_empresa);
        }

        void setFields(RequisitoPesquisa requisito) {
            mItem = requisito;
            mNomeView.setText(requisito.getNome());
            mAreaView.setText(requisito.getArea());
            mValorView.setText(String.format("R$ %s,00", requisito.getValor()));
            mDescricaoView.setText(requisito.getDescricao());
            mEmpresaView.setText(requisito.getNomeRequisitante());
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
