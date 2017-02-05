package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Mike Lowe
 */
public final class Ground extends Actor {

    private static final String TEXTURE_PATH = "ground.png";

    private final Texture texture;

    public Ground() {
        this.texture = new Texture(Gdx.files.internal(TEXTURE_PATH));
        this.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        setWidth(this.texture.getWidth());
        setHeight(this.texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), 0, 0, texture.getWidth(), texture.getHeight());
      //  batch.draw(texture, getX(), getY());
    }

    public void dispose() {
        texture.dispose();
    }

}