package wdwdn.entity;

import wdwdn.World;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon on 1/24/2015.
 */
public class DynamicEntity extends GameEntity {
    private Vector2 velocity;

    public DynamicEntity(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
        this.velocity = new Vector2();
    }

    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        super.update(delta);
    }

    public void setVelocity(float x, float y) {
        if (y > 0)
            setAnimation("walkup");
        if (y < 0)
            setAnimation("walkdown");
        if (x != 0)
            setAnimation("walk");

        if (velocity.y > 0 && y == 0)
            setAnimation("idleup");

        if (velocity.y < 0 && y == 0)
            setAnimation("idledown");

        if (velocity.x != 0 && x == 0)
            setAnimation("idledown");


        this.velocity.x = x;
        this.velocity.y = y;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
