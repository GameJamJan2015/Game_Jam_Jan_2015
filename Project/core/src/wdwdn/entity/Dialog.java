package wdwdn.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import wdwdn.Assets;

public class Dialog {
    String[] texts;

    int index = 0;
    Vector2 pos;
    float time;
    public boolean removeDialog;
    boolean keyFix = false;

    public Dialog(String... texts) {
        this.texts = texts;

        if (Gdx.input.isKeyPressed(Keys.SPACE))
            keyFix = true;
    }

    public void update(float delta) {
        if (!Gdx.input.isKeyPressed(Keys.SPACE))
            keyFix = false;

        time += delta;

        if ((Gdx.input.isKeyJustPressed(Keys.SPACE) || time > 5.5f ) && keyFix != true) {
            if (index + 1 >= texts.length) {
                removeDialog = true;
            } else {
                index++;
                time = 0;
            }
        }
    }

    public void draw(Batch batch) {
        batch.draw(Assets.pixel, 200, 40, 1280 - 400, 160);

        Assets.font.setColor(Color.WHITE);
        Assets.font.setScale(.4f);
        Assets.font.drawWrapped(batch, texts[index], 300, 150, 1280 - 600,
                HAlignment.CENTER);
    }

}
