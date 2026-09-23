package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.view.GameStage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {
    Field xVelocityField, yVelocityField, yAccelerationField;
    private GameCharacter gameCharacter;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeEach
    public void setup() throws NoSuchFieldException {
        gameCharacter = new GameCharacter(0, 30, 30, "assets/Character1.png", 4, 3, 2, 111, 97, KeyCode.A, KeyCode.D, KeyCode.W);
        xVelocityField = gameCharacter.getClass().getDeclaredField("xVelocity");
        yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        yAccelerationField = gameCharacter.getClass().getDeclaredField("yAcceleration");
        xVelocityField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yAccelerationField.setAccessible(true);
    }

    @Test
    public void respawn_givenNewGameCharacter_thenCoordinatesAre30_30() {
        gameCharacter.respawn();
        assertEquals(30, gameCharacter.getX(), "Initial x");
        assertEquals(30, gameCharacter.getY(), "Initial y");
    }

    @Test
    public void respawn_givenNewGameCharacter_thenScoreIs0() {
        gameCharacter.respawn();
        assertEquals(0, gameCharacter.getScore(), "Initial score");
    }

    @Test
    public void moveX_givenMoveRightOnce_thenXCoordinateIncreasedByxVelocityField() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveRight();
        gameCharacter.moveX();
        assertEquals(30 + xVelocityField.getInt(gameCharacter), gameCharacter.getX(), " Move right x");
    }

    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYVelocityIncreases() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yVelocity1 = yVelocityField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yVelocity2 = yVelocityField.getInt(gameCharacter);
        assertTrue(yVelocity2 > yVelocity1, "Velocity is increasing");
    }

    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYAccelerationUnchanged() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yAcceleration1 = yAccelerationField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yAcceleration2 = yAccelerationField.getInt(gameCharacter);
        assertTrue(yAcceleration1 == yAcceleration2, "Acceleration is not change");
    }

    @Test
    public void checkReachGameWall_leftBoundary_shouldReturnTrue() {
        gameCharacter.setX(-1);
        boolean reachedWall = gameCharacter.checkReachGameWall();
        assertTrue(reachedWall, "Character should reach the left boundary");
    }

    @Test
    public void checkReachGameWall_rightBoundary_shouldReturnTrue() {
        gameCharacter.setX(GameStage.WIDTH + 1);
        boolean reachedWall = gameCharacter.checkReachGameWall();
        assertTrue(reachedWall, "Character should reach the right boundary");
    }

    @Test
    public void jump_whenCanJumpIsTrue_shouldStartJumping() {
        gameCharacter.setCanJump(true);
        gameCharacter.jump();
        assertTrue(gameCharacter.isJumping(), "Character should start jumping");
        assertFalse(gameCharacter.canJump(), "canJump should be false after jumping");
    }

    @Test
    public void jump_whenAirborne_shouldNotJump() {
        gameCharacter.setCanJump(false);
        gameCharacter.setIsJumping(true);

        gameCharacter.jump();
        assertTrue(gameCharacter.isJumping(), "Character should jumping");
    }

    @Test
    public void collided_horizontalCollision_shouldStopMovement() {
        GameCharacter targetCharacter = new GameCharacter(1, 100, 100, "assets/Character2.png", 4, 4, 1, 32, 32, null, null, null);

        gameCharacter.setX(100);
        gameCharacter.setIsMoveRight(true);
        targetCharacter.setX(110);

        gameCharacter.collided(targetCharacter);
        assertFalse(gameCharacter.isMoveRight(), "Movement should stop on horizontal collision");
    }

    @Test
    public void collided_verticalCollision_shouldIncreaseScoreAndRespawnTarget() {
        GameCharacter targetCharacter = new GameCharacter(1, 50, 100, "assets/Character2.png", 4, 4, 1, 32, 32, null, null, null);

        gameCharacter.setX(50);
        gameCharacter.setY(80);
        gameCharacter.setIsFalling(true);
        targetCharacter.setY(100);

        int initialScore = gameCharacter.getScore();
        boolean isCollided = gameCharacter.collided(targetCharacter);

        assertTrue(isCollided, "Vertical collision should be detected");
        assertEquals(initialScore + 1, gameCharacter.getScore(), "Score should increase by 1 after a successful jump-on collision");
    }
}