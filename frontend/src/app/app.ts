import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
selector: 'app-root',
imports: [RouterOutlet, RouterLink, RouterLinkActive],
templateUrl: './app.html',
styleUrl: './app.css'
})
export class App {

constructor(private router: Router) {}

get showLayout(): boolean {
return this.router.url !== '/login';
}

}
