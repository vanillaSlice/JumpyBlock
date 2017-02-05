package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import lowe.mike.jumpyblock.Assets;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */
public final class MainMenuScreen extends AbstractScreen {

    private static final float SPACING = 50f;

    private final Image title;
    private final ImageButton playButton;

    /**
     * Creates a new {@code MainMenuScreen} given a {@link ScreenManager}, {@link Assets}
     * and a {@link SpriteBatch}.
     *
     * @param screenManager the {@link ScreenManager} used to manager game {@link Screen}s
     * @param assets        {@link Assets} containing assets used in the {@link Screen}
     * @param spriteBatch   {@link SpriteBatch} to add sprites to
     */
    public MainMenuScreen(ScreenManager screenManager, Assets assets, SpriteBatch spriteBatch) {
        super(screenManager, assets, spriteBatch);
        assets.loadMainMenuScreenAssets();
        this.title = new Image(assets.titleTexture);
        this.playButton = initialisePlayButton();
        this.stage.addActor(this.title);
        this.stage.addActor(this.playButton);
    }

    private ImageButton initialisePlayButton() {
        ImageButton playButton = new ImageButton(getPlayButtonStyle());
        addPlayButtonListener(playButton);
        return playButton;
    }

    private ImageButton.ImageButtonStyle getPlayButtonStyle() {
        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.imageUp = getDrawableFromTexture(assets.playButtonUpTexture);
        playButtonStyle.imageOver = getDrawableFromTexture(assets.playButtonOverTexture);
        playButtonStyle.imageDown = playButtonStyle.imageOver;
        return playButtonStyle;
    }

    private void addPlayButtonListener(ImageButton playButton) {
        playButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchToGameScreen();
            }

        });
    }

    private void switchToGameScreen() {
        screenManager.setScreen(new GameScreen(screenManager, assets, spriteBatch));
    }

    @Override
    void handleUserInput() {
        if (isSpaceJustPressed()) {
            switchToGameScreen();
        }
    }

    @Override
    void update(float delta) {
        updateTitlePosition();
        updateButtonPosition();
    }

    private void updateTitlePosition() {
        // center
        float x = (viewport.getWorldWidth() - title.getWidth()) * .5f;
        // two thirds of the way up the screen
        float y = (viewport.getWorldHeight() - title.getHeight()) * .666f;
        title.setPosition(x, y);
    }

    private void updateButtonPosition() {
        // center
        float x = (viewport.getWorldWidth() - playButton.getWidth()) * .5f;
        // underneath the title
        float y = title.getY() - playButton.getHeight() - SPACING;
        playButton.setPosition(x, y);
    }

    @Override
    void disposeAssets() {
        assets.diposeMainMenuScreenAssets();
    }

}