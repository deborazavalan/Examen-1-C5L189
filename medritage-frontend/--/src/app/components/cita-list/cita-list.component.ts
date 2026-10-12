import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { CitaService } from '../../services/cita.service';
import { CitaMedica } from '../../models/cita.model';

@Component({
  selector: 'app-cita-list',
  standalone: true,
  templateUrl: './cita-list.component.html',
  styleUrl: './cita-list.component.css'
})
export class CitaListComponent implements OnInit {
  private citaService = inject(CitaService);

  filtros = ['TODAS', 'ALTA', 'MEDIA', 'BAJA'];
  citas = signal<CitaMedica[]>([]);
  filtro = signal('TODAS');

  citasFiltradas = computed(() => {
    if (this.filtro() === 'TODAS') {
      return this.citas();
    }
    return this.citas().filter(c => c.nivelPrioridad === this.filtro());
  });

  ngOnInit(): void {
    this.citaService.getCitas().subscribe(data => this.citas.set(data));
  }

  cambiarFiltro(f: string): void {
    this.filtro.set(f);
  }

  avanzarEstado(cita: CitaMedica): void {
    let nuevo = '';
    if (cita.estado === 'PENDIENTE') {
      nuevo = 'EN_ATENCION';
    } else if (cita.estado === 'EN_ATENCION') {
      nuevo = 'ATENDIDA';
    } else {
      return;
    }
    this.citaService.actualizarEstado(cita.id, nuevo).subscribe(act => {
      this.citas.update(lista => lista.map(c => (c.id === act.id ? act : c)));
    });
  }
}
