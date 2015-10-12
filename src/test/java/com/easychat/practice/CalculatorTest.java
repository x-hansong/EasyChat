package com.easychat.practice;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by yonah on 15-9-17.
 */
public class CalculatorTest extends TestCase {
    private static Calculator calculator = new Calculator();
    @Before
    public void setUp() throws Exception {
        calculator.clear();
    }

    @Test
    public void testAdd() throws Exception {
        calculator.add(3);
        calculator.add(2);
        assertEquals(5, calculator.getResult());
    }

    @Test
    public void testSubstract() throws Exception {
        calculator.add(10);
        calculator.substract(2);
        assertEquals(9, calculator.getResult());
    }
    @Ignore("Multiply() Not yet implemented")
    @Test
    public void testMultiply() throws Exception {

    }

    @Test
    public void testDivide() throws Exception {
        calculator.add(8);
        calculator.divide(2);
        assertEquals(4, calculator.getResult());
    }
}