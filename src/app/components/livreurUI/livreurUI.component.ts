import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tournee',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './livreurUI.component.html',
  styleUrl: './livreurUI.component.scss'
})
export class LivreurUIComponent {}