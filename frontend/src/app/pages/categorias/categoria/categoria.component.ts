import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-categoria',
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './categoria.component.html',
  styleUrl: './categoria.component.css'
})
export class CategoriaComponent implements OnInit {
  categoria = {
    id: '',
    nombre: '',
    descripcion: '',
    tipoId: ''
  };

  categorias: any[] = [];
  tipos: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.obtenerCategorias();
    this.obtenerTipos();
  }

  obtenerCategorias() {
    this.http.get<any[]>('http://localhost:8080/api/categorias').subscribe(res => {
      this.categorias = res;
    });
  }

  obtenerTipos() {
    this.http.get<any[]>('http://localhost:8080/api/tipos').subscribe(res => {
      this.tipos = res;
    });
  }

  guardarCategoria() {
    if (this.editando && this.categoria.id) {
    this.http.put(`http://localhost:8080/api/categorias/${this.categoria.id}`, this.categoria).subscribe(() => {
      this.obtenerCategorias();
      this.categoria = {id: '', nombre: '', descripcion: '', tipoId: ''};
      this.editando = false;
    });
    } else {
      this.http.post('http://localhost:8080/api/categorias', this.categoria).subscribe(() => {
      this.obtenerCategorias();
      this.categoria = {id: '', nombre: '', descripcion: '', tipoId: ''};
    });
  }
  }

  editando: boolean = false;

  editarCategoria(cat: any) {
    this.categoria = {
      id: cat.id,
      nombre: cat.nombre,
      descripcion: cat.descripcion,
      tipoId: cat.tipo?.id
    };
    this.editando = true;
  }

  eliminarCategoria(id: number) { 
    this.http.delete(`http://localhost:8080/api/categorias/${id}`).subscribe(() => {
      this.obtenerCategorias();
    });
  }
  
  cancelarEdicion() {
  this.categoria = { id: '', nombre: '', descripcion: '', tipoId: '' };
  this.editando = false;
  }
}
