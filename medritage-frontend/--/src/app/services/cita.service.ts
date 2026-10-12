import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CitaMedica, CrearCitaPayload } from '../models/cita.model';

@Injectable({ providedIn: 'root' })
export class CitaService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.API_URL}citas`;

  getCitas(): Observable<CitaMedica[]> {
    return this.http.get<CitaMedica[]>(this.base);
  }

  getCitasPorPrioridad(prioridad: string): Observable<CitaMedica[]> {
    return this.http.get<CitaMedica[]>(`${this.base}/prioridad/${prioridad}`);
  }

  crearCita(payload: CrearCitaPayload): Observable<CitaMedica> {
    return this.http.post<CitaMedica>(this.base, payload);
  }

  actualizarEstado(id: number, estado: string): Observable<CitaMedica> {
    return this.http.patch<CitaMedica>(`${this.base}/${id}/estado`, { estado });
  }
}