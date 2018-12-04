import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswers } from 'app/shared/model/answers.model';

@Component({
    selector: 'jhi-answers-detail',
    templateUrl: './answers-detail.component.html'
})
export class AnswersDetailComponent implements OnInit {
    answers: IAnswers;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ answers }) => {
            this.answers = answers;
        });
    }

    previousState() {
        window.history.back();
    }
}
