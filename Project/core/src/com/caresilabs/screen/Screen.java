package com.caresilabs.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen {
	private Game game;
	/** Called when this screen becomes the current screen for a {@link Game}. */
	public abstract void show ();
	
	/** Called when the screen should update itself.
	 * @param delta The time in seconds since the last render. */
	public abstract void update (float delta);
	
	/** Called when the screen should render itself.
	 * @param delta The time in seconds since the last render. */
	public abstract void present (float delta);

	/** @see com.badlogic.gdx.ApplicationListener#resize(int, int) */
	public abstract void resize (int width, int height);

	/** @see com.badlogic.gdx.ApplicationListener#pause() */
	public abstract void pause ();

	/** @see com.badlogic.gdx.ApplicationListener#resume() */
	public abstract void resume ();

	/** Called when this screen should release all resources. */
	public abstract void dispose ();
	
	protected void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public SpriteBatch getBatch() {
		return game.getBatch();
	}
	
	public void setScreen (Screen screen) {
		game.setScreen(screen);
	}
}