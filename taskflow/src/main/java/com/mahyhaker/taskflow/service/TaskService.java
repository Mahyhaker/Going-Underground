package com.mahyhaker.taskflow.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mahyhaker.taskflow.dto.TaskRequestDTO;
import com.mahyhaker.taskflow.dto.TaskResponseDTO;
import com.mahyhaker.taskflow.entity.Task;
import com.mahyhaker.taskflow.entity.Usuario;
import com.mahyhaker.taskflow.enums.PrioridadeTask;
import com.mahyhaker.taskflow.enums.RoleUsuario;
import com.mahyhaker.taskflow.enums.StatusTask;
import com.mahyhaker.taskflow.exception.RecursoNaoEncontradoException;
import com.mahyhaker.taskflow.repository.TaskRepository;
import com.mahyhaker.taskflow.repository.UsuarioRepository;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final UsuarioRepository usuarioRepository;

    public TaskService(TaskRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<TaskResponseDTO> listarTodas(String emailUsuario, int page, int size, String sortBy, String direction) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        if (usuario.getRole() == RoleUsuario.ROLE_ADMIN) {
            return repository.findAll(pageable).map(this::converterParaResponseDTO);
        }

        return repository.findByUsuario(usuario, pageable).map(this::converterParaResponseDTO);
    }

    public TaskResponseDTO criar(String emailUsuario, TaskRequestDTO dto) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);

        Task task = new Task();
        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setStatus(dto.getStatus());
        task.setPrioridade(dto.getPrioridade());
        task.setUsuario(usuario);

        Task taskSalva = repository.save(task);
        return converterParaResponseDTO(taskSalva);
    }

    public TaskResponseDTO buscarPorId(String emailUsuario, Long id) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);

        Task task = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa com id " + id + " não encontrada."));

        if (usuario.getRole() != RoleUsuario.ROLE_ADMIN && !task.getUsuario().getId().equals(usuario.getId())) {
            throw new RecursoNaoEncontradoException("Tarefa com id " + id + " não encontrada.");
        }

        return converterParaResponseDTO(task);
    }

    public TaskResponseDTO atualizar(String emailUsuario, Long id, TaskRequestDTO dto) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);

        Task task = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa com id " + id + " não encontrada."));

        if (usuario.getRole() != RoleUsuario.ROLE_ADMIN && !task.getUsuario().getId().equals(usuario.getId())) {
            throw new RecursoNaoEncontradoException("Tarefa com id " + id + " não encontrada.");
        }

        task.setTitulo(dto.getTitulo());
        task.setDescricao(dto.getDescricao());
        task.setStatus(dto.getStatus());
        task.setPrioridade(dto.getPrioridade());

        Task taskAtualizada = repository.save(task);
        return converterParaResponseDTO(taskAtualizada);
    }

    public void deletar(String emailUsuario, Long id) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);

        Task task = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tarefa com id " + id + " não encontrada."));

        if (usuario.getRole() != RoleUsuario.ROLE_ADMIN && !task.getUsuario().getId().equals(usuario.getId())) {
            throw new RecursoNaoEncontradoException("Tarefa com id " + id + " não encontrada.");
        }

        repository.delete(task);
    }

    public Page<TaskResponseDTO> buscarPorStatus(String emailUsuario, StatusTask status, int page, int size, String sortBy, String direction) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        if (usuario.getRole() == RoleUsuario.ROLE_ADMIN) {
            return repository.findByStatus(status, pageable).map(this::converterParaResponseDTO);
        }

        return repository.findByUsuarioAndStatus(usuario, status, pageable).map(this::converterParaResponseDTO);
    }

    public Page<TaskResponseDTO> buscarPorPrioridade(String emailUsuario, PrioridadeTask prioridade, int page, int size, String sortBy, String direction) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        if (usuario.getRole() == RoleUsuario.ROLE_ADMIN) {
            return repository.findByPrioridade(prioridade, pageable).map(this::converterParaResponseDTO);
        }

        return repository.findByUsuarioAndPrioridade(usuario, prioridade, pageable).map(this::converterParaResponseDTO);
    }

    public Page<TaskResponseDTO> buscarPorTitulo(String emailUsuario, String titulo, int page, int size, String sortBy, String direction) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));

        if (usuario.getRole() == RoleUsuario.ROLE_ADMIN) {
            return repository.findByTituloContainingIgnoreCase(titulo, pageable).map(this::converterParaResponseDTO);
        }

        return repository.findByUsuarioAndTituloContainingIgnoreCase(usuario, titulo, pageable)
                .map(this::converterParaResponseDTO);
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
    }

    private TaskResponseDTO converterParaResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitulo(),
                task.getDescricao(),
                task.getStatus(),
                task.getPrioridade(),
                task.getDataCriacao(),
                task.getDataAtualizacao(),
                task.getUsuario().getEmail()
        );
    }
}