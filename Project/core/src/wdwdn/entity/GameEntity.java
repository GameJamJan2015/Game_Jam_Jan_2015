package wdwdn.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon on 1/23/2015.
 */
public class GameEntity {
    protected Vector2 position;
    private Rectangle bounds;
    private Animation anim;

    public GameEntity(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width/2, y - height/2, width, height);
    }

    public void update(float delta) {
        this.bounds.x = position.x - bounds.width/2;
        this.bounds.y = position.y - bounds.height /2;
    }

    // GETTTERS
    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getAngle() {
        return 0;
    }

    // SETTERS
    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }
}
