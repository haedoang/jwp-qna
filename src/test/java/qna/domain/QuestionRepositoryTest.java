package qna.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.DirtiesContext;
import qna.CannotDeleteException;
import qna.utils.StringUtils;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * packageName : qna.domain
 * fileName : QuestionHistoryTest
 * author : haedoang
 * date : 2021-11-09
 * description :
 */
@DataJpaTest
@EnableJpaAuditing
@TestMethodOrder(MethodOrderer.MethodName.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {
    private static final int MAX_COLUMN_LENGTH = 500;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        this.user1 = userRepository.save(UserTest.JAVAJIGI);
        this.user2 = userRepository.save(UserTest.SANJIGI);
    }


    @Test
    @DisplayName("Question 검증 테스트")
    public void T1_questionSaveTest() {
        //WHEN
        Question question1 = questionRepository.save(QuestionTest.Q1);
        Question question2 = questionRepository.save(QuestionTest.Q2);
        //THEN
        assertAll(
                () -> assertThat(question1.isOwner(user1)).isTrue(),
                () -> assertThat(question2.isOwner(user2)).isTrue()
        );
    }

    @Test
    @DisplayName("Question 유효성체크1 null")
    public void T2_validate() {
        //WHEN
        Question titleNull = new Question(null, "contents1");
        //THEN
        assertThatThrownBy(() -> questionRepository.save(titleNull)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Question 유효성체크2 길이초과")
    public void T3_validate2() {
        //WHEN
        Question titleLengthOver = new Question(StringUtils.getRandomString(MAX_COLUMN_LENGTH), "contents1");
        //THEN
        assertThatThrownBy(() -> questionRepository.save(titleLengthOver)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("본인이 작성한 질문인지 확인한다.")
    public void T4_isOwner() {
        //WHEN
        Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        //THEN
        assertThat(question.isOwner(user1)).isTrue();
    }

    @Test
    @DisplayName("답변이 있는지 조회한다.")
    public void T5_hasAnswers() {
        //GIVEN
        Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        Answer answer = answerRepository.save(new Answer(user1, question, "Answers Contents1"));
        Answer answer2 = answerRepository.save(new Answer(user2, question, "Answers Contents2"));
        question.addAnswer(answer);
        question.addAnswer(answer2);
        //WHEN
        Question findQuestion = questionRepository.findById(question.getId()).orElseThrow(NoSuchElementException::new);
        //THEN
        assertThat(findQuestion.hasAnswers()).isTrue();
    }

    @Test
    @DisplayName("답변이 모두 본인이 작성한 것이 아니면 false")
    public void T5_hasOwnAnswers_NG() {
        //GIVEN
        Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        Answer answer = answerRepository.save(new Answer(user1, question, "Answers Contents1"));
        Answer answer2 = answerRepository.save(new Answer(user2, question, "Answers Contents2"));
        question.addAnswer(answer);
        question.addAnswer(answer2);
        //WHEN
        Question findQuestion = questionRepository.findById(question.getId()).orElseThrow(NoSuchElementException::new);
        //THEN
        assertThat(findQuestion.hasOwnAnswers()).isFalse();
    }

    @Test
    @DisplayName("답변이 모두 본인이 작성한 것이 아니면 true")
    public void T5_hasOwnAnswers_OK() {
        //GIVEN
        Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        Answer answer = answerRepository.save(new Answer(user1, question, "Answers Contents1"));
        Answer answer2 = answerRepository.save(new Answer(user1, question, "Answers Contents2"));
        question.addAnswer(answer);
        question.addAnswer(answer2);
        //WHEN
        Question findQuestion = questionRepository.findById(question.getId()).orElseThrow(NoSuchElementException::new);
        //THEN
        assertThat(findQuestion.hasOwnAnswers()).isTrue();
    }

    @Test
    @DisplayName("삭제하기_실패")
    public void T6_delete() {
        //GIVEN
        Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        Answer answer = answerRepository.save(new Answer(user1, question, "Answers Contents1"));
        Answer answer2 = answerRepository.save(new Answer(user2, question, "Answers Contents2"));
        //WHEN
        question.addAnswer(answer);
        question.addAnswer(answer2);
        Question findQuestion = questionRepository.findById(question.getId()).orElseThrow(NoSuchElementException::new);
        //THEN
        assertAll(
                () -> assertThatThrownBy(() -> findQuestion.delete(user2)).isInstanceOf(CannotDeleteException.class)
                        .hasMessageContaining("질문을 삭제할 권한이 없습니다."),
                () -> assertThatThrownBy(() -> findQuestion.delete(user1)).isInstanceOf(CannotDeleteException.class)
                        .hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.")
        );
    }
}
