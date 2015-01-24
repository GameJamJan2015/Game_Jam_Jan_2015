package wdwdn.entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import wdwdn.Assets;
import wdwdn.World;


public class Enemy extends DynamicEntity {

	private World world;
	public float radius;
	
	public Enemy(World world, float x, float y, float width, float height, float radius) {
		super(x, y, width, height);
		
		this.world = world;
		this.radius = radius;
		
		addAnimation("idle", new Animation(1, Assets.pixel));
		setAnimation("idle");
	}
	public void update(float delta){
		super.update(delta);
		
		float range = getRange(world.getPlayer().getPosition());
		if(range < radius)
		{
			Vector2 differenceToPlayer = world.getPlayer().getPosition().cpy().sub(position);
			differenceToPlayer.nor();
			
			position.add(differenceToPlayer.scl(delta * 1));
			
			if (range < 1) {
				remove();
			}
		}
	}

	public float getRange(Vector2 pos){
		return pos.cpy().sub(position).len();
	}
	
	
	
}
