package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import lowe.mike.jumpyblock.Assets;
import lowe.mike.jumpyblock.JumpyBlockGame;

/**
 * Provides a base class for the {@link Screen}s in the game.
 *
 * @author Mike Lowe
 */
abstract class AbstractScreen extends ScreenAdapter {

    final ScreenManager screenManager;
    final Assets assets;
    final SpriteBatch spriteBatch;
    final OrthographicCamera camera = new OrthographicCamera(JumpyBlockGame.WIDTH, JumpyBlockGame.HEIGHT);
    final Viewport viewport;
    final Stage stage;

    private boolean isPaused;

    /**
     * Creates a new {@code AbstractScreen} given a {@link ScreenManager}, {@link Assets}
     * and a {@link SpriteBatch}.
     *
     * @param screenManager the {@link ScreenManager} used to manager game {@link Screen}s
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     */
    AbstractScreen(ScreenManager screenManager, Assets assets, SpriteBatch spriteBatch) {
        this.screenManager = screenManager;
        this.assets = assets;
        this.spriteBatch = spriteBatch;
        this.camera.setToOrtho(false);
        this.viewport = new ExtendViewport(JumpyBlockGame.WIDTH, JumpyBlockGame.HEIGHT, this.camera);
        this.stage = new Stage(this.viewport, this.spriteBatch);
        Gdx.input.setInputProcessor(this.stage);
    }

    /**
     * @param texture the {@link Texture} to create a {@link Drawable} from
     * @return a {@link Drawable}
     */
    final Drawable getDrawableFromTexture(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public final void pause() {
        isPaused = true;
    }

    @Override
    public final void resume() {
        isPaused = false;
    }

    @Override
    public final void render(float delta) {
        if (isPaused)
            return;
        handleUserInput();
        clearScreen();
        update(delta);
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Subclasses should determine how to handle user input.
     */
    abstract void handleUserInput();

    /**
     * @return {@code true} if space has just been pressed
     */
    final boolean isSpaceJustPressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    /**
     * @return {@code true} if the screen has just been touched
     */
    final boolean isJustTouched() {
        return Gdx.input.justTouched();
    }

    private void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 0);
    }

    /**
     * Subclasses should determine how to update the {@link Screen} in each frame.
     *
     * @param delta time in seconds since the last frame
     */
    abstract void update(float delta);

    @Override
    public final void dispose() {
        stage.dispose();
        disposeAssets();
    }

    /**
     * Subclasses should select what to dispose from {@link Assets}.
     */
    abstract void disposeAssets();

}