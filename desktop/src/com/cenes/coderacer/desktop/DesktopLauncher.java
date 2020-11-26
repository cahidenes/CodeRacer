package com.cenes.coderacer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cenes.coderacer.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;//16*90;
		config.height = 1080;//9*90;
		config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
