package com.example.tecsocapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tecsocapp.modelo.Empresa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;


public class PerfilEmpresa extends Fragment {

    View PerfilView;
    private TextView razaoSocial;
    private TextView cnpj;
    private TextView editEmail;
    private TextView endereco;
    private TextView representante;
    private FirebaseAuth auth;
    EmpresaMain empresamain = new EmpresaMain();
    DatabaseReference db = empresamain.inicializarFirebase();
    private TextView spinner;

    public PerfilEmpresa() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void inicializaComponentes() {
        razaoSocial = PerfilView.findViewById(R.id.cadastro_empresa_razao_social);
        cnpj = PerfilView.findViewById(R.id.cadastro_empresa_cnpj);
        editEmail = PerfilView.findViewById(R.id.cadastroEmail);
        endereco = PerfilView.findViewById(R.id.cadastro_empresa_endereco);
        representante = PerfilView.findViewById(R.id.cadastro_empresa_representante);
        spinner = PerfilView.findViewById(R.id.area_atuacao_empresa);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        PerfilView = inflater.inflate(R.layout.fragment_perfil_empresa, container, false);
        inicializaComponentes();

        auth = Conexao.getFirebaseAuth();
        AddItemsFromDb();

        // Inflate the layout for this fragment*/
        return PerfilView;
    }

    public void requisito_pesquisa(View view){

    }

    private void AddItemsFromDb() {

        String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        db.child("empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Empresa empresa = objSnapshot.getValue(Empresa.class);
                    if (empresa.getId_usuario().equals(id)) {
                        razaoSocial.setHint(empresa.getRazaoSocial());
                        cnpj.setHint(empresa.getCnpj());
                        editEmail.setHint(auth.getCurrentUser().getEmail());
                        endereco.setHint(empresa.getEndereco());
                        representante.setHint(empresa.getRepresentante());
                        spinner.setHint(empresa.getArea_atuacao());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
