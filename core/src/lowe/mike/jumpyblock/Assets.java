package lowe.mike.jumpyblock;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import lowe.mike.jumpyblock.screen.GameScreen;
import lowe.mike.jumpyblock.screen.MainMenuScreen;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s,
 * used in the <i>Jumpy Block</i> game.
 *
 * @author Mike Lowe
 */
public final class Assets {

    // global
    private static final AssetDescriptor<Music> MUSIC_ASSET_DESCRIPTOR
            = new AssetDescriptor<Music>("music.mp3", Music.class);
    private static final float MUSIC_VOLUME = 0.2f;

    // main menu screen
    private static final AssetDescriptor<Texture> TITLE_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("title.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAY_BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("play-button-up.png", Texture.class);
    private static final AssetDescriptor<Texture> PLAY_BUTTON_OVER_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("play-button-over.png", Texture.class);

    // game screen
    private static final AssetDescriptor<Texture> BLOCK_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("block.png", Texture.class);


    // global assets
    public Music music;

    // main menu screen assets
    public Texture titleTexture;
    public Texture playButtonUpTexture;
    public Texture playButtonOverTexture;

    // game screen assets
    public Texture blockTexture;

    private final AssetManager assetManager = new AssetManager();

    /**
     * Load assets used throughout the game.
     */
    public void loadGlobalAssets() {
        loadAssets(MUSIC_ASSET_DESCRIPTOR);
        music = assetManager.get(MUSIC_ASSET_DESCRIPTOR);
        music.setVolume(MUSIC_VOLUME);
        music.setLooping(true);
    }

    private void loadAssets(AssetDescriptor<?>... assetDescriptors) {
        for (AssetDescriptor<?> assetDescriptor : assetDescriptors) {
            assetManager.load(assetDescriptor);
        }
        assetManager.finishLoading();
    }

    /**
     * Dispose all global assets.
     */
    public void disposeGlobalAssets() {
        unloadAssets(MUSIC_ASSET_DESCRIPTOR);
    }

    private void unloadAssets(AssetDescriptor<?>... assetDescriptors) {
        for (AssetDescriptor<?> assetDescriptor : assetDescriptors) {
            assetManager.unload(assetDescriptor.fileName);
        }
    }

    /**
     * Load assets used by the {@link MainMenuScreen}.
     */
    public void loadMainMenuScreenAssets() {
        loadAssets(TITLE_TEXTURE_ASSET_DESCRIPTOR, PLAY_BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR,
                PLAY_BUTTON_OVER_TEXTURE_ASSET_DESCRIPTOR);
        titleTexture = assetManager.get(TITLE_TEXTURE_ASSET_DESCRIPTOR);
        playButtonUpTexture = assetManager.get(PLAY_BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR);
        playButtonOverTexture = assetManager.get(PLAY_BUTTON_OVER_TEXTURE_ASSET_DESCRIPTOR);
    }

    /**
     * Dispose assets used by the {@link MainMenuScreen}.
     */
    public void diposeMainMenuScreenAssets() {
        unloadAssets(TITLE_TEXTURE_ASSET_DESCRIPTOR, PLAY_BUTTON_UP_TEXTURE_ASSET_DESCRIPTOR,
                PLAY_BUTTON_OVER_TEXTURE_ASSET_DESCRIPTOR);
    }

    /**
     * Load assets used by the {@link GameScreen}.
     */
    public void loadGameScreenAssets() {
        loadAssets(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
        blockTexture = assetManager.get(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
    }

    /**
     * Dispose assets used by the {@link GameScreen}.
     */
    public void disposeGameScreenAssets() {
        unloadAssets(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
    }

}