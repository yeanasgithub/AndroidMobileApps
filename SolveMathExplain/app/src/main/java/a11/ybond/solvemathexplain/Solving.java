package a11.ybond.solvemathexplain;

import java.util.ArrayList;
import java.util.List;

public class Solving {

    private List<Question> questions;

    private int numberCorrect;
    private int numberIncorrect;
    private int totalQuestions;
    private int score;
    private Question currentQuestion;


    // constructor
    public Solving()
    {
        numberCorrect = 0;
        numberIncorrect = 0;
        totalQuestions = 0;
        score = 0;
        currentQuestion = new Question(10);
        //currentQuestion = new Question();
        questions = new ArrayList<Question>();

    }

    public void makeNewQuestions()
    {
        currentQuestion = new Question(totalQuestions * 2 + 2);
        totalQuestions++;
        questions.add(currentQuestion);
    }

    public boolean checkAnswer(int submittedAnswer)
    {
        boolean isCorrect;
        if (currentQuestion.getAnswer() == submittedAnswer)
        {
            numberCorrect++;
            isCorrect = true;
        }

        else
        {
            numberIncorrect++;
            isCorrect = false;
        }

        score = numberCorrect * 5 - numberIncorrect * 10;
        return isCorrect;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNumberIncorrect() {
        return numberIncorrect;
    }

    public void setNumberIncorrect(int numberIncorrect) {
        this.numberIncorrect = numberIncorrect;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

}
