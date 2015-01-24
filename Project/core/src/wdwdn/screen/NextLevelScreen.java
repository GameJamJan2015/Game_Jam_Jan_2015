package wdwdn.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import wdwdn.Assets;

/**
 * Created by Simon on 1/24/2015.
 */
public class NextLevelScreen extends Screen {

    String[] texts = new String[]{"You're next", "moahaha"};
    int level;
    float time;

    public NextLevelScreen(int level) {
        this.level = level;
    }

    @Override
    public void show() {

    }

    @Override
    public void update(float delta) {
        time += delta;

        if (time > 3.5f) {
            setScreen(new GameScreen(level));
        }
    }

    @Override
    public void present(float delta) {
        getBatch().begin();
        Assets.font.setColor(0, 0, 0, 1);
        Assets.font.setScale(.7f);
        Assets.font.drawMultiLine(getBatch(), texts[level - 2], 0, 720 / 2f, 1280, BitmapFont.HAlignment.CENTER);
        Assets.font.setColor(1, 1, 1, 1);
        getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
