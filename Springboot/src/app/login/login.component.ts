import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { UserService } from '../user.service';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild('AppComponent', {static: false}) app: AppComponent;

  username: string;
  password : string;
  errorMessage = 'Invalid Credentials';
  successMessage: string;
  invalidLogin = false;
  loginSuccess = false;


  constructor(private route: ActivatedRoute, private router: Router, private authenticationService: AuthService, public userService: UserService,
    public appco: AppComponent) { }

  ngOnInit(): void {
    this.appco.isLoggedIn = this.authenticationService.isUserLoggedIn()
    
  }

  userValid= new FormGroup({
    firstName: new FormControl('',[Validators.required]),
    lastName: new FormControl('',[Validators.required])
  });

  handleLogin(){
    this.authenticationService.authenticationService(this.username, this.password).subscribe((result)=> {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage='Login Successful.';
      this.router.navigate(['/users']);
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });
  }
}
