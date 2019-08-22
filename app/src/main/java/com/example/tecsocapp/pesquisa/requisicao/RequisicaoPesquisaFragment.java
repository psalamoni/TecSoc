package com.example.tecsocapp.pesquisa.requisicao;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.RequisitoPesquisa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequisicaoPesquisaFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RequisicaoPesquisaRecyclerViewAdapter mAdapter;

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

            mAdapter = new RequisicaoPesquisaRecyclerViewAdapter(new ArrayList<>(), mListener);
            recyclerView.setAdapter(mAdapter);

            AddItemsFromDb();
        }

        return view;
    }

    private void AddItemsFromDb() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.child("requisitoPesquisa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RequisitoPesquisa> requisitos = new ArrayList<>();

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    requisitos.add(objSnapshot.getValue(RequisitoPesquisa.class));
                }

                mAdapter.updateData(requisitos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                alert("Falha ao buscar os requisitos de pesquisa");
            }
        });
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

    private void alert(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
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
