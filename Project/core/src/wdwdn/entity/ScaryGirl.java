package wdwdn.entity;

import wdwdn.World;

public class ScaryGirl extends Enemy {

	float timer;

	public ScaryGirl(World world, float x, float y, float width, float height,
			float radius) {
		super(world, x, y, width, height, radius);

	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (stopMoving == true) {
			timer += delta;

			if (timer > 3)
				remove();
		}

	}

	protected void updateRange(float range) {
		if (range < 1.5f && !stopMoving) {

			stopMoving = true;
			world.startFlashing(3.1f);

		}

	}

}
