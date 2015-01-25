package wdwdn.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import wdwdn.Assets;
import wdwdn.World;

public class ScaryGirl extends Enemy {

	float timer;
boolean hasJumped = false;

	public ScaryGirl(World world, float x, float y,
			float radius) {
		super(world, x, y, 1, 1, radius);
		addAnimation("idle", new Animation(1, new TextureRegion(Assets.characters, 192,0, 64,64)));
		setAnimation("idle");
		
		stopMoving = true;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (stopMoving == true) {
			timer += delta;

			if (timer > 4) {
				if(getRange(world.getPlayer().getPosition() ) < 2.5f)
					world.getPlayer().addLifeBattery(-35);
				remove();
				Assets.playSound(Assets.girlscream, .23f);
			} else if (timer > 2 && !hasJumped) {
				hasJumped = true;
				world.startFlashing(2.34f);
				position = world.getPlayer().getPosition().cpy().add(world.getPlayer().getVelocity());
			}
		}

	}

	protected void updateRange(float range) {
		//if (range < 1.5f && !stopMoving) {

			

		//}

	}

}
