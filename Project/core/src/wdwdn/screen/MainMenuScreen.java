package wdwdn.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Simon on 1/25/2015.
 */
public class MainMenuScreen extends Screen{

    @Override
    public void show() {

    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            setScreen(new GameScreen(1));
    }

    @Override
    public void present(float delta) {
        getBatch().begin();

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
