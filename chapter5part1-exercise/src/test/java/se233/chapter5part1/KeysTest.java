package se233.chapter5part1;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.Keys;

import static org.junit.jupiter.api.Assertions.*;

public class KeysTest {

    @BeforeAll
    public static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void add_singleKeyPress_shouldUpdateState() {
        Keys keys = new Keys();
        keys.add(KeyCode.A);
        assertTrue(keys.isPressed(KeyCode.A), "Key A should be recorded as pressed");
    }

    @Test
    public void add_multipleKeysPressedSimultaneously_shouldTrackAll() {
        Keys keys = new Keys();
        keys.add(KeyCode.A);
        keys.add(KeyCode.W);
        assertTrue(keys.isPressed(KeyCode.A), "Key A should be pressed");
        assertTrue(keys.isPressed(KeyCode.W), "Key W should be pressed simultaneously");
    }
}