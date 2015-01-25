package wdwdn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import wdwdn.WDWDNStarter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.width = 1280;
		config.height = 720;
		config.fullscreen = true;
		config.title = "The Asylum";
		//config.fullscreen = true;
		new LwjglApplication(new WDWDNStarter(), config);
	}
}
