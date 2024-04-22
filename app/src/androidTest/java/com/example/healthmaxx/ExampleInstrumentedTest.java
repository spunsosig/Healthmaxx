package com.example.healthmaxx;

import android.content.Context;

import androidx.annotation.UiThread;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.healthmaxx.DB.DBHandler;
import com.example.healthmaxx.Models.User;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private DBHandler db;
    Context appContext;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.healthmaxx", appContext.getPackageName());
    }

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = new DBHandler(appContext, "TestDB");
//        db.getWritableDatabase(); // This will initialize the database
    }

    @Test
    @UiThread
    public void testAddUser(){
        // Arrange
        String email = "test@gmail.com";
        String password = "Password1";
        String name = "John Doe";

        // Act
        long result = db.addUser(email, password, name, appContext);

        // Assert
        User user = db.getUser(email);

        assertNotEquals("User did not add to database successfully", result, -1);
        assertNotNull("User should not be null after adding", user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());

    }
}