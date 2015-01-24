package wdwdn.entity;

import javax.media.j3d.Texture2D;

import wdwdn.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class Dialog {
	String[] texts;

	int index = 0;
	Vector2 pos;
	float time;
	public boolean removeDialog;

	public Dialog(String... texts) {
		this.texts = texts;
	}

	public void update(float delta) {

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {

			if (index + 1>= texts.length) {

				removeDialog = true;
			} else {
				index++;
			}
		}

	}

	public void draw(Batch batch) {
		batch.draw(Assets.pixel, 200, 0, 1280 - 400, 200);

		Assets.font.setColor(Color.WHITE);
		Assets.font.setScale(.4f);
		Assets.font.drawMultiLine(batch, texts[index], 300, 150, 1280 - 600,
				HAlignment.CENTER);
		// sbatch.Draw()

		// set font white

	}

}
