package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * {@code Block} instances are the characters to be controlled
 * by the player.
 *
 * @author Mike Lowe
 */
public final class Block extends Image {

    private static final int GRAVITY = -25;
    private static final int FORWARD = 200;
    private static final int JUMP = 525;

    public final Rectangle bounds = new Rectangle();

    private final Vector2 startingPosition;
    private final Vector2 velocity = new Vector2();
    private boolean isDead;

    /**
     * Creates a new {@code Block} instance given a {@link Texture} and {@link Vector2}.
     *
     * @param texture          the {@link Texture} of the {@code Block}
     * @param startingPosition the starting position of the {@code Block}
     */
    public Block(Texture texture, Vector2 startingPosition) {
        super(texture);
        this.startingPosition = startingPosition;
    }

    /**
     * Resets this {@code Block} back to its initial state.
     */
    public void reset() {
        setPosition(startingPosition.x, startingPosition.y);
        velocity.setZero();
        isDead = false;
    }

    @Override
    protected void positionChanged() {
        bounds.set(getX(), getY(), getWidth(), getWidth());
    }

    /**
     * Make this {@code Block} jump.
     */
    public void jump() {
        if (!isDead) {
            velocity.y = JUMP;
        }
    }

    @Override
    public void act(float delta) {
        velocity.y += GRAVITY;

        int newX = (int) ((isDead) ? getX() : getX() + (int) (FORWARD * delta));
        int newY = (int) (getY() + (int) (velocity.y * delta));

        if (newY < 0)
            newY = 0;

        setPosition(newX, newY);
    }

    /**
     * Kill this {@code Block}.
     */
    public void kill() {
        if (!isDead) {
            isDead = true;
            velocity.y = 0;
        }
    }

}