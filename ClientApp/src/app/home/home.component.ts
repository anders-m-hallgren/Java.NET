import { Component, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent {
  public features: Feature[];
  constructor(http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    http.get<Feature[]>(baseUrl + 'feature').subscribe(result => {
      this.features = result;
    }, error => console.error(error));
  }
}

interface Feature {
  name: string;
  activated: boolean;
  description: string;
}
