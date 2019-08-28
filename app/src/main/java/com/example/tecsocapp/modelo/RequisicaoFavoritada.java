package com.example.tecsocapp.modelo;

public class RequisicaoFavoritada {
    private String Id;

    private String usuarioId;
    private String requisicaoId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getRequisicaoId() {
        return requisicaoId;
    }

    public void setRequisicaoId(String requisicaoId) {
        this.requisicaoId = requisicaoId;
    }
}
