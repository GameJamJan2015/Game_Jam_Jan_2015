package wdwdn;

import wdwdn.screen.Game;
import wdwdn.screen.GameScreen;
import wdwdn.screen.SplashScreen;

public class WDWDNStarter extends Game {

	@Override
	public void create() {
		super.create();
		setScreen(new SplashScreen());
	}
}
