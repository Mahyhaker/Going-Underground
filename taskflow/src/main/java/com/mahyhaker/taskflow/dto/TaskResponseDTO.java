package com.mahyhaker.taskflow.dto;

import java.time.LocalDateTime;

import com.mahyhaker.taskflow.enums.PrioridadeTask;
import com.mahyhaker.taskflow.enums.StatusTask;

public class TaskResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private StatusTask status;
    private PrioridadeTask prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String emailUsuario;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(Long id, String titulo, String descricao, StatusTask status,
                           PrioridadeTask prioridade, LocalDateTime dataCriacao,
                           LocalDateTime dataAtualizacao, String emailUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.emailUsuario = emailUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public PrioridadeTask getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeTask prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}