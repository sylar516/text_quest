package entities;

import java.util.Objects;

//Класс - реализация страницы квеста
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestPage page = (QuestPage) o;
        return Objects.equals(question, page.question) && Objects.equals(positiveAnswer, page.positiveAnswer) && Objects.equals(negativeAnswer, page.negativeAnswer) && Objects.equals(gameOver, page.gameOver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, positiveAnswer, negativeAnswer, gameOver);
    }
}
