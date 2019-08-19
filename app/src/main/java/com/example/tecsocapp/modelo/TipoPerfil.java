package com.example.tecsocapp.modelo;

public class TipoPerfil {
    private String UsuarioId;
    private String Perfil;

    public final static String PERFIL_EMPRESA = "Empresa";
    public final static String PERFIL_PESQUISA = "Pesquisa";

    public String getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }
}
