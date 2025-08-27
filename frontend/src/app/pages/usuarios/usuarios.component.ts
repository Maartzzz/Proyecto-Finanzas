import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { UsuarioService } from '../../services/usuario.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule} from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { HttpClient } from '@angular/common/http';

declare var bootstrap: any
@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [
    FormsModule, 
    CommonModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatListModule
  ],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent implements OnInit{

  usuarios: Usuario[] = [];
  nuevo: Usuario = {
      nombre: '',
      apellido:'',
      correo:'',
      telefono: '',
      contrasena:''
  };

  usuarioEditado: Usuario = {
  id: undefined,
  nombre: '',
  apellido: '',
  correo: '',
  telefono: '',
  contrasena: ''
  };

  abrirModal(usuario: Usuario) {
  // Copia el usuario seleccionado para no alterar el original hasta confirmar
  this.usuarioEditado = { ...usuario };
  }

  constructor(private usuarioService: UsuarioService, private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.usuarioService.getUsuarios().subscribe(data => this.usuarios = data);
  }

  agregarUsuario(form: NgForm) {

    if (form.invalid) {
      return;
    }

    this.usuarioService.registrar(this.nuevo).subscribe(() => {
      this.cargarUsuarios();
      form.resetForm();
    });
  }

  eliminarUsuario(id: number): void {
    console.log('ID a eliminar:', id);
  if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
    this.http.delete(`http://localhost:8080/api/usuarios/${id}`)
      .subscribe({
        next: () => {
          this.usuarios = this.usuarios.filter(usuario => usuario.id !== id);
        },
        error: err => {
          console.error('Error al eliminar:', err);
          alert('No se pudo eliminar el usuario.');
        }
      });
  }
  }

  actualizarUsuario() {
  if (!this.usuarioEditado.id) return;

  this.usuarioService.actualizarUsuario(this.usuarioEditado.id, this.usuarioEditado).subscribe(() => {
    this.cargarUsuarios();

    // Cerrar el modal manualmente
    const modal = document.getElementById('editarModal');
    if (modal) {
      const modalInstance = bootstrap.Modal.getInstance(modal);
      modalInstance?.hide();
    }
  });
  }

}
