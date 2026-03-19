import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../../core/services/task.service';
import { AuthService } from '../../../core/services/auth.service';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit {
  tasks: any[] = [];
  tituloBusca = '';
  statusFiltro = '';
  prioridadeFiltro = '';

  constructor(
    private taskService: TaskService,
    private authService: AuthService,
    private toast: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarTasks();
  }

  carregarTasks(): void {
    this.taskService.listar().subscribe({
      next: (response: any) => {
        this.tasks = response.content;
      },
      error: () => {
        this.toast.show('Erro ao carregar tasks', 'error');
      }
    });
  }

  buscar(): void {
    if (!this.tituloBusca.trim()) {
      this.carregarTasks();
      return;
    }

    this.taskService.buscarPorTitulo(this.tituloBusca).subscribe({
      next: (response: any) => {
        this.tasks = response.content;
      },
      error: () => {
        this.toast.show('Erro ao buscar tasks', 'error');
      }
    });
  }

  filtrar(): void {
    this.taskService.listar().subscribe({
      next: (response: any) => {
        let lista = response.content;

        if (this.statusFiltro) {
          lista = lista.filter((t: any) => t.status === this.statusFiltro);
        }

        if (this.prioridadeFiltro) {
          lista = lista.filter((t: any) => t.prioridade === this.prioridadeFiltro);
        }

        this.tasks = lista;
      },
      error: () => {
        this.toast.show('Erro ao filtrar tasks', 'error');
      }
    });
  }

  limparFiltros(): void {
    this.statusFiltro = '';
    this.prioridadeFiltro = '';
    this.tituloBusca = '';
    this.carregarTasks();
  }

  deletar(id: number): void {
    const confirmado = window.confirm('Tem certeza que deseja excluir esta task?');

    if (!confirmado) {
      return;
    }

    this.taskService.deletar(id).subscribe({
      next: () => {
        this.toast.show('Task excluída com sucesso');
        this.carregarTasks();
      },
      error: () => {
        this.toast.show('Erro ao excluir task', 'error');
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
