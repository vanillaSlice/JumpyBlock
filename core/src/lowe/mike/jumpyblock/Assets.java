package lowe.mike.jumpyblock;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code Assets} provides access to assets, such as {@link Texture}s,
 * used in the <i>Jumpy Block</i> game.
 *
 * @author Mike Lowe
 */
public final class Assets {

    private static final AssetDescriptor<Music> MUSIC_ASSET_DESCRIPTOR
            = new AssetDescriptor<Music>("music.mp3", Music.class);
    private static final AssetDescriptor<FreeTypeFontGenerator> FONT_GENERATOR_ASSET_DESCRIPTOR
            = new AssetDescriptor<FreeTypeFontGenerator>("font.ttf", FreeTypeFontGenerator.class);
    private static final AssetDescriptor<Texture> BLOCK_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("block.png", Texture.class);
    private static final AssetDescriptor<Texture> GROUND_SECTION_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("ground-section.png", Texture.class);
    private static final AssetDescriptor<Texture> WALL_TEXTURE_ASSET_DESCRIPTOR
            = new AssetDescriptor<Texture>("wall.png", Texture.class);

    private static final float MUSIC_VOLUME = .2f;
    private static final FreeTypeFontGenerator.FreeTypeFontParameter FONT_PARAMETER = new FreeTypeFontGenerator.FreeTypeFontParameter();

    static {
        FONT_PARAMETER.color = Color.BLACK;
        FONT_PARAMETER.borderWidth = 3f;
        FONT_PARAMETER.borderColor = Color.GREEN;
    }

    public Music music;
    public FreeTypeFontGenerator fontGenerator;
    public Texture blockTexture;
    public Texture groundSectionTexture;
    public Texture wallTexture;

    private final AssetManager assetManager = new AssetManager();
    private final FreeTypeFontGeneratorLoader loader = new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver());
    private final Map<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();

    /**
     * Load assets used throughout the game.
     */
    public void load() {
        // use asset manager to load in assets
        assetManager.setLoader(FreeTypeFontGenerator.class, loader);
        assetManager.load(MUSIC_ASSET_DESCRIPTOR);
        assetManager.load(FONT_GENERATOR_ASSET_DESCRIPTOR);
        assetManager.load(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
        assetManager.load(GROUND_SECTION_TEXTURE_ASSET_DESCRIPTOR);
        assetManager.load(WALL_TEXTURE_ASSET_DESCRIPTOR);

        // wait for asset manager to finish loading
        assetManager.finishLoading();

        // assign loaded assets to variables
        music = assetManager.get(MUSIC_ASSET_DESCRIPTOR);
        music.setVolume(MUSIC_VOLUME);
        music.setLooping(true);
        fontGenerator = assetManager.get(FONT_GENERATOR_ASSET_DESCRIPTOR);
        blockTexture = assetManager.get(BLOCK_TEXTURE_ASSET_DESCRIPTOR);
        groundSectionTexture = assetManager.get(GROUND_SECTION_TEXTURE_ASSET_DESCRIPTOR);
        wallTexture = assetManager.get(WALL_TEXTURE_ASSET_DESCRIPTOR);
    }

    /**
     * @param size size of the font to generate
     * @return a {@link BitmapFont}
     */
    public BitmapFont generateFont(int size) {
        // use cached version if it exists
        if (fonts.containsKey(size)) {
            return fonts.get(size);
        }
        FONT_PARAMETER.size = size;
        BitmapFont font = fontGenerator.generateFont(FONT_PARAMETER);
        fonts.put(size, font);
        return font;
    }

    /**
     * Dispose all assets.
     */
    public void dispose() {
        assetManager.dispose();
    }

}