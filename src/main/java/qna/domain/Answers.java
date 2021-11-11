package qna.domain;

import java.util.List;

/**
 * packageName : qna.domain
 * fileName : Answers
 * author : haedoang
 * date : 2021-11-11
 * description : 일급 컬렉션
 */
public class Answers {
    private final List<Answer> answers;

    private Answers(List<Answer> answerList) {
       this.answers = answerList;
    }

    public Answers of(List<Answer> answerList) {
        return new Answers(answerList);
    }

    public boolean hasOwnAnswers(User user) {
        return this.answers.stream().allMatch(answer -> answer.getWriter().equals(user));
    }

}
