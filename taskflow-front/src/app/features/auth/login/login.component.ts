import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toast: ToastService,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required]]
    });
  }

  entrar(): void {
    if (this.form.invalid) {
      this.toast.show('Preencha os campos corretamente', 'error');
      return;
    }

    this.authService.login(this.form.value).subscribe({
      next: (response: any) => {
        this.authService.salvarToken(response.token);
        this.toast.show('Login realizado com sucesso!');
        this.router.navigate(['/tasks']);
      },
      error: () => {
        this.toast.show('Email ou senha inválidos', 'error');
      }
    });
  }
}
