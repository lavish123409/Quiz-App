package com.example.quizapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizItem {

    String question,correctAns;
    String[] options = new String[4];

    public QuizItem(String ques,String cAns,String op1,String op2,String op3,String op4){

        question = ques;
        correctAns = cAns ;

        options[0] = op1 ;
        options[1] = op2 ;
        options[2] = op3 ;
        options[3] = op4 ;


        List<String> intList = Arrays.asList(options);

        Collections.shuffle(intList);

        options = intList.toArray(options);


    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }


}
