package wdwdn.entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.AddListenerAction;

import com.badlogic.gdx.utils.Array;
import wdwdn.Assets;
import wdwdn.World;


public class Shadow extends Enemy {

	public Shadow(World world, float x, float y,
			float radius) {
		super(world, x, y, .7f, 1, radius);

		Array<TextureRegion> walk = new Array<TextureRegion>();
		for (int i = 0; i < 22; i++) {
			walk.add(new TextureRegion(Assets.shadow, i * 64, 0, 64, 64));
		}
		addAnimation("walk", new Animation(.1f, walk));
		setAnimation("walk");

		speed = 0.5f;
	}
	public void update(float delta) {
		super.update(delta);
		
		//if(getRange(world.getPlayer().getPosition()) < 3.0f)
		//{
			world.getPlayer().addLifeBattery(-Math.max(radius/2 - getRange(world.getPlayer().getPosition()), 0 ) * delta * 2.0f);
		//}
			
		
		
	}
	@Override
	protected void updateRange(float range) {
		if (range < 1.0f) {
			world.startFlashing(3.1f);
			remove();
		}
	}

}
