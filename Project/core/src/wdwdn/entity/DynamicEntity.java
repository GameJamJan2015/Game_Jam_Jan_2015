package wdwdn.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon on 1/24/2015.
 */
public class DynamicEntity extends GameEntity {
    private Vector2 velocity;

    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.velocity = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        position.x += velocity.x * delta;
        position.y += velocity.x * delta;
    }
}
