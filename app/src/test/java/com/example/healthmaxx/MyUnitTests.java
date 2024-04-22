package com.example.healthmaxx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.Login.CalculateBMR;
import com.example.healthmaxx.databinding.FragmentCalculateBMRBinding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MyUnitTests {

    @Test
    public void testCalculateBMR() {
        CalculateBMR fragment = new CalculateBMR();

        // Arrange
        Double weight = 70.0;
        Double height = 163.5;
        int age = 21;
        String gender = "Male";

        // Act
        Double bmr = fragment.calculateBMR(height, weight, age, gender);

        // Assert
        assertEquals(1621.875, bmr, 0.01);

    }

    @Test
    public void testPasswordSecurity(){

        // Test Case 1: Weak password
        assertFalse(LoginActivity.isSecurePassword("password"));

        // Test Case 2: Strong password
        assertTrue(LoginActivity.isSecurePassword("$tr0ngP4ssW0rdX"));

    }

}
