package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by mikelowe on 04/02/2017.
 */

public final class Wall extends Actor {

    private static final String TEXTURE_PATH = "wall.png";

    private final Texture texture;

    public Wall() {
        this.texture = new Texture(Gdx.files.internal(TEXTURE_PATH));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    public void dispose() {
        texture.dispose();
    }

}