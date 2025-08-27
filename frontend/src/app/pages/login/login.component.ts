import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';

declare var bootstrap: any

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  correo = '';
  contrasena = '';
  error = '';

  registro = {
  nombre: '',
  apellido: '',
  correo: '',
  telefono: '',
  contrasena: ''
  };

  registrarUsuario(form: NgForm) {
    if (form.invalid) return;

    this.http.post('http://localhost:8080/api/usuarios', this.registro).subscribe({
      next: () => {
        alert('Usuario registrado correctamente');
        form.resetForm();
        const modal = bootstrap.Modal.getInstance(document.getElementById('registroModal')!);
        modal?.hide();
      },
      error: err => {
        console.error(err);
        alert('Error al registrar el usuario');
      }
    });
  }

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    this.http.post<{ token: string}>('http://localhost:8080/api/auth/login', {
      correo: this.correo,
      contrasena: this.contrasena
    }).subscribe({
      next: res => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['../perfil']);
      },
      error: err => {
        this.error = 'Correo o contraseña incorrectos', err;
      }
    });
  }
  
}
