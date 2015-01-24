package wdwdn.entity;

/**
 * Created by Simon on 1/24/2015.
 */
public class Player extends DynamicEntity {
    public Player(float x, float y) {
        super(x, y, 1, 1);
        bounds.width /=2;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        sprite.setFlip(getVelocity().x > 0, false);
    }
}
