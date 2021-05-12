import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';
import { AuthService } from './login/auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = "http://localhost:8080/api/v1/users";

  constructor(private http: HttpClient, public authService: AuthService) { }

  getUser(id: number): Observable<User>{
    return this.http.get<User>(this.baseUrl+'/'+id);
  }

  getUserByValue(val: any): Observable<User[]>{
    return this.http.get<User[]>(this.baseUrl+'/search/'+val);
  }

  createUser(user: Object): Observable<Object>{
    return this.http.post(this.baseUrl, user);
  }

  updateUser(id: number, value: any): Observable<Object>{
    return this.http.put(this.baseUrl+'/'+id, value);
  }

  deleteUser(id: number): Observable<any>{
    return this.http.delete(this.baseUrl+'/'+id,{responseType:'text'});
  }

  getUsersList(): Observable<User[]>{
    return this.http.get<User[]>(this.baseUrl);
  }

  private readonly emailRegex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
  private readonly contactRegex="\\d{10}";

  userValid = new FormGroup({
    firstName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    lastName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    emailId: new FormControl('',[Validators.required, Validators.pattern(this.emailRegex)]),
    contact: new FormControl('',[Validators.required, Validators.pattern(this.contactRegex)]),

  });

  isInvalidAndDirty(field: string): boolean{
    const ctrl = this.userValid.get(field);
    return ctrl !==null && !ctrl.valid && ctrl.dirty;
  }

  hasError(field: string, error: string): boolean{
    const ctrl = this.userValid.get(field);
    return ctrl !==null && ctrl.dirty && ctrl.hasError(error);
  }

}
