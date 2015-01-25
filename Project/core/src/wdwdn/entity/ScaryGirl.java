package wdwdn.entity;

import wdwdn.Assets;
import wdwdn.World;

public class ScaryGirl extends Enemy {

	float timer;

	public ScaryGirl(World world, float x, float y,
			float radius) {
		super(world, x, y, 1, 1, radius);

	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (stopMoving == true) {
			timer += delta;

			if (timer > 3) {
				remove();
				Assets.playSound(Assets.girlscream, .23f);
			}
		}

	}

	protected void updateRange(float range) {
		if (range < 1.5f && !stopMoving) {

			stopMoving = true;
			world.startFlashing(3.1f);

		}

	}

}
