package a11.ybond.solvemathexplain;

import java.util.Random;

public class Question {

    private int firstNum;
    private int secondNum;
    private int answer;

    // there are four possible choices for the user to pick from
    private int [] answerArray;

    // which of the four positions is correct, 0, 1, 2, or 3
    private int answerPosition;

    // the maximum value of firstNumber or secondNumber
     private int upperLimit ;

    // question number e.g. Q1
    public int questionNumber = 0;

    // string output of the problem e.g. " 10 % 14 = "
    private String questionEquation;


    private String questionPhrase;

    // generate a new random question
    public Question(int upperLimit)
    {
        this.upperLimit = upperLimit;
        Random randomNumberMaker = new Random();

        // [0...84] + 15 = [15 ... 99]
        this.firstNum = randomNumberMaker.nextInt(85) + 15;
        // [0...20] + 10 = [10 ... 30]
        this.secondNum = randomNumberMaker.nextInt(21) + 10;
        this.answer = this.firstNum % this.secondNum;

        this.questionNumber = questionNumber;

        this.questionEquation = firstNum + "%" + secondNum + "=";

        this.questionPhrase = "What is the result value after the above equation is operated?";


        this.answerPosition = randomNumberMaker.nextInt(4);
        this.answerArray = new int[] {0,1,2,3};

        this.answerArray[0] = answer + 1;
        this.answerArray[1] = answer + 10;
        this.answerArray[2] = answer - 5;
        this.answerArray[3] = answer - 2;

        this.answerArray = shuffleArray(this.answerArray);

        // after shuffling, replace one of the elem with answer value
        answerArray[answerPosition] = answer;

    }

    // mix answers
    private int [] shuffleArray(int[] array)
    {
        int index, temp;
        Random randomNumberGenerator = new Random();

        for (int i = array.length - 1; i > 0; i--)
        {
            index = randomNumberGenerator.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return array;
    }


    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }


    public int getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public int getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getQuestionEquation() {
        return questionEquation;
    }

    public void setQuestionEquation(String questionEquation) {
        this.questionEquation = questionEquation;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }

}
