import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toast: ToastService,
    private router: Router
  ) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      senha: ['', Validators.required]
    });
  }

  registrar(): void {
    if (this.form.invalid) {
      this.toast.show('Preencha os campos corretamente', 'error');
      return;
    }

    this.authService.register(this.form.value).subscribe({
      next: () => {
        this.toast.show('Conta criada com sucesso!');
        this.router.navigate(['/login']);
      },
      error: () => {
        this.toast.show('Erro ao criar conta', 'error');
      }
    });
  }
}
