package ui;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;

/**
* Tests that the app runs and that elements are shown.
*/
public class AppTest extends ApplicationTest {

    @Start
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }
    /**
     * Tests that elements are shown.
     */
    @Test
    public void testLoginScreenLoaded() {
        assertThat(lookup("#loginButton").tryQuery()).isPresent();
        assertThat(lookup("#createUserButton").tryQuery()).isPresent();
        assertThat(lookup("#usernameInput").tryQuery()).isPresent();
        assertThat(lookup("#passwordInput").tryQuery()).isPresent();
        assertThat(lookup("#errorMessage").tryQuery()).isPresent();
    }

    /**
     * Tests that the method supportHeadless method works.
     */
    @Test
    void testSupportHeadlessWhenHeadlessIsTrue() {
        // Arrange
        System.setProperty("headless", "true");

        // Act
        App.supportHeadless();

        // Assert
        assertEquals("glass", System.getProperty("testfx.robot"));
        assertEquals("true", System.getProperty("testfx.headless"));
        assertEquals("sw", System.getProperty("prism.order"));
        assertEquals("t2k", System.getProperty("prism.text"));
        assertEquals("true", System.getProperty("java.awt.headless"));
    }

    /**
     * Method that clears properties.
     */
    @AfterEach
    void tearDownProperties() {
        System.clearProperty("testfx.robot");
        System.clearProperty("testfx.headless");
        System.clearProperty("prism.order");
        System.clearProperty("prism.text");
    }
}
