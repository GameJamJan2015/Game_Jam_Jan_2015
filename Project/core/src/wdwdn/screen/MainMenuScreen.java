package wdwdn.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import wdwdn.Assets;

/**
 * Created by Simon on 1/25/2015.
 */
public class MainMenuScreen extends Screen{

    @Override
    public void show() {

    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            setScreen(new GameScreen(4));
    }

    @Override
    public void present(float delta) {
        getBatch().begin();
        getBatch().draw(Assets.menu, 0 ,0, 1280, 720);
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
