package wdwdn.screen;

import wdwdn.Assets;
import wdwdn.World;
import wdwdn.WorldRenderer;

/**
 * Created by Simon on 1/23/2015.
 */
public class GameScreen extends Screen {
    private World world;
    private WorldRenderer renderer;

    @Override
    public void show() {
        this.world = new World();
        this.renderer = new WorldRenderer(world);
    }

    @Override
    public void update(float delta) {
        world.update(delta);
    }

    @Override
    public void present(float delta) {
        renderer.render(getBatch());

        getBatch().begin();
        getBatch().draw(Assets.region, 0,0, 42,42);
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
