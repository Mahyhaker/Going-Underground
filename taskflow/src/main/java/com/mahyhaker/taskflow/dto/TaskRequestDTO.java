package com.mahyhaker.taskflow.dto;

import com.mahyhaker.taskflow.enums.PrioridadeTask;
import com.mahyhaker.taskflow.enums.StatusTask;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    private String descricao;

    @NotNull(message = "O status é obrigatório.")
    private StatusTask status;

    @NotNull(message = "A prioridade é obrigatória.")
    private PrioridadeTask prioridade;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String titulo, String descricao, StatusTask status, PrioridadeTask prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
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
}