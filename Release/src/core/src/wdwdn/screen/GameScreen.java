package wdwdn.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

import wdwdn.Assets;
import wdwdn.LevelLoader;
import wdwdn.World;
import wdwdn.WorldRenderer;
import wdwdn.entity.Dialog;

/**
 * Created by Simon on 1/23/2015.
 */
public class GameScreen extends Screen {
	private World world;
	private WorldRenderer renderer;
	private int level;

	public static Dialog dialog;
	OrthographicCamera uiCam;

	public GameScreen(int level) {
		this.level = level;
	}

	@Override
	public void show() {
		this.world = new World(LevelLoader.LoadMap(level));
		this.renderer = new WorldRenderer(world);
		this.uiCam = new OrthographicCamera(1280, 720);
		uiCam.position.x = 1280 / 2;
		uiCam.position.y = 720 / 2;
	}

	@Override
	public void update(float delta) {
		world.update(delta);

		if (dialog != null)
			dialog.update(delta);

		if (world.isNextLevel) {
			setScreen(new NextLevelScreen(level + 1));
		}

		if (world.getPlayer().getLifeBattery() <= 0)
			setScreen(new GameOverScreen());

		if (dialog != null && dialog.removeDialog == true) {
			dialog = null;
		}
	}

	@Override
	public void present(float delta) {
		// getBatch().setProjectionMatrix(renderer.getCamera().combined);
		renderer.render(getBatch());

		uiCam.update();
		getBatch().setProjectionMatrix(uiCam.combined);
		getBatch().begin();

		if (dialog != null)
			dialog.draw(getBatch());
		
		// Draw life bar
        getBatch().draw(Assets.region, 40, 600, 300, 64);
        getBatch().setColor(0,1,0,1);
        getBatch().draw(Assets.pixel, 56, 621, (world.getPlayer().getLifeBattery()/100)*225, 21);
        getBatch().setColor(1, 1, 1, 1);

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
