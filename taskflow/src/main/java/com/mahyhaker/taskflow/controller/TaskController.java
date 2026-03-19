package com.mahyhaker.taskflow.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahyhaker.taskflow.dto.TaskRequestDTO;
import com.mahyhaker.taskflow.dto.TaskResponseDTO;
import com.mahyhaker.taskflow.enums.PrioridadeTask;
import com.mahyhaker.taskflow.enums.StatusTask;
import com.mahyhaker.taskflow.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> listar(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(service.listarTodas(authentication.getName(), page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> buscarPorId(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(authentication.getName(), id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<TaskResponseDTO>> buscarPorStatus(
            Authentication authentication,
            @PathVariable StatusTask status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(service.buscarPorStatus(authentication.getName(), status, page, size, sortBy, direction));
    }

    @GetMapping("/prioridade/{prioridade}")
    public ResponseEntity<Page<TaskResponseDTO>> buscarPorPrioridade(
            Authentication authentication,
            @PathVariable PrioridadeTask prioridade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(service.buscarPorPrioridade(authentication.getName(), prioridade, page, size, sortBy, direction));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<TaskResponseDTO>> buscarPorTitulo(
            Authentication authentication,
            @RequestParam String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(service.buscarPorTitulo(authentication.getName(), titulo, page, size, sortBy, direction));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> criar(Authentication authentication, @Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(authentication.getName(), dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> atualizar(Authentication authentication, @PathVariable Long id, @Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(authentication.getName(), id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(Authentication authentication, @PathVariable Long id) {
        service.deletar(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }
}