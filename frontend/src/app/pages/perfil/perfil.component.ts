import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NgChartsModule } from 'ng2-charts'; 
import { ChartData } from 'chart.js';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  usuario: any = {};
  categorias: any[] = [];
  tipos: any[] = [];
  movimientos: any[] = [];
  metas: any[] = [];
  vista = 'movimientos';

  chartDataPie: ChartData<'pie', number[], string> = {
  labels: [],
  datasets: [
    {
      data: [],
      backgroundColor: ['#198754', '#dc3545', '#0d6efd'],
    }
  ]
  };

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigate(['/login']);
      return;
    }

    this.http.get<any>('http://localhost:8080/api/perfil').subscribe({
      next: res => {
        this.usuario = res.usuario;
        this.categorias = res.categorias;
        this.tipos = res.tipos;
        this.movimientos = res.movimientos;
        this.metas = res.metas;

        this.procesarDatosParaGrafico();
      },
      error: err => {
        console.error('Error al cargar perfil:', err);
        this.router.navigate(['/login']);
      }
    });
  }

  calcularSaldo(): number {
  let saldo = 0;

  for (const mov of this.movimientos) {
    const tipoNombre = mov.categoria?.tipo?.nombre?.toLowerCase();

    if (tipoNombre === 'ingreso') {
      saldo += mov.monto;
    } else if (tipoNombre === 'salida' || tipoNombre === 'ahorro') {
      saldo -= mov.monto;
    }
  }
  return saldo;
  }

  calcularAhorro(): number {
  let ahorro = 0;
  for (const mov of this.movimientos) {
    const tipoNombre = mov.categoria?.tipo?.nombre?.toLowerCase();
    if (tipoNombre === 'ahorro') ahorro += mov.monto;
  }
  return ahorro;
  }

  filtrarCategoriasPorTipo(tipoId: number): any[] {
    return this.categorias.filter(cat => cat.tipo?.id === tipoId);
  }

  procesarDatosParaGrafico() {
    const resumen: { [tipo: string]: number } = {};

    for (const mov of this.movimientos) {
      const tipoNombre = mov.categoria?.tipo?.nombre;
      if (!tipoNombre) continue;

      resumen[tipoNombre] = (resumen[tipoNombre] || 0) + mov.monto;
    }
    this.chartDataPie.labels = Object.keys(resumen);
    this.chartDataPie.datasets[0].data = Object.values(resumen);
  }
}
