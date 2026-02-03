package com.geopokrovskiy.okved_game_test.unit;

import com.geopokrovskiy.okved_game_test.exception.NotRussianNumberException;
import com.geopokrovskiy.okved_game_test.service.NumberNormalizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class NumberNormalizerTest {

    private final NumberNormalizer numberNormalizer = new NumberNormalizer();

    private final String correctRussianNumber1 = "+79261234567";
    private final String correctRussianNumber2 = "89261234567";
    private final String correctRussianNumber3 = "8(926)123-45-67";
    private final String correctRussianNumber4 = "0079261234567";
    private final String correctRussianNumber5 = "+7 926 123 45 67";

    private final String incorrectNumber1 = "+39 123 45 67";
    private final String incorrectNumber2 = "+7 926 123 45 67 8";
    private final String incorrectNumber3 = "+7 926 123 45 6";

    @Test
    @DisplayName("Correct Russian mobile number starting with +7")
    public void testCase1() {
        String normalizedNumber = numberNormalizer.normalizePhoneNumber(correctRussianNumber1);
        assertEquals(correctRussianNumber1, normalizedNumber);
    }


    @Test
    @DisplayName("Correct Russian mobile number starting with 8")
    public void testCase2() {
        String normalizedNumber = numberNormalizer.normalizePhoneNumber(correctRussianNumber2);
        assertEquals(correctRussianNumber1, normalizedNumber);
    }

    @Test
    @DisplayName("Correct Russian mobile having brackets and dashes")
    public void testCase3() {
        String normalizedNumber = numberNormalizer.normalizePhoneNumber(correctRussianNumber3);
        assertEquals(correctRussianNumber1, normalizedNumber);
    }

    @Test
    @DisplayName("Correct Russian mobile starting wth 007")
    public void testCase4() {
        String normalizedNumber = numberNormalizer.normalizePhoneNumber(correctRussianNumber4);
        assertEquals(correctRussianNumber1, normalizedNumber);
    }

    @Test
    @DisplayName("Correct Russian mobile having spaces")
    public void testCase5() {
        String normalizedNumber = numberNormalizer.normalizePhoneNumber(correctRussianNumber5);
        assertEquals(correctRussianNumber1, normalizedNumber);
    }

    @Test
    @DisplayName("Foreign number")
    public void testCase6() {
        assertThrows(NotRussianNumberException.class, () -> numberNormalizer.normalizePhoneNumber(incorrectNumber1));
    }

    @Test
    @DisplayName("Incorrect Russian number with an extra digit")
    public void testCase7() {
        assertThrows(NotRussianNumberException.class, () -> numberNormalizer.normalizePhoneNumber(incorrectNumber2));
    }

    @Test
    @DisplayName("Incorrect Russian number without a digit")
    public void testCase8() {
        assertThrows(NotRussianNumberException.class, () -> numberNormalizer.normalizePhoneNumber(incorrectNumber3));
    }
}
