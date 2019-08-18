package com.example.tecsocapp.modelo;

public class Pesquisa {
    private String titulo;
    private String grande_area;
    private String pequena_area;
    private String descricao;
    private String universidade;
    private String instituto;
    private String id_pesquisa;
    private String id_usuario;
    private String professor;
    private String contatos;

    public Pesquisa() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGrande_area() {
        return grande_area;
    }

    public void setGrande_area(String grande_area) {
        this.grande_area = grande_area;
    }

    public String getPequena_area() {
        return pequena_area;
    }

    public void setPequena_area(String pequena_area) {
        this.pequena_area = pequena_area;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUniversidade() {
        return universidade;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public String getInstituto() {
        return instituto;
    }

    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }

    public String getId_pesquisa() {
        return id_pesquisa;
    }

    public void setId_pesquisa(String id_pesquisa) {
        this.id_pesquisa = id_pesquisa;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getContatos() {
        return contatos;
    }

    public void setContatos(String contatos) {
        this.contatos = contatos;
    }


    //Título, grande área, pequena área, descrição, universidade, instituto, professor responsável, contatos, pesquisadores

}
