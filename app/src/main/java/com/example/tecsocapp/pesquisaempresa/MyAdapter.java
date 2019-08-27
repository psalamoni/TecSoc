package com.example.tecsocapp.pesquisaempresa;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tecsocapp.DescricaoPesquisasEmpresa;
import com.example.tecsocapp.EmpresaMain;
import com.example.tecsocapp.PerfilEmpresa;

public class MyAdapter extends FragmentPagerAdapter {

    EmpresaMain empresa = new EmpresaMain();

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = 3;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PesquisasEmpresa pesquisas = new PesquisasEmpresa();
                return pesquisas;
            case 1:
                PerfilEmpresa perfilempresa = new PerfilEmpresa();
                return perfilempresa;
            case 2:
                DescricaoPesquisasEmpresa descricaopesquisaempresa = new DescricaoPesquisasEmpresa();
                return descricaopesquisaempresa;
            default:
                PesquisasEmpresa another = new PesquisasEmpresa();
                return another;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}