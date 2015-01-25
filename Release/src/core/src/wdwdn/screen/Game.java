
package wdwdn.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import wdwdn.Assets;

public abstract class Game implements ApplicationListener {
    private Screen screen;
    private float delta;
    private float dt = 1 / 60f;
    private float accu = 0;
    private SpriteBatch batch;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    @Override
    public void dispose() {
        if (screen != null) {
            batch.dispose();
            screen.dispose();
        }
        // Dispose Assets
        Assets.dispose();
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
    }

    @Override
    public void render() {
        if (screen != null) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glClearColor(1, 1, 1, 1);
            delta = Gdx.graphics.getDeltaTime();

            if (delta > 0.08f) delta = 0.08f; // note: max frame time to avoid spiral of death

            accu += delta;
            if (accu >= dt) {
                screen.update(delta);
                screen.present(dt);
                accu -= dt;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); //Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
        }
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);
    }

    /**
     * Sets the current screen. {@link Screen#hide()} is called on any old screen, and {@link Screen#show()} is called on the new
     * screen, if any.
     *
     * @param screen may be {@code null}
     */
    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.dispose();
        this.screen = screen;

        if (this.screen != null) {

            this.screen.setGame(this);
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }
    }

    /**
     * @return the currently active {@link Screen}.
     */
    public Screen getScreen() {
        return screen;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
