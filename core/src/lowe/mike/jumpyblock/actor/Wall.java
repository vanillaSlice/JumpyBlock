package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * {@code Wall} instances represent the wall obstacles in the world.
 *
 * @author Mike Lowe
 */
public final class Wall extends Group {

    private static final int SPACING = 150;

    public final Rectangle topWallBounds = new Rectangle();
    public final Rectangle bottomWallBounds = new Rectangle();
    public final Rectangle scoreBounds = new Rectangle();

    private final Texture texture;
    private final Actor topWall;
    private final Actor bottomWall;
    private boolean isPassed;

    /**
     * Creates a new {@code Wall} instance given a {@link Texture}.
     *
     * @param texture the {@link Texture} of the {@code Wall}
     */
    public Wall(Texture texture) {
        this.texture = texture;
        this.topWall = new Image(this.texture);
        this.bottomWall = new Image(this.texture);
        addActor(this.topWall);
        addActor(this.bottomWall);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        topWall.setPosition(bottomWall.getX(), bottomWall.getY() + bottomWall.getHeight() + SPACING);
        topWallBounds.set(x, y + topWall.getHeight() + SPACING, topWall.getWidth(), topWall.getHeight());
        bottomWallBounds.set(x, y, bottomWall.getWidth(), bottomWall.getHeight());
        scoreBounds.set(x + bottomWall.getWidth(), y + bottomWall.getHeight(), 0, SPACING);
    }

    /**
     * Sets if this {@code Wall} has been passed.
     *
     * @param isPassed if this {@code Wall} has been passed
     * @return {@code true} if the value is changed; {@code false} otherwise
     */
    public boolean setPassed(boolean isPassed) {
        if (this.isPassed == isPassed) {
            return false;
        }
        this.isPassed = isPassed;
        return true;
    }

}