package wdwdn.screen;

import com.badlogic.gdx.Gdx;
import wdwdn.Assets;

public class SplashScreen extends Screen {

	@Override
	public void show() {
		Assets.load();
	}

	@Override
	public void update(float delta) {
		if (Assets.manager.update()) {
			Assets.loadFinished();
			Assets.playMusic();
			Gdx.input.setCursorCatched(true);
			setScreen(new MainMenuScreen());
		}
	}

	@Override
	public void present(float delta) {
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
