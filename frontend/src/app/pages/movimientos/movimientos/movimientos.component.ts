import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-movimiento',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './movimientos.component.html',
  styleUrls: ['./movimientos.component.css']
})
export class MovimientoComponent implements OnInit {
  movimientos: any[] = [];
  categorias: any[] = [];
  tipos: any[] = [];

  movimiento = {
    id: '',
    monto: '',
    descripcion: '',
    categoriaId: ''
  };

  editando = false;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.obtenerMovimientos();
    this.obtenerCategorias();
    this.obtenerTipos();
  }

  obtenerMovimientos() {
    this.http.get<any[]>('http://localhost:8080/api/movimientos').subscribe(res => {
      this.movimientos = res;
    });
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

  guardarMovimiento() {
    const request = this.editando
      ? this.http.put(`http://localhost:8080/api/movimientos/${this.movimiento.id}`, this.movimiento)
      : this.http.post('http://localhost:8080/api/movimientos', this.movimiento);

    request.subscribe(() => {
      this.obtenerMovimientos();
      this.movimiento = { id: '', monto: '', descripcion: '', categoriaId: '' };
      this.editando = false;
    });
  }

  editarMovimiento(mov: any) {
    this.movimiento = {
      id: mov.id,
      monto: mov.monto,
      descripcion: mov.descripcion,
      categoriaId: mov.categoria?.id
    };
    this.editando = true;
  }

  eliminarMovimiento(id: number) {
    this.http.delete(`http://localhost:8080/api/movimientos/${id}`).subscribe(() => {
      this.obtenerMovimientos();
    });
  }

  obtenerNombreCategoria(id: number): string {
    return this.categorias.find(c => c.id === id)?.nombre || 'N/A';
  }

  obtenerTipoColorPorCategoriaId(id: number): string {
    const categoria = this.categorias.find(c => c.id === id);
    const tipo = this.tipos.find(t => t.id === categoria?.tipo?.id);
    return tipo?.color || '#ccc';
  }

  obtenerSaldo(): number {
    return this.movimientos.reduce((acc, m) => {
      const categoria = this.categorias.find(c => c.id === m.categoria.id);
      const tipo = this.tipos.find(t => t.id === categoria?.tipo?.id);
      return tipo?.nombre === 'Ingreso' ? acc + m.monto : acc - m.monto;
    }, 0);
  }
}
