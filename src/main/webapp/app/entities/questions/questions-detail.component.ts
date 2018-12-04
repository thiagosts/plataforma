import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestions } from 'app/shared/model/questions.model';

@Component({
    selector: 'jhi-questions-detail',
    templateUrl: './questions-detail.component.html'
})
export class QuestionsDetailComponent implements OnInit {
    questions: IQuestions;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ questions }) => {
            this.questions = questions;
        });
    }

    previousState() {
        window.history.back();
    }
}
