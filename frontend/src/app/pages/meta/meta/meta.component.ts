import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-meta',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './meta.component.html',
  styleUrls: ['./meta.component.css']
})
export class MetaComponent implements OnInit {
  metas: any[] = [];
  meta = {
    titulo: '',
    objetivo: 0,
    fecha_limite: '',
    activo: false
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.obtenerMetas();
  }

  obtenerMetas(): void {
      this.http.get<any[]>('http://localhost:8080/api/metas').subscribe(res => {
      this.metas = res;
    });
  }

  registrarMeta(): void {
      this.http.post('http://localhost:8080/api/metas', this.meta).subscribe(() => {
      this.meta = { titulo: '', objetivo: 0, fecha_limite: '', activo: false};
      this.obtenerMetas();
    });
  }

  calcularPorcentaje(meta: any): number {
    return Math.min(100, (meta.acumulado / meta.objetivo) * 100);
  }

  activarMeta(id: number) {
    this.http.put(`http://localhost:8080/api/metas/activar/${id}`, {}).subscribe(() => {
    this.obtenerMetas();
  });
  }

  eliminar(id:number) {
    this.http.delete(`http://localhost:8080/api/metas/eliminar/${id}`).subscribe(() => {
      this.obtenerMetas();
    })
  }

}
