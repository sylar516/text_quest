package entities;

public class QuestPage {
    private String question;
    private String positiveAnswer;
    private String negativeAnswer;
    private String gameOver;

    public QuestPage(String question, String positiveAnswer, String negativeAnswer, String gameOver) {
        this.question = question;
        this.positiveAnswer = positiveAnswer;
        this.negativeAnswer = negativeAnswer;
        this.gameOver = gameOver;
    }

    public String getQuestion() {
        return question;
    }

    public String getPositiveAnswer() {
        return positiveAnswer;
    }

    public String getNegativeAnswer() {
        return negativeAnswer;
    }
    public String getGameOver() {
        return gameOver;
    }
}
