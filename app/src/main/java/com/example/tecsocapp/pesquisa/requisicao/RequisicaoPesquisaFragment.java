package com.example.tecsocapp.pesquisa.requisicao;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;

import java.util.ArrayList;
import java.util.List;

public class RequisicaoPesquisaFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequisicaoPesquisaFragment() {
    }

    @SuppressWarnings("unused")
    public static RequisicaoPesquisaFragment newInstance(int columnCount) {
        RequisicaoPesquisaFragment fragment = new RequisicaoPesquisaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

            List<RequisitoPesquisa> requisitos = new ArrayList<>(); //TODO: Buscar requisitos no BD

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new RequisicaoPesquisaRecyclerViewAdapter(requisitos, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RequisitoPesquisa requisitoPesquisa);
    }
}
