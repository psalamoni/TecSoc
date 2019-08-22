package com.example.tecsocapp.modelo;

public class RequisitoPesquisa {
    private String nome;
    private String area;
    private String descricao;
    private String valor;
    private String obsAdicional;
    private String id_requisito_pesquisa;
    private String id_usuario;
    private String nomeRequisitante;

    public String getId_requisito_pesquisa() {
        return id_requisito_pesquisa;
    }

    public void setId_requisito_pesquisa(String id_requisito_pesquisa) {
        this.id_requisito_pesquisa = id_requisito_pesquisa;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public RequisitoPesquisa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getObsAdicional() {
        return obsAdicional;
    }

    public void setObsAdicional(String obsAdicional) {
        this.obsAdicional = obsAdicional;
    }

    public String getNomeRequisitante() {
        return nomeRequisitante;
    }

    public void setNomeRequisitante(String nomeRequisitante) {
        this.nomeRequisitante = nomeRequisitante;
    }

    @Override
    public String toString() {
        return "RequisitoPesquisa{" +
                "nome='" + nome + '\'' +
                ", id_requisito_pesquisa='" + id_requisito_pesquisa + '\'' +
                '}';
    }
}

