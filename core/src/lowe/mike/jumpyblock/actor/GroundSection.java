package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * {@code GroundSection} instances are drawn across the ground of the
 * world. Multiple instances are created to cover the entire ground.
 *
 * @author Mike Lowe
 */
public final class GroundSection extends Image {

    public final Rectangle bounds = new Rectangle();

    /**
     * Creates a new {@code GroundSection} instance given a {@link Texture}.
     *
     * @param texture the {@link Texture} of the {@code GroundSection}
     */
    public GroundSection(Texture texture) {
        super(texture);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }

}