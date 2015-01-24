package wdwdn.entity;

import wdwdn.World;

/**
 * Created by Simon on 1/24/2015.
 */
public class Player extends DynamicEntity {
	
	float life = 100;
	
    public Player(World world, float x, float y) {
        super(world, x, y, 1, 1);
        bounds.width /=2;
        bounds.height*=.75f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        
        sprite.setFlip(getVelocity().x < 0, false);
    }
}
