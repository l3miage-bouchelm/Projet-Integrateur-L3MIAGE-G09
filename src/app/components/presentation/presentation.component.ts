import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { LivreurLoginComponent } from "../livreur-login/livreur-login.component";

@Component({
    selector: 'app-presentation',
    standalone: true,
    templateUrl: './presentation.component.html',
    styleUrl: './presentation.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        CommonModule,
        LivreurLoginComponent
    ]
})
export class PresentationComponent { }
