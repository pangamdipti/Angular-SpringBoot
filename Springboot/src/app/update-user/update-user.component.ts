import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
  id: number;
  user: User;
  submitted: boolean = false;

 /*  private readonly emailRegex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
  private readonly contactRegex="\\d{10}";


  updateUserValid = new FormGroup({
    firstName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    lastName: new FormControl('',[Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    emailId: new FormControl('',[Validators.required, Validators.pattern(this.emailRegex)]),
    contact: new FormControl('',[Validators.required, Validators.pattern(this.contactRegex)]),

  }); */
 

  isInvalidAndDirty(field: string): boolean{
    const ctrl = this.userValid.get(field);
    return ctrl !==null && !ctrl.valid && ctrl.dirty;
  }

  hasError(field: string, error: string): boolean{
    const ctrl = this.userValid.get(field);
    return ctrl !==null && ctrl.dirty && ctrl.hasError(error);
  }

  constructor(private route: ActivatedRoute, private router: Router,
    private userService: UserService) { }

    userValid: FormGroup = this.userService.userValid;

  ngOnInit(){
    this.user = new User();
    this.id = this.route.snapshot.params['id'];
    this.userService.getUser(this.id).subscribe(
      data => {
        console.log(data)
        this.user = data;
      }, error => console.log(error));
  }

  updateUser(){
    this.userService.updateUser(this.id, this.user).subscribe(
      data => {
        console.log(data)
        this.user = new User();
        this.gotoList();
      }, error => console.log(error));
  }

  onSubmit() {
    this.updateUser();   
  }

  gotoList() {
    this.router.navigate(['users']);
  }



}
