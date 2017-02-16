package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import lowe.mike.jumpyblock.JumpyBlockGame;

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
    private boolean isFalling;
    private boolean isStopped;

    /**
     * Creates a new {@code Block} instance given a {@link Texture} and
     * a {@link Vector2} starting position.
     *
     * @param texture          the {@link Texture} of the {@code Block}
     * @param startingPosition the {@link Vector2} starting position
     */
    public Block(Texture texture, Vector2 startingPosition) {
        super(texture);
        this.startingPosition = startingPosition;
        reset();
    }

    /**
     * Resets this {@code Block} back to its initial state.
     */
    public void reset() {
        setPosition(startingPosition.x, startingPosition.y);
        velocity.set(FORWARD, 0);
        isFalling = false;
        isStopped = false;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Make this {@code Block} jump. Will only take effect if this {@code Block}
     * is not falling or has not been stopped.
     */
    public void jump() {
        if (!isFalling && !isStopped) {
            velocity.y = JUMP;
        }
    }

    @Override
    public void act(float delta) {
        // don't need to bother calculating new position if stopped
        if (isStopped) {
            return;
        }

        // always add gravity
        velocity.y += GRAVITY;

        // calculate new position
        int x = (int) (getX() + (velocity.x * delta));
        int y = (int) (getY() + (velocity.y * delta));

        // don't let block go through the ceiling
        int ceiling = (int) (JumpyBlockGame.HEIGHT - getHeight());
        if (y > ceiling) {
            y = ceiling;
        }

        setPosition(x, y);
    }

    /**
     * Make this {@code Block} fall. Once this method is called the {@code Block}
     * can no longer jump.
     */
    public void fall() {
        if (!isFalling) {
            isFalling = true;
            velocity.setZero();
        }
    }

    /**
     * Stops this {@code Block} from moving. Once this method is called the {@code Block}
     * can no longer jump.
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;
        }
    }

}