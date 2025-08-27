import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private baseUrl = 'http://localhost:8080/api/usuarios'

  constructor(private http: HttpClient) { }

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.baseUrl);
  }

  registrar(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.baseUrl, usuario);
  }

  actualizarUsuario(id: number, usuario: Usuario): Observable<any> {
  return this.http.put(`http://localhost:8080/api/usuarios/${id}`, usuario);
  }
}
