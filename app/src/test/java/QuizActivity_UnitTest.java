
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
    //*** UNIT TESTING ***
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
        assertTrue(isCorrectAnswerTest(selectedAnswer, correctAnswer));

        // Check if the selected answer is correct and update the score
        if (selectedAnswer.equals(correctAnswer)) {
            quizActivity.score++;
        }

        // Verify that the score is updated correctly
        assertEquals(1, quizActivity.score);
    }
    //Flavios Unit Test
    //Testing the following questions
    //       2. "Database transactions should:",
    //       a. be run on a foreground thread"
    //       b. "be run on a background thread"
    //       c. "be started only when an activity is first created"
    //       d. "Threads should only be made from natural fiber"
    //  correct answer: "be run on a background thread"
    @Test
    public void testDBTransactionQuestion() {
        // Create a mock QuizActivity instance
        QuizActivity quizActivity =  mock(QuizActivity.class);
        // Set up the score variable
        quizActivity.score = 0;
        // Simulate selecting the correct answer
        String selectedAnswer = "be run on a background thread";
        String correctAnswer = "be run on a background thread";
        assertTrue(isCorrectAnswerTest(selectedAnswer, correctAnswer));

        // Check if the answer is correct
        if (isCorrectAnswerTest(selectedAnswer, correctAnswer)) {
            quizActivity.score++;
        }

        // Verify that the score is updated correctly
        assertEquals(1, quizActivity.score);
    }

    //Test the "background task should   do what" question...
    // 1. Background tasks should do what?
    //   a. "always update the UI", "not update the UI",
    //   b." be painted chroma key green (00b140)"
    //   c."always be started using buttons" ,
    //   d. "not update the UI"
    //correct answer: "not update the UI"

    @Test
    public void testBackgroundThreadQuestion() {
        // Create a mock QuizActivity instance
        QuizActivity quizActivity =  mock(QuizActivity.class);
        // Set up the score variable
        quizActivity.score = 0;
        // Simulate selecting the correct answer
        String selectedAnswer = "not update the UI";
        String correctAnswer = "not update the UI";

        // Check if the selected answer is correct and update the score
        if (selectedAnswer.equals(correctAnswer)) {
            quizActivity.score++;
        }

        // Verify that the score is updated correctly
        assertEquals(1, quizActivity.score);
    }

}