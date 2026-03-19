package com.mahyhaker.taskflow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mahyhaker.taskflow.entity.Task;
import com.mahyhaker.taskflow.entity.Usuario;
import com.mahyhaker.taskflow.enums.PrioridadeTask;
import com.mahyhaker.taskflow.enums.StatusTask;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUsuario(Usuario usuario, Pageable pageable);

    Page<Task> findByUsuarioAndStatus(Usuario usuario, StatusTask status, Pageable pageable);

    Page<Task> findByUsuarioAndPrioridade(Usuario usuario, PrioridadeTask prioridade, Pageable pageable);

    Page<Task> findByUsuarioAndTituloContainingIgnoreCase(Usuario usuario, String titulo, Pageable pageable);

    Page<Task> findByStatus(StatusTask status, Pageable pageable);

    Page<Task> findByPrioridade(PrioridadeTask prioridade, Pageable pageable);

    Page<Task> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}