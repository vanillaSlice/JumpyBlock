package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Random;

import lowe.mike.jumpyblock.Assets;
import lowe.mike.jumpyblock.JumpyBlockGame;
import lowe.mike.jumpyblock.actor.Block;
import lowe.mike.jumpyblock.actor.GroundSection;
import lowe.mike.jumpyblock.actor.Wall;

/**
 * Game screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends AbstractScreen {

    private static final String BEST_PREFERENCES_KEY = "best";
    private static final int SCORE_LABEL_FONT_SIZE = 36;
    private static final int SCORE_LABEL_Y_OFFSET = 50;
    private static final Vector2 BLOCK_STARTING_POSITION = new Vector2(50, 400);
    private static final int FIRST_WALL_X_POSITION = 400;
    private static final int WALL_SPACING = 250;
    private static final Random RANDOM = new Random();
    private static final int GAME_OVER_LABEL_FONT_SIZE = 32;
    private static final String GAME_OVER_LABEL_TEXT = "Game Over";
    private static final int GAME_OVER_SCORE_LABEL_FONT_SIZE = 30;
    private static final String GAME_OVER_SCORE_LABEL_TEXT = "Score  %d";
    private static final int GAME_OVER_BEST_LABEL_FONT_SIZE = 30;
    private static final String GAME_OVER_BEST_LABEL_TEXT = "Best  %d";
    private static final int REPLAY_LABEL_FONT_SIZE = 24;
    private static final String REPLAY_LABEL_TEXT = "Tap to replay";

    private final Preferences preferences;
    private int best;
    private int score;
    private final Label scoreLabel;
    private final Block block;
    private final Group ground = new Group();
    private final Group walls = new Group();
    private final Label gameOverLabel;
    private final Label gameOverScoreLabel;
    private final Label gameOverBestLabel;
    private final Label replayLabel;
    private boolean gameOver;

    /**
     * Creates a new {@code GameScreen} given a {@link ScreenManager}, {@link Assets}
     * and a {@link SpriteBatch}.
     *
     * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     */
    public GameScreen(ScreenManager screenManager, Assets assets, SpriteBatch spriteBatch) {
        super(screenManager, assets, spriteBatch);
        this.preferences = Gdx.app.getPreferences(JumpyBlockGame.TITLE);
        this.best = this.preferences.getInteger(BEST_PREFERENCES_KEY);
        this.scoreLabel = initialiseLabel(SCORE_LABEL_FONT_SIZE);
        this.block = new Block(assets.blockTexture, BLOCK_STARTING_POSITION);
        this.gameOverLabel = initialiseLabel(GAME_OVER_LABEL_FONT_SIZE, GAME_OVER_LABEL_TEXT);
        this.gameOverScoreLabel = initialiseLabel(GAME_OVER_SCORE_LABEL_FONT_SIZE);
        this.gameOverBestLabel = initialiseLabel(GAME_OVER_BEST_LABEL_FONT_SIZE);
        this.replayLabel = initialiseLabel(REPLAY_LABEL_FONT_SIZE, REPLAY_LABEL_TEXT);
        this.stage.addActor(this.walls);
        this.stage.addActor(this.ground);
        this.stage.addActor(this.block);
        this.stage.addActor(this.scoreLabel);
        this.stage.addActor(this.gameOverLabel);
        this.stage.addActor(this.gameOverScoreLabel);
        this.stage.addActor(this.gameOverBestLabel);
        this.stage.addActor(this.replayLabel);
        startNewGame();
    }

    private void startNewGame() {
        score = 0;
        scoreLabel.setVisible(true);
        block.reset();
        resetGround();
        resetWalls();
        gameOverLabel.setVisible(false);
        gameOverScoreLabel.setVisible(false);
        gameOverBestLabel.setVisible(false);
        replayLabel.setVisible(false);
        gameOver = false;
    }

    /*
     * Shift all ground sections left to the beginning of the world.
     */
    private void resetGround() {
        float x = 0;
        for (Actor actor : ground.getChildren()) {
            actor.setPosition(x, 0);
            x += actor.getWidth();
        }
    }

    // DONE UP TO HERE
    /*
     * Shifts all wall sections left to the beginning of the world.
     */
    private void resetWalls() {
        float x = FIRST_WALL_X_POSITION;
        for (Actor actor : walls.getChildren()) {
            Wall wall = (Wall) actor;
            wall.setPosition(x, -200 - (RANDOM.nextInt(300)));
            wall.setPassed(false);
            x += wall.getWidth() + WALL_SPACING;
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        addGroundSections();
        addWalls();
    }

    /*
     * Get number of ground sections needed to cover ground and add
     * sections accordingly.
     */
    private void addGroundSections() {
        int needed = (int) Math.ceil(camera.viewportWidth / assets.groundSectionTexture.getWidth()) + 1;
        while (needed > ground.getChildren().size) {
            addGroundSection();
        }
    }

    private void addGroundSection() {
        GroundSection groundSection = new GroundSection(assets.groundSectionTexture);
        setGroundSectionPosition(groundSection);
        ground.addActor(groundSection);
    }

    /*
     * Place new ground section after the last ground section (if one exists).
     * For example, assume that [1, 2, 3] represents the sections that make up the ground
     * and a new section 4 is added. 4's position will then be set after 3.
     */
    private void setGroundSectionPosition(GroundSection groundSection) {
        float x;
        if (!ground.hasChildren()) {
            x = 0;
        } else {
            Actor last = ground.getChildren().peek();
            x = last.getX() + last.getWidth();
        }
        groundSection.setPosition(x, 0);
    }

    private void addWalls() {
        // do better calculation
        int needed = 40;
        while (needed > walls.getChildren().size) {
            addWall();
        }
    }

    private void addWall() {
        Wall wall = new Wall(assets.wallTexture);
        setWallPosition(wall);
        walls.addActor(wall);
    }

    private void setWallPosition(Wall wall) {
        float x;
        if (!walls.hasChildren()) {
            x = FIRST_WALL_X_POSITION;
        } else {
            Actor last = walls.getChildren().peek();
            x = last.getX() + last.getWidth() + 250;
        }
        wall.setPosition(x, -200 - (RANDOM.nextInt(300)));
    }

    @Override
    void handleUserInput() {
        if (isJustTouched() || isSpaceJustPressed()) {
            if (gameOver) {
                startNewGame();
            } else {
                block.jump();
            }
        }
    }

    @Override
    void update(float delta) {
        updateCamera();
        updateScoreLabel();

        //only do this stuff if not game over
        handleCollisions();

        repositionGround();

        //reposition walls


        // leave game over stuff until the end
        if (gameOver) {
            scoreLabel.setVisible(false);
            updateGameOverLabel();
        }
    }

    private void updateCamera() {
        camera.position.x = (int) block.getX() + (camera.viewportWidth * .5f) - block.getWidth();
        camera.update();
    }

    private void updateScoreLabel() {
        scoreLabel.setText(Integer.toString(score));
        float x = camera.position.x - (scoreLabel.getWidth() / 2);
        float y = camera.position.y + (camera.viewportHeight / 2) - scoreLabel.getHeight() - SCORE_LABEL_Y_OFFSET;
        scoreLabel.setPosition(x, y);
    }

    private void handleCollisions() {
        handleGroundCollision();
        handleWallCollision();
        // if (block.bounds.overlaps(ceiling))
        //     block.fall();
    }

    private void handleGroundCollision() {
        for (Actor actor : ground.getChildren()) {
            GroundSection groundSection = (GroundSection) actor;
            if (block.bounds.overlaps(groundSection.bounds)) {
                block.stop();
                gameOver = true;
                if (score > best) {
                    best = score;
                    preferences.putInteger("best", best);
                    preferences.flush();
                }
            }
        }
    }

    private void handleWallCollision() {
        for (Actor actor : walls.getChildren()) {
            Wall wall = (Wall) actor;
            if (block.bounds.overlaps(wall.topWallBounds) || block.bounds.overlaps(wall.bottomWallBounds)) {
                block.fall();
                gameOver = true;
                if (score > best) {
                    best = score;
                    preferences.putInteger("best", best);
                    preferences.flush();
                }
            } else if (block.bounds.overlaps(wall.scoreBounds)) {
                if (wall.setPassed(true)) {
                    score++;
                }
            }
        }
    }

    /*
     * Makes the first ground section the last ground section if it has disappeared off screen.
     * For example, assume that [1, 2, 3, 4] represents the sections that make up the ground.
     * When 1 is no longer visible on the screen it will be moved to the end like so [2, 3, 4, 1].
     */
    private void repositionGround() {
        GroundSection first = (GroundSection) (ground.getChildren().first());
        if ((first.getX() + first.getWidth()) < (camera.position.x - (camera.viewportWidth / 2))) {
            setGroundSectionPosition(first);
            ground.removeActor(first);
            ground.addActor(first);
        }
    }

    private void updateGameOverLabel() {
        gameOverScoreLabel.setText(String.format(GAME_OVER_SCORE_LABEL_TEXT, score));
        gameOverScoreLabel.pack();
        gameOverBestLabel.setText(String.format(GAME_OVER_BEST_LABEL_TEXT, best));
        gameOverBestLabel.pack();
        updateGameOverLabelPosition();
        gameOverLabel.setVisible(true);
        gameOverScoreLabel.setVisible(true);
        gameOverBestLabel.setVisible(true);
        replayLabel.setVisible(true);
    }

    private void updateGameOverLabelPosition() {
        float x = camera.position.x - (gameOverLabel.getWidth() * .5f);
        float y = (viewport.getWorldHeight() * .666f) - gameOverLabel.getHeight();
        gameOverLabel.setPosition(x, y);
        gameOverScoreLabel.setPosition(camera.position.x - (gameOverScoreLabel.getWidth() * .5f), gameOverLabel.getY() - gameOverScoreLabel.getHeight() - 30f);
        gameOverBestLabel.setPosition(camera.position.x - (gameOverBestLabel.getWidth() * .5f), gameOverScoreLabel.getY() - gameOverBestLabel.getHeight() - 30f);
        replayLabel.setPosition(camera.position.x - (replayLabel.getWidth() * .5f), gameOverBestLabel.getY() - replayLabel.getHeight() - 30f);
    }

}