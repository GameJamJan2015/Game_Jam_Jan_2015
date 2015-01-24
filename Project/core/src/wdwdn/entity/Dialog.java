package wdwdn.entity;

import javax.media.j3d.Texture2D;

import wdwdn.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;



public class Dialog {
	String[] texts;
	
	int index = 0;
	Vector2 pos;
	float time;
	
	
	public Dialog(Texture2D tex, Vector2 pos, String... texts){
		this.texts = texts;
		this.tex = tex;
		this.pos = pos;
	}
	public void update(float delta){
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			
			index ++;
		}
			
	}
	public void draw(Batch batch){
	batch.draw(Assets.region, x, y, wth, hght);
		Assets.font.drawMultiLine(batch, texts[index], 0, 720/2, 1280, HAlignment.CENTER);
		//sbatch.Draw()
		
	}
	
}
