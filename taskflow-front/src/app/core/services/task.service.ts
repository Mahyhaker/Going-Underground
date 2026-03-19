import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TaskRequestModel } from '../../models/task-request.model';
import { TaskResponseModel } from '../../models/task-response.model';
import { PageResponseModel } from '../../models/page-response.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) {}

  listar(page: number = 0, size: number = 10): Observable<PageResponseModel<TaskResponseModel>> {
    return this.http.get<PageResponseModel<TaskResponseModel>>(
      `${this.apiUrl}?page=${page}&size=${size}&sortBy=id&direction=desc`
    );
  }

  buscarPorId(id: number): Observable<TaskResponseModel> {
    return this.http.get<TaskResponseModel>(`${this.apiUrl}/${id}`);
  }

  buscarPorTitulo(titulo: string, page: number = 0, size: number = 10): Observable<PageResponseModel<TaskResponseModel>> {
    return this.http.get<PageResponseModel<TaskResponseModel>>(
      `${this.apiUrl}/buscar?titulo=${titulo}&page=${page}&size=${size}&sortBy=id&direction=desc`
    );
  }

  criar(data: TaskRequestModel): Observable<TaskResponseModel> {
    return this.http.post<TaskResponseModel>(this.apiUrl, data);
  }

  atualizar(id: number, data: TaskRequestModel): Observable<TaskResponseModel> {
    return this.http.put<TaskResponseModel>(`${this.apiUrl}/${id}`, data);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
