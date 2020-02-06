package test;

import main.Main;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MainTest {
    Main main = new Main();
    @Test
    public void fraction1() {
        test("1 * 0/5" , "0");
    }

    @Test
    public void fraction2() {
        test("1/2 * 3_3/4" , "1_7/8");
    }

    @Test
    public void fraction3() {
        test("1/2 * 3_3/4  +      1/2 * 3_3/4 -  1/2 * 9_3/4" , "-1_1/8");
    }

    @Test
    public void fraction4() {
        test("10/2" , "5");
    }

    @Test
    public void fraction5() {
        test("5/2" , "2_1/2");
    }

    @Test
    public void fraction6() {
        test("5/2 * 4" , "10");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fraction7() {
        main.solve("1 * 5/0".split("\\s+"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fraction8() {
        main.solve("1 * 5/0 +".split("\\s+"));
    }

    private void test(String input, String expected) {
        assertEquals(main.solve(input.split("\\s+")), expected);
    }

}