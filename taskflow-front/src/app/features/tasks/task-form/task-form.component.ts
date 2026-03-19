import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TaskService } from '../../../core/services/task.service';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.css'
})
export class TaskFormComponent implements OnInit {
  taskId: number | null = null;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private toast: ToastService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      titulo: ['', [Validators.required]],
      descricao: [''],
      status: ['PENDENTE', [Validators.required]],
      prioridade: ['ALTA', [Validators.required]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.taskId = Number(id);
      this.taskService.buscarPorId(this.taskId).subscribe({
        next: (task: any) => {
          this.form.patchValue({
            titulo: task.titulo,
            descricao: task.descricao,
            status: task.status,
            prioridade: task.prioridade
          });
        },
        error: () => {
          this.toast.show('Erro ao carregar task', 'error');
          this.router.navigate(['/tasks']);
        }
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.toast.show('Preencha os campos obrigatórios', 'error');
      return;
    }

    const dados = this.form.value;

    if (this.taskId) {
      this.taskService.atualizar(this.taskId, dados).subscribe({
        next: () => {
          this.toast.show('Task atualizada com sucesso!');
          this.router.navigate(['/tasks']);
        },
        error: () => {
          this.toast.show('Erro ao atualizar task', 'error');
        }
      });
    } else {
      this.taskService.criar(dados).subscribe({
        next: () => {
          this.toast.show('Task criada com sucesso!');
          this.router.navigate(['/tasks']);
        },
        error: () => {
          this.toast.show('Erro ao criar task', 'error');
        }
      });
    }
  }
}
