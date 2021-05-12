import { Component, OnInit, ViewChild } from '@angular/core';
import { UserDetailsComponent } from '../user-details/user-details.component';
import { Observable } from "rxjs";
import { UserService } from "../user.service";
import { User } from "../user";
import { Router } from '@angular/router';
import { AuthService } from '../login/auth.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  user: User;
  private userService: UserService;

  constructor(userService: UserService, private router: Router, private authenticationService: AuthService,
    public appco: AppComponent) {
    this.userService = userService;
  }

  ngOnInit() {
    this.getUsers();
    this.appco.isLoggedIn = this.authenticationService.isUserLoggedIn();
  }



  private getUsers() {
    this.userService.getUsersList().subscribe(
      data => {
        this.users = data;
      });
    console.log(this.users);
  }

  val: any;
  getUserByVal(event) {
    this.val = event.target.value;
    console.log(this.val);
    if(this.val === null || this.val === ""){
      console.log(this.val);
      this.val="null";
      console.log(this.val);

      this.userService.getUserByValue(this.val).subscribe(
        data => {
          this.users = data;
        });
      console.log(this.users);

    }
    else{this.userService.getUserByValue(this.val).subscribe(
      data => {
        this.users = data;
      });
    console.log(this.users);}
    
  }

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe(
      data => {
        console.log(data);
        this.getUsers();
      },
      error => console.log(error));
  }

  userDetails(id: number) {
    this.router.navigate(['details', id]);
  }

  updateUser(id: number) {
    this.router.navigate(['update', id]);
  }

}
