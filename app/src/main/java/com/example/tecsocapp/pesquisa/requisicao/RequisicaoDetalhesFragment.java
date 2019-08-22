package com.example.tecsocapp.pesquisa.requisicao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Empresa;
import com.example.tecsocapp.modelo.RequisitoPesquisa;

public class RequisicaoDetalhesFragment extends Fragment {
    private String mIdRequisicao;
    private String mIdUsuario;

    private RequisicaoDetalhesViewModel mViewModel;

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

    public static RequisicaoDetalhesFragment newInstance(String requisicaoId, String usuarioId) {
        RequisicaoDetalhesFragment fragment = new RequisicaoDetalhesFragment();
        Bundle args = new Bundle();
        args.putString("requisicaoId", requisicaoId);
        args.putString("usuarioId", usuarioId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIdRequisicao = getArguments().getString("requisicaoId");
            mIdUsuario = getArguments().getString("usuarioId");
        }
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
        mViewModel = ViewModelProviders.of(this).get(RequisicaoDetalhesViewModel.class);
        mViewModel.setIdRequisicao(mIdRequisicao);
        mViewModel.setIdUsuario(mIdUsuario);

        mViewModel.getRequisitoPesquisa().observe(this, this::setTextViewsRequisito);
        mViewModel.getEmpresa().observe(this, this::setTextViewsEmpresa);
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
    }

    private void alert(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
