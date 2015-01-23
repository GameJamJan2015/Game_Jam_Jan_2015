package wdwdn;

import wdwdn.screen.Game;
import wdwdn.screen.GameScreen;

public class WDWDNStarter extends Game {

	@Override
	public void create() {
		super.create();
		setScreen(new GameScreen());
	}
}
