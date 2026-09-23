package se233.chapter5part2.model;

import javafx.geometry.Point2D;
import se233.chapter5part2.view.GameStage;

import java.util.Random;

public class Food {
    private Point2D position;
    private Random rn;
    private boolean isSpecial;
    private int points;

    public Food(Point2D position) {
        this.rn = new Random();
        this.position = position;
        this.isSpecial = false;
        this.points = 1;
    }

    public Food(Point2D position, boolean isSpecial) {
        this.rn = new Random();
        this.position = position;
        this.isSpecial = isSpecial;
        this.points = isSpecial ? 5 : 1;
    }

    public Food() {
        this.rn = new Random();
        this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
        this.isSpecial = rn.nextInt(5) == 0;
        this.points = this.isSpecial ? 5 : 1;
    }

    public void respawn() {
        Point2D prev_position = this.position;
        do {
            this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
        } while (prev_position == this.position);
        this.isSpecial = rn.nextInt(5) == 0;
        this.points = this.isSpecial ? 5 : 1;
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public int getPoints() {
        return points;
    }
}
