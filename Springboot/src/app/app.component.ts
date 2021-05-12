import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from './login/auth.service';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Angular + Springboot CRUD ';

  public isLoggedIn : boolean;



  constructor(private route: ActivatedRoute, private router: Router, private authenticationService:AuthService, public userService: UserService){}

  ngOnInit(){
   // this.userService.data
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();
    console.log('menu ->' + this.isLoggedIn);
  }


  handleLogout(){
    this.authenticationService.logout();
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();
    console.log('menu ->' + this.isLoggedIn);
    this.router.navigate(['login']);
  }
}
