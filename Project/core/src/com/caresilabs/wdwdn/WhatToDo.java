package com.caresilabs.wdwdn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.caresilabs.screen.Game;
import com.caresilabs.screen.GameScreen;

public class WhatToDo extends Game {

	@Override
	public void create() {
		super.create();
		setScreen(new GameScreen());
	}
}
