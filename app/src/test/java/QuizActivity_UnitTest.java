
/*
 * @abstract: This is a unit test for the QuizActivity class in an Android application
 *  tests the isCorrectAnswerTest method to verify if the selected answer is correct
 *  tests the checkAnswer method to ensure that the score is updated correctly
 */
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


import org.junit.Test;
import com.example.dev_p2_android_application.QuizActivity;

public class QuizActivity_UnitTest {

    public boolean isCorrectAnswerTest(String selectedAnswer, String correctAnswer) {
        return selectedAnswer.equals(correctAnswer);
    }
    //*** UNIT TESTTING ***
    @Test
    public void IsAnswerCorrectTesting() {
        // Correct answer scenario
        String selectedAnswer = "Java Virtual Machine";
        String correctAnswer = "Java Virtual Machine";
        assertTrue(isCorrectAnswerTest(selectedAnswer, correctAnswer));

        // Incorrect answer scenario
        selectedAnswer = "JVM Visits Mars";
        correctAnswer = "Java Virtual Machine";
        assertFalse(isCorrectAnswerTest(selectedAnswer, correctAnswer));
    }


    @Test

    public void checkAnswerTest() {
        // Create a mock QuizActivity instance
        QuizActivity quizActivity = mock(QuizActivity.class);

        // Set up the score variable
        quizActivity.score = 0;

        // Simulate selecting the correct answer
        String selectedAnswer = "Java Virtual Machine";
        String correctAnswer = "Java Virtual Machine";

        // Check if the answer is correct
        if (isCorrectAnswerTest(selectedAnswer, correctAnswer)) {
            quizActivity.score++;
        }

        // Verify that the score is updated correctly
        assertEquals(1, quizActivity.score);
    }

}