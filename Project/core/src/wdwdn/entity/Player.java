package wdwdn.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import wdwdn.World;
import wdwdn.screen.GameScreen;

/**
 * Created by Simon on 1/24/2015.
 */
public class Player extends DynamicEntity {
	
	private float lifeBattery = 100;
	private boolean recharge;
	
    public Player(World world, float x, float y) {
        super(world, x, y, 1, 1);
        bounds.width /=2;
        bounds.height*=.75f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(world.playerLight.getDistance() > .08f && GameScreen.dialog == null){
        	if (lifeBattery > 15)
        		addLifeBattery(-delta * 12);
        	
        	if (lifeBattery < 15)
        		recharge = true;
        	
        } else {
        	addLifeBattery( delta * 1.95f);
        }
        
        sprite.setFlip(getVelocity().x < 0, false);
    }
    
    public boolean canUseLight() {
    	return (recharge ? lifeBattery > 25 : lifeBattery > 10);
    }
    
    public void addLifeBattery(float value) {
		this.lifeBattery += value;
		
		if (this.lifeBattery > 25) {
			recharge = false;
		}
		
		if(this.lifeBattery > 100)
			this.lifeBattery = 100;
		
		if (this.lifeBattery <= 0)
			this.lifeBattery = 0;
	}
    
    public float getLifeBattery() {
		return lifeBattery;
	}
    
    public float getLifeBatteryPercentage(){
    	
    	return lifeBattery / 100f;
    }
}
