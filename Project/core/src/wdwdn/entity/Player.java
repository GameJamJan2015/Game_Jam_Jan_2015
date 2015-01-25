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
        	if (lifeBattery > 10)
        		setLifeBattery(lifeBattery - delta * 12);
        	
        	if (lifeBattery < 10)
        		recharge = true;
        	
        } else {
        	setLifeBattery(lifeBattery + delta * 1.5f);
        }
        
        Gdx.app.log("", "suuppp: " + lifeBattery);
        
        sprite.setFlip(getVelocity().x < 0, false);
    }
    
    public boolean canUseLight() {
    	return (recharge ? lifeBattery > 20 : lifeBattery > 10);
    }
    
    public void setLifeBattery(float lifeBattery) {
		this.lifeBattery = lifeBattery;
		
		if (this.lifeBattery > 20) {
			recharge = false;
		}
		
		if(this.lifeBattery > 100)
			this.lifeBattery = 100;
	}
    
    public float getLifeBattery() {
		return lifeBattery;
	}
}
