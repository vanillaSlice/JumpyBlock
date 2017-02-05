package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import lowe.mike.jumpyblock.Assets;
import lowe.mike.jumpyblock.actor.Block;
import lowe.mike.jumpyblock.actor.Ground;
import lowe.mike.jumpyblock.actor.Wall;

/**
 * Game screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
public final class GameScreen extends AbstractScreen {

    //sprites here
    private final Block block;

    private final Block block2;

    private final Wall wall1 = new Wall();
//    private final Wall wall2 = new Wall();
//    private final Wall wall3 = new Wall();
//    private final Wall wall4 = new Wall();
//    private final Ground ground = new Ground();

    /**
     * Creates a new {@code GameScreen} given a {@link ScreenManager}, {@link Assets}
     * and a {@link SpriteBatch}.
     *
     * @param screenManager the {@link ScreenManager} used to manager game {@link Screen}s
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     */
    public GameScreen(ScreenManager screenManager, Assets assets, SpriteBatch spriteBatch) {
        super(screenManager, assets, spriteBatch);
        assets.loadGameScreenAssets();
        block = new Block(assets.blockTexture, new Vector2(50, 300));
        block2 = new Block(assets.blockTexture, new Vector2(1050, 300));
        block2.reset();

        //TODO sort these out
        this.stage.addActor(this.block);
        this.stage.addActor(this.block2);
        this.stage.addActor(this.wall1);
//        this.stage.addActor(this.wall2);
//        this.stage.addActor(this.wall3);
//        this.stage.addActor(this.wall4);
//        this.stage.addActor(this.ground);

        startNewGame();
    }

    private void startNewGame() {
        block.reset();

        wall1.setPosition(300, -300);
//        wall2.setPosition(550, -300);
//        wall3.setPosition(800, -300);
//        wall4.setPosition(1050, -300);
    }

    @Override
    void handleUserInput() {
        // check for death
        if (isJustTouched() || isSpaceJustPressed())
            block.jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
            startNewGame();
    }

    @Override
    void update(float delta) {
        updateCamera();
        updateGroundPosition();

        if (block.bounds.getX() > 250)
            block.kill();
    }

    private void updateCamera() {
        camera.position.x = (int) block.getX() + (camera.viewportWidth * .5f) - block.getWidth();
        camera.update();
    }

    private void updateGroundPosition() {
        // fills width of screen in each frame
     //   ground.setPosition(camera.position.x - (camera.viewportWidth * .5f), 0);
    }

    @Override
    void disposeAssets() {
        assets.disposeGameScreenAssets();
    }

}