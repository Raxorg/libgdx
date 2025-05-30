/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.tests.utils.OrthoCamController;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

public class TiledMapLayerTintOpacityTest extends GdxTest {
	private final static String MAP_ORTHO = "data/maps/tiled-tint-opacity/ortho.tmx";
	private final static String MAP_ORTHO_CACHED = "data/maps/tiled-tint-opacity/ortho_cached.tmx";
	private final static String MAP_ISO = "data/maps/tiled-tint-opacity/iso.tmx";
	private final static String MAP_ISO_STAG = "data/maps/tiled-tint-opacity/iso_stag.tmx";
	private final static String MAP_HEX = "data/maps/tiled-tint-opacity/hex.tmx";
	private final static String MAP_ORTHO_W_IMG = "data/maps/tiled-tint-opacity/ortho_w_img_layer.tmx";
	private final static String MAP_ORTHO_DEEP_GROUP_TINT = "data/maps/tiled-tint-opacity/ortho_deep_group_tints.tmx";

	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	private OrthoCamController cameraController;
	private AssetManager assetManager;
	private BitmapFont font;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private int mapType = 0;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 2;
		camera.update();

		cameraController = new OrthoCamController(camera);
		Gdx.input.setInputProcessor(cameraController);

		font = new BitmapFont();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load(MAP_ORTHO, TiledMap.class);
		assetManager.load(MAP_ORTHO_CACHED, TiledMap.class);
		assetManager.load(MAP_ISO, TiledMap.class);
		assetManager.load(MAP_ISO_STAG, TiledMap.class);
		assetManager.load(MAP_HEX, TiledMap.class);
		assetManager.load(MAP_ORTHO_W_IMG, TiledMap.class);
		assetManager.load(MAP_ORTHO_DEEP_GROUP_TINT, TiledMap.class);
		assetManager.finishLoading();

		map = assetManager.get(MAP_ORTHO);
		renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			if (mapType != 0) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 0;
				map = assetManager.get(MAP_ORTHO);
				renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			if (mapType != 1) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 1;
				map = assetManager.get(MAP_ORTHO_CACHED);
				renderer = new OrthoCachedTiledMapRenderer(map, 1f / 32f);
				((OrthoCachedTiledMapRenderer)renderer).setBlending(true);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			if (mapType != 2) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 2;
				map = assetManager.get(MAP_ISO);
				renderer = new IsometricTiledMapRenderer(map, 1f / 64f);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
			if (mapType != 3) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 3;
				map = assetManager.get(MAP_ISO_STAG);
				renderer = new IsometricStaggeredTiledMapRenderer(map, 1f / 64f);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
			if (mapType != 4) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 4;
				map = assetManager.get(MAP_HEX);
				renderer = new HexagonalTiledMapRenderer(map, 1f / 128f);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
			if (mapType != 5) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 5;
				map = assetManager.get(MAP_ORTHO_W_IMG);
				renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
			if (mapType != 6) {
				if (renderer instanceof Disposable) ((Disposable)renderer).dispose();
				mapType = 6;
				map = assetManager.get(MAP_ORTHO_DEEP_GROUP_TINT);
				renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
			}
		}

		ScreenUtils.clear(137f / 255f, 137f / 255f, 137f / 255f, 1f);
		camera.update();

		// add margin to view bounds so it is easy to see any issues with clipping, calculated same way as
		// BatchTiledMapRenderer#setView (OrthographicCamera)
		final float margin = 3;
		final float width = camera.viewportWidth * camera.zoom - margin * 2;
		final float height = camera.viewportHeight * camera.zoom - margin * 2;
		final float w = width * Math.abs(camera.up.y) + height * Math.abs(camera.up.x);
		final float h = height * Math.abs(camera.up.y) + width * Math.abs(camera.up.x);
		final float x = camera.position.x - w / 2;
		final float y = camera.position.y - h / 2;
		renderer.setView(camera.combined, x, y, w, h);
		renderer.render();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(x, y, w, h);
		shapeRenderer.end();

		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		font.draw(batch, "Switch type with 1-7", Gdx.graphics.getHeight() - 100, 50);
		font.draw(batch, renderer.getClass().getSimpleName(), Gdx.graphics.getHeight() - 100, 20);
		batch.end();
	}
}
