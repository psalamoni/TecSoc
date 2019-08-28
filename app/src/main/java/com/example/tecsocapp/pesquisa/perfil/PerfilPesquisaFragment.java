package com.example.tecsocapp.pesquisa.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tecsocapp.Login;
import com.example.tecsocapp.R;
import com.example.tecsocapp.modelo.Pesquisa;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilPesquisaFragment extends Fragment {
    private TextInputEditText titulo, grande_area, descricao, universidade, instituto,
            professor, contato, pequena_area;
    private Button btnRegistrar;

    private DatabaseReference db;
    private PerfilPesquisaViewModel viewModel;

    public static PerfilPesquisaFragment newInstance() {
        return new PerfilPesquisaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.perfil_pesquisa_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializaComponentes(view);
        eventoClicks();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();

        viewModel = ViewModelProviders.of(this).get(PerfilPesquisaViewModel.class);
        viewModel.setUsuarioId(Login.sUsuarioId);
        viewModel.getPesquisa().observe(this, this::setViewsPesquisa);
    }

    private void setViewsPesquisa(Pesquisa pesquisa) {
        titulo.setText(pesquisa.getTitulo());
        grande_area.setText(pesquisa.getGrande_area());
        pequena_area.setText(pesquisa.getPequena_area());
        descricao.setText(pesquisa.getDescricao());
        universidade.setText(pesquisa.getUniversidade());
        instituto.setText(pesquisa.getInstituto());
        professor.setText(pesquisa.getProfessor());
        contato.setText(pesquisa.getContatos());
    }

    private void eventoClicks() {
        btnRegistrar.setOnClickListener(view -> {
            Pesquisa pesquisaOriginal = viewModel.getPesquisa().getValue();

            Pesquisa pesquisaAlterada = new Pesquisa();
            pesquisaAlterada.setTitulo(titulo.getText().toString());
            pesquisaAlterada.setGrande_area(grande_area.getText().toString());
            pesquisaAlterada.setPequena_area(pequena_area.getText().toString());
            pesquisaAlterada.setDescricao(descricao.getText().toString());
            pesquisaAlterada.setUniversidade(universidade.getText().toString());
            pesquisaAlterada.setInstituto(instituto.getText().toString());
            pesquisaAlterada.setProfessor(professor.getText().toString());
            pesquisaAlterada.setContatos(contato.getText().toString());

            pesquisaAlterada.setId_pesquisa(pesquisaOriginal.getId_pesquisa());
            pesquisaAlterada.setId_usuario(pesquisaOriginal.getId_usuario());

            // titulo,grande_area,descricao,email,universidade,instituicao,professor,contato,senha
            db.child("pesquisa").child(pesquisaAlterada.getId_pesquisa()).setValue(pesquisaAlterada);

            alert("Pesquisa salva com sucesso!");
        });
    }

    private void inicializaComponentes(View view) {
        titulo = view.findViewById(R.id.cadastro_pesquisa_titulo);
        grande_area = view.findViewById(R.id.cadastro_pesquisa_grande_area);
        descricao = view.findViewById(R.id.cadastro_pesquisa_descricao);
        universidade = view.findViewById(R.id.cadastro_pesquisa_universidade);
        instituto = view.findViewById(R.id.cadastro_pesquisa_instituto);
        professor = view.findViewById(R.id.cadastro_pesquisa_professor);
        contato = view.findViewById(R.id.cadastro_pesquisa_contato);
        pequena_area = view.findViewById(R.id.cadastro_pesquisa_pequena_area);
        btnRegistrar = view.findViewById(R.id.btn_cadastro_pesquisa_salvar);
    }

    private boolean isCamposPreenchidos() {
        boolean preenchidos = mostrarObrigatorioSeNaoPreenchido(titulo);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(grande_area);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(descricao);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(universidade);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(instituto);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(professor);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(contato);
        preenchidos &= mostrarObrigatorioSeNaoPreenchido(pequena_area);

        return preenchidos;
    }

    private boolean mostrarObrigatorioSeNaoPreenchido(TextInputEditText editText) {
        boolean erroExibido = false;

        if (editText.getText().toString().isEmpty()) {
            ((TextInputLayout) editText.getParent().getParent()).setError("Campo obrigatório");
            erroExibido = true;
        } else {
            ((TextInputLayout) editText.getParent().getParent()).setError(null);
        }

        return !erroExibido;
    }

    private void alert(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
