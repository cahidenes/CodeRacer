package com.cenes.coderacer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Main extends ApplicationAdapter {

	BitmapFont font, geriSayimFont, yukaribuyukfont, yukarikucukfont, fontkotu, bonusFont, statFont, graphFont, shopFont;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	String goster = "", geriSayim = "", bonusString="";
	String[] code;
	ArrayList<Character> typed;
	ArrayList<Vector2> experienceCloud;
	Viewport viewport;
	int W = 1440;
	int H = 810;
	int dogruint = 0;
	float timer = 1, say = 0, wmpUpdateTimer = 0, bonusTimer = 0, animationTimer = 0;
	int wpm = 0, bonusTopla = 0;
	boolean bitti = false;
	int cursori, cursorj;
	int toplamdogrular = 0;
	float cameraX = 0, cameraY = 0, cameraXhedef = 0;
	boolean basladi = false;
	int suanakadardogru = 0;
	int totalcharacter = 0;
	boolean disable_correct = false;
	int played = 0;
	int yuk1 = -1, yuk2 = -1, yuk3 = -1;
	Preferences preferences;

	int progressMax = 10;
	int progressCur = 0;
	int progressLvl = 0;
	float progressActual = 0;
	ArrayList<Float> satir1, satir2, satir3, satir4;
	float sayactus2, sayactus3, sayactus4, sayactus5, sayactus6, sayactus7, sayactus8;

	float shop_x = 0, statistics_x = 0, basla_y = 0, loadingY, shopXhedef = 0, statisticsXhedef = 0, baslaYhedef = 0, loadingYhedef;
	float bonusBackScl = 0;
	int correctCount = 0;
	float kodActualH = 0;

	int bool_font = 10, bool_dark = 15, bool_sound = 20, bool_syntax = 25, bool_imlec = 30;
	int bool_dogru1 = 30, bool_dogru3 = 50, bool_dogru5 = 75, bool_dogru10 = 100, bool_dogruinf = 1000;
	int bool_bonus2 = 1, bool_bonus4 = 1, bool_bonus8 = 1, bool_bonus16 = 1, bool_bonus32 = 1;

	Texture text_font, text_dark, text_sound, text_syntax, text_imlec;
	Texture text_dogru1, text_dogru3, text_dogru5, text_dogru10, text_dogruinf;
	Texture text_bonus2, text_bonus4, text_bonus8, text_bonus16, text_bonus32;
	Texture text_bonus_back, shop, race, stats, loading, fetching, cursor_t, cursor_kotu, tick,
            keyboard, tus1, tus2, tus3, tus4, tus5, tus6, tus7, tus8, exit;
	Animation<TextureRegion> loadingAnimation;

	Color mavi, kirmizi, turuncu, mor;

	int stat_yazilankarakter = 0, stat_toplampuan = 0, stat_racecount = 0, stat_best = 0, stat_worst = 10000, stat_best_dis = 0, stat_worst_dis = 10000;
	ArrayList<Integer> stat_wpms, stat_wpms_dis;
	ArrayList<Character> stat_yanlis_karakterler;
	ArrayList<Integer> stat_yanlis_sayilar;

	Sound type1, type2, type3, type4, start, upgrade, purchase, error, count1, count2, exp,
			bonus1, bonus2, bonus3, bonus4, bonus5, bonus6, dart;
	private float exptimer;
	private int expplay;

	// 1 -> font, dark theme, typing sound, syntax coloring, imlec
	// 2 -> bastigini dogruya cevien 1, 3, 5, 10, inf
	// 3 -> max bonus 2x, 4x, 8x, 16x, 32x
	@Override
	public void create () {

		type1 = Gdx.audio.newSound(Gdx.files.internal("type1.mp3"));
		type2 = Gdx.audio.newSound(Gdx.files.internal("type2.mp3"));
		type3 = Gdx.audio.newSound(Gdx.files.internal("type3.mp3"));
		type4 = Gdx.audio.newSound(Gdx.files.internal("type4.mp3"));
		start = Gdx.audio.newSound(Gdx.files.internal("start.mp3"));
		upgrade = Gdx.audio.newSound(Gdx.files.internal("upgrade.mp3"));
		purchase = Gdx.audio.newSound(Gdx.files.internal("purchase.mp3"));
		error = Gdx.audio.newSound(Gdx.files.internal("error.mp3"));
		count1 = Gdx.audio.newSound(Gdx.files.internal("count1.mp3"));
		count2 = Gdx.audio.newSound(Gdx.files.internal("count2.mp3"));
		exp = Gdx.audio.newSound(Gdx.files.internal("exp.mp3"));
		bonus1 = Gdx.audio.newSound(Gdx.files.internal("bonus1.mp3"));
		bonus2 = Gdx.audio.newSound(Gdx.files.internal("bonus2.mp3"));
		bonus3 = Gdx.audio.newSound(Gdx.files.internal("bonus3.mp3"));
		bonus4 = Gdx.audio.newSound(Gdx.files.internal("bonus4.mp3"));
		bonus5 = Gdx.audio.newSound(Gdx.files.internal("bonus5.mp3"));
		bonus6 = Gdx.audio.newSound(Gdx.files.internal("bonus6.mp3"));
		dart = Gdx.audio.newSound(Gdx.files.internal("dart.mp3"));

		mavi = new Color(0x1565c0ff);
		kirmizi = new Color(0xff0000ff);
		turuncu = mor; // new Color(0xff9800ff);
		mor = new Color(0x6a1b9aff);

		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.characters = FreeTypeFontGenerator.DEFAULT_CHARS + '\t';
		param.size = 30;
		param.color = Color.WHITE;
		font = new FreeTypeFontGenerator(Gdx.files.internal("kod2.ttf")).generateFont(param);
		font.getData().markupEnabled = true;
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fontkotu = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
        fontkotu.getData().markupEnabled = true;
        fontkotu.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		param.size = 200;
		param.color = mavi;
		geriSayimFont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		geriSayimFont.getData().markupEnabled = false;
		geriSayimFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		param.size = 50;
		param.color = Color.BLACK;
		yukaribuyukfont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		yukaribuyukfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		param.size = 40;
		param.color = Color.WHITE;
		statFont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		statFont.getData().markupEnabled = true;
		statFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		param.size = 30;
		param.color = Color.BLACK;
		yukarikucukfont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		yukarikucukfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		param.color = mavi;
		shopFont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		shopFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


		param.color = Color.WHITE;
		param.size = 20;
		graphFont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		graphFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		param.color = Color.WHITE;
		param.borderColor = Color.BLACK;
		param.size = 50;
		param.borderWidth = 5;
		bonusFont = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf")).generateFont(param);
		bonusFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

//		W = Gdx.graphics.getWidth();
//		H = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, W, H);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.setProjectionMatrix(camera.combined);
		typed = new ArrayList<>();
		experienceCloud = new ArrayList<>();
		stat_wpms = new ArrayList<>();
		stat_wpms_dis = new ArrayList<>();
		stat_yanlis_karakterler = new ArrayList<>();
		stat_yanlis_sayilar = new ArrayList<>();
		viewport = new FitViewport(Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight()*2, camera);
//		viewport = new FitViewport(W, H, camera);
		satir1 = new ArrayList<>();
		satir2 = new ArrayList<>();
        satir3 = new ArrayList<>();
        satir4 = new ArrayList<>();
        for (int i = 0; i < 13; i++) satir1.add(0f);
        for (int i = 0; i < 12; i++) satir2.add(0f);
        for (int i = 0; i < 12; i++) satir3.add(0f);
        for (int i = 0; i < 10; i++) satir4.add(0f);

		preferences = Gdx.app.getPreferences("coderacer");
		stat_yazilankarakter = preferences.getInteger("stat_yazilankarakter", 0);
		stat_worst = preferences.getInteger("stat_worst", 10000);
		stat_best = preferences.getInteger("stat_best", 0);
		stat_toplampuan = preferences.getInteger("stat_toplampuan", 0);
		stat_racecount = preferences.getInteger("stat_racecount", 0);
		stat_best_dis = preferences.getInteger("stat_best_dis", 0);
		stat_worst_dis = preferences.getInteger("stat_worst_dis", 10000);
		int stat_racecount_dis = preferences.getInteger("stat_racecount_dis", 0);
		for (int i = 0; i < stat_racecount; i++)
			stat_wpms.add(preferences.getInteger("stat_wpms_"+i));
		for (int i = 0; i < stat_racecount_dis; i++)
			stat_wpms_dis.add(preferences.getInteger("stat_wpms_dis_"+i));
		bool_font = preferences.getInteger("bool_font", 16);
		bool_dark = preferences.getInteger("bool_dark", 20);
		bool_sound = preferences.getInteger("bool_sound", 23);
		bool_syntax = preferences.getInteger("bool_syntax", 27);
		bool_imlec = preferences.getInteger("bool_imlec", 30);
		bool_dogru1 = preferences.getInteger("bool_dogru1", 30);
		bool_dogru3 = preferences.getInteger("bool_dogru3", 31);
		bool_dogru5 = preferences.getInteger("bool_dogru5", 32);
		bool_dogru10 = preferences.getInteger("bool_dogru10", 33);
		bool_dogruinf = preferences.getInteger("bool_dogruinf", 40);
		bool_bonus2 = preferences.getInteger("bool_bonus2", 25);
		bool_bonus4 = preferences.getInteger("bool_bonus4", 29);
		bool_bonus8 = preferences.getInteger("bool_bonus8", 31);
		bool_bonus16 = preferences.getInteger("bool_bonus16", 33);
		bool_bonus32 = preferences.getInteger("bool_bonus32", 35);
		progressCur = preferences.getInteger("progressCur", 0);
		progressLvl = preferences.getInteger("progressLvl", 0);
		int stat_yanlis_count = preferences.getInteger("stat_yanlis_count", 0);
		for (int i = 0; i < stat_yanlis_count; i++){
			stat_yanlis_sayilar.add(preferences.getInteger("stat_yanlis_sayilar_" + i));
			stat_yanlis_karakterler.add(preferences.getString("stat_yanlis_karakterler_" + i, " ").charAt(0));
		}
		recalculateProgress();
		calculateYuks();

		text_font = new Texture(Gdx.files.internal("shop_font.png"));
		text_font.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_dark = new Texture(Gdx.files.internal("shop_dark.png"));
		text_dark.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_sound = new Texture(Gdx.files.internal("shop_sound.png"));
		text_sound.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_syntax = new Texture(Gdx.files.internal("shop_syntax.png"));
		text_syntax.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_imlec = new Texture(Gdx.files.internal("shop_imlec.png"));
		text_imlec.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		text_dogru1 = new Texture(Gdx.files.internal("shop_dogru1.png"));
		text_dogru1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_dogru3 = new Texture(Gdx.files.internal("shop_dogru3.png"));
		text_dogru3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_dogru5 = new Texture(Gdx.files.internal("shop_dogru5.png"));
		text_dogru5.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_dogru10 = new Texture(Gdx.files.internal("shop_dogru10.png"));
		text_dogru10.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_dogruinf = new Texture(Gdx.files.internal("shop_dogruinf.png"));
		text_dogruinf.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		text_bonus2 = new Texture(Gdx.files.internal("shop_bonus2.png"));
		text_bonus2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_bonus4 = new Texture(Gdx.files.internal("shop_bonus4.png"));
		text_bonus4.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_bonus8 = new Texture(Gdx.files.internal("shop_bonus8.png"));
		text_bonus8.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_bonus16 = new Texture(Gdx.files.internal("shop_bonus16.png"));
		text_bonus16.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		text_bonus32 = new Texture(Gdx.files.internal("shop_bonus32.png"));
		text_bonus32.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		text_bonus_back = new Texture(Gdx.files.internal("bonus_back.png"));
		text_bonus_back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		shop = new Texture(Gdx.files.internal("shop.png"));
		shop.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		race = new Texture(Gdx.files.internal("race.png"));
		race.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		stats = new Texture(Gdx.files.internal("stats.png"));
		stats.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		loading = new Texture(Gdx.files.internal("loading.png"));
		loading.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		fetching = new Texture(Gdx.files.internal("fetching.png"));
		fetching.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		cursor_t = new Texture(Gdx.files.internal("cursor.png"));
		cursor_t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cursor_kotu = new Texture(Gdx.files.internal("cursor_kotu.png"));
		cursor_kotu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tick = new Texture(Gdx.files.internal("tick.png"));
		tick.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        keyboard = new Texture(Gdx.files.internal("keyboard.png"));
        keyboard.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus1 = new Texture(Gdx.files.internal("tus1.png"));
		tus1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus2 = new Texture(Gdx.files.internal("tus2.png"));
		tus2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus3 = new Texture(Gdx.files.internal("tus3.png"));
		tus3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus4 = new Texture(Gdx.files.internal("tus4.png"));
		tus4.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus5 = new Texture(Gdx.files.internal("tus5.png"));
		tus5.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus6 = new Texture(Gdx.files.internal("tus6.png"));
		tus6.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus7 = new Texture(Gdx.files.internal("tus7.png"));
		tus7.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tus8 = new Texture(Gdx.files.internal("tus8.png"));
		tus8.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exit = new Texture(Gdx.files.internal("exit.png"));
		exit.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


		loadingAnimation = new Animation<>(0.1f,
				new TextureRegion(loading, 0, 0, 100, 100),
				new TextureRegion(loading, 100, 0, 100, 100),
				new TextureRegion(loading, 200, 0, 100, 100),
				new TextureRegion(loading, 300, 0, 100, 100),
				new TextureRegion(loading, 0, 100, 100, 100),
				new TextureRegion(loading, 100, 100, 100, 100),
				new TextureRegion(loading, 200, 100, 100, 100),
				new TextureRegion(loading, 300, 100, 100, 100)
		);


		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyTyped(char character) {

				if (bitti) return true;
				if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$€-%+=#_&~* \n".contains(String.valueOf(character)) && basladi && timer < 0){
                    typesound();
					if (dogruint == cursorj && cursorj < code[cursori].length() && code[cursori].charAt(cursorj) == character){
						dogruint++;
						dogruBasti();
						cursorj++;
						typed.add(character);
						if (code.length-1 == cursori && code[cursori].length() == dogruint){
							bitti();
						}
					} else if (correctCount > 0){ // corrected
						correctCount--;

						if (code[cursori].length() == cursorj) yanlisaekle('\n');
						else yanlisaekle(code[cursori].charAt(cursorj));

					    if (code[cursori].length() == cursorj){
                            toplamdogrular += code[cursori].length();
                            cursori++;
                            dogruBasti();
                            cursorj = 0;
                            dogruint = 0;
                            typed.clear();
                            while (cursori < code.length && cursorj < code[cursori].length() && code[cursori].charAt(cursorj) == ' '){
                                cursorj++;
                                typed.add(' ');
                                dogruint++;
                            }
                        } else {
                            dogruint++;
                            dogruBasti();
							cursorj++;
							typed.add(character);
                        }
						if (code.length-1 == cursori && code[cursori].length() == dogruint){
							bitti();
						}
					} else {
						if (dogruint == cursorj){
							if (code[cursori].length() == cursorj) yanlisaekle('\n');
							else yanlisaekle(code[cursori].charAt(cursorj));
						}
						cursorj++;
						hataliBasti();
						typed.add(character);
					}

				}
				return super.keyTyped(character);
			}

			@Override
			public boolean keyDown(int keycode) {
                System.out.println("keycode = " + keycode);

                if (keycode == Input.Keys.SPACE && !basladi && baslaYhedef == 0){
					start.play(0.5f);
					shopXhedef = -400;
					statisticsXhedef = 400;
					baslaYhedef = -600;
					new Thread(new Runnable() {
						@Override
						public void run() {
							String str = "";
							do {
								str = getRandomCode();
							} while (str.equals("Hata") || str.length() > 700);
							code = str.split("\n");
							totalcharacter = 0;
							for (int i = 0 ; i < code.length; i ++){
								totalcharacter += code[i].replaceFirst("\\s+","").length()+1;
							}
							basladi = true;
							cursori = 0;
							cursorj = 0;
							timer = 3;
							loadingYhedef = -600;
							goster = highlight(code);
						}
					}).start();
				}

                if (bitti) return true;
				if (!(basladi && timer < 0)) return true;
				if (keycode == Input.Keys.BACKSPACE){
					typesound();
					if (typed.size() > dogruint){
					   	cursorj--;
					    typed.remove(typed.size()-1);
                    }
				} else if (keycode == Input.Keys.ENTER){
					typesound();
                    if (code[cursori].length() == typed.size() && typed.size() == dogruint){
						toplamdogrular += code[cursori].length();
						cursori++;
						dogruBasti();
						cursorj = 0;
						dogruint = 0;
						typed.clear();
						while (cursori < code.length && cursorj < code[cursori].length() && code[cursori].charAt(cursorj) == ' '){
							cursorj++;
							typed.add(' ');
							dogruint++;
						}
                    } else {
                    	if (correctCount > 0){ // corrected
                    		correctCount--;
							dogruint++;
							dogruBasti();
							cursorj++;
							typed.add('\n');
							if (code.length-1 == cursori && code[cursori].length() == dogruint)
								bitti();
						} else {
							hataliBasti();
							cursorj++;
							typed.add('\n');
						}
					}
                } else if (keycode == Input.Keys.ESCAPE){
					basladi = false;
					geriSayim = "";
					goster = "";
					typed.clear();
					dogruint = 0;
					bonusTimer = 2;
					shopXhedef = 0;
					statisticsXhedef = 0;
					baslaYhedef = 0;
					loadingYhedef = 0;
					bonusBackScl = 0f;
					wpm = (int) ((suanakadardogru) / (5f * say / 60f));
				}

				switch (keycode){
                    case 68: satir1.set(0, 1f); break;
                    case Input.Keys.NUM_1: satir1.set(1, 1f); break;
                    case Input.Keys.NUM_2: satir1.set(2, 1f); break;
                    case Input.Keys.NUM_3: satir1.set(3, 1f); break;
                    case Input.Keys.NUM_4: satir1.set(4, 1f); break;
                    case Input.Keys.NUM_5: satir1.set(5, 1f); break;
                    case Input.Keys.NUM_6: satir1.set(6, 1f); break;
                    case Input.Keys.NUM_7: satir1.set(7, 1f); break;
                    case Input.Keys.NUM_8: satir1.set(8, 1f); break;
                    case Input.Keys.NUM_9: satir1.set(9, 1f); break;
                    case Input.Keys.NUM_0: satir1.set(10, 1f); break;
                    case Input.Keys.MINUS: satir1.set(11, 1f); break;
                    case Input.Keys.EQUALS: satir1.set(12, 1f); break;

                    case Input.Keys.Q: satir2.set(0, 1f); break;
                    case Input.Keys.W: satir2.set(1, 1f); break;
                    case Input.Keys.E: satir2.set(2, 1f); break;
                    case Input.Keys.R: satir2.set(3, 1f); break;
                    case Input.Keys.T: satir2.set(4, 1f); break;
                    case Input.Keys.Y: satir2.set(5, 1f); break;
                    case Input.Keys.U: satir2.set(6, 1f); break;
                    case Input.Keys.I: satir2.set(7, 1f); break;
                    case Input.Keys.O: satir2.set(8, 1f); break;
                    case Input.Keys.P: satir2.set(9, 1f); break;
                    case 71: satir2.set(10, 1f); break;
                    case 72: satir2.set(11, 1f); break;

                    case Input.Keys.A: satir3.set(0, 1f); break;
                    case Input.Keys.S: satir3.set(1, 1f); break;
                    case Input.Keys.D: satir3.set(2, 1f); break;
                    case Input.Keys.F: satir3.set(3, 1f); break;
                    case Input.Keys.G: satir3.set(4, 1f); break;
                    case Input.Keys.H: satir3.set(5, 1f); break;
                    case Input.Keys.J: satir3.set(6, 1f); break;
                    case Input.Keys.K: satir3.set(7, 1f); break;
                    case Input.Keys.L: satir3.set(8, 1f); break;
                    case 74: satir3.set(9, 1f); break;
                    case 75: satir3.set(10, 1f); break;
                    case Input.Keys.BACKSLASH: satir3.set(11, 1f); break;

                    case Input.Keys.Z: satir4.set(0, 1f); break;
                    case Input.Keys.X: satir4.set(1, 1f); break;
                    case Input.Keys.C: satir4.set(2, 1f); break;
                    case Input.Keys.V: satir4.set(3, 1f); break;
                    case Input.Keys.B: satir4.set(4, 1f); break;
                    case Input.Keys.N: satir4.set(5, 1f); break;
                    case Input.Keys.M: satir4.set(6, 1f); break;
                    case Input.Keys.COMMA: satir4.set(7, 1f); break;
                    case Input.Keys.PERIOD: satir4.set(8, 1f); break;
                    case Input.Keys.SLASH: satir4.set(9, 1f); break;

                    case Input.Keys.TAB: sayactus2 = 1f; break;

                    case Input.Keys.SHIFT_LEFT: sayactus4 = 1f; break;
                    case Input.Keys.BACKSPACE: sayactus5 = 1f; break;
                    case Input.Keys.ENTER: sayactus6 = 1f; break;
                    case Input.Keys.SHIFT_RIGHT: sayactus7 = 1f; break;
                    case Input.Keys.SPACE: sayactus8 = 1f; break;
                }
				return super.keyDown(keycode);
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				screenX = (int)(((float)screenX/Gdx.graphics.getWidth())*W - cameraX);
				screenY = (int)(((float)screenY/Gdx.graphics.getHeight())*H - cameraY);
				System.out.println("screenX = " + screenX);
				System.out.println("screenY = " + screenY);

				if (1448 < screenX && screenX < 1469 && 351 < screenY && screenY < 374){
					disable_correct = !disable_correct;
				}

				if (basladi) return true;
				if (300 < screenY && screenY < 600 && baslaYhedef == 0) {
					if (50 < screenX && screenX < 350) {
						// shop
						if (cameraXhedef == 0) cameraXhedef = 1040;
						else cameraXhedef = 0;
						dart.play();
					} else if (W - 350 < screenX && screenX < W - 50) {
						// statistics
						if (cameraXhedef == 0) cameraXhedef = -1040;
						else cameraXhedef = 0;
						dart.play();
					} else if ((W - 300) / 2 < screenX && screenX < (W - 300) / 2 + 300) {
						// play
//						geriSayim = "Fetching Code";
                        start.play();
						shopXhedef = -400;
						statisticsXhedef = 400;
						baslaYhedef = -600;
						new Thread(new Runnable() {
							@Override
							public void run() {
								String str = "";
								do {
									str = getRandomCode();
								} while (str.equals("Hata") || str.length() > 700);
								code = str.split("\n");
								totalcharacter = 0;
								for (int i = 0 ; i < code.length; i ++){
									totalcharacter += code[i].replaceFirst("\\s+","").length()+1;
								}
								basladi = true;
								cursori = 0;
								cursorj = 0;
								timer = 3;
								loadingYhedef = -600;
								goster = highlight(code);
							}
						}).start();
					}
				} else if (screenY > H-150 && baslaYhedef == 0) {
					if ((W - 100) / 2 < screenX && screenX < (W - 100) / 2 + 100){
						dart.play();
						System.exit(0);
					}
				}
				int col = 0;
				if (-W + 450 < screenX && screenX < -W + 450 + 150) col = 1;
				else if (-W + 650 < screenX && screenX < -W + 650 + 150) col = 2;
				else if (-W + 850 < screenX && screenX < -W + 850 + 150) col = 3;
				else if (-W + 1050 < screenX && screenX < -W + 1050 + 150) col = 4;
				else if (-W + 1250 < screenX && screenX < -W + 1250 + 150) col = 5;

				int row = 0;
				if (300-150 < screenY && screenY < 300) row = 1;
				else if (525-150 < screenY && screenY < 525) row = 2;
				else if (750-150 < screenY && screenY < 750) row = 3;

				System.out.println("row = " + row);
				System.out.println("col = " + col);

				if (row == 1){
					if (col == 1){
						if (bool_font > 0 && bool_font <= progressLvl){
							progressLvl -= bool_font;
							recalculateProgress();
							bool_font = 0;
							purchase.play();
							saveShop();
						}
					} else if (col == 2){
						if (bool_font == 0 && bool_dark > 0 && progressLvl >= bool_dark){
							progressLvl -= bool_dark;
							recalculateProgress();
							bool_dark = 0;
							purchase.play();
							saveShop();
						}
					} else if (col == 3){
						if (bool_dark == 0 && bool_sound > 0 && progressLvl >= bool_sound){
							progressLvl -= bool_sound;
							recalculateProgress();
							bool_sound = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 4){
						if (bool_sound == 0 && bool_syntax > 0 && progressLvl >= bool_syntax){
							progressLvl -= bool_syntax;
							recalculateProgress();
							bool_syntax = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 5){
						if (bool_syntax == 0 && bool_imlec > 0 && progressLvl >= bool_imlec){
							progressLvl -= bool_imlec;
							recalculateProgress();
							bool_imlec = 0;
                            purchase.play();
							saveShop();
						}
					}
				} else if (row == 2){
					if (col == 1){
						if (bool_dogru1 > 0 && bool_dogru1 <= progressLvl){
							progressLvl -= bool_dogru1;
							recalculateProgress();
							bool_dogru1 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 2){
						if (bool_dogru1 == 0 && bool_dogru3 > 0 && progressLvl >= bool_dogru3){
							progressLvl -= bool_dogru3;
							recalculateProgress();
							bool_dogru3 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 3){
						if (bool_dogru3 == 0 && bool_dogru5 > 0 && progressLvl >= bool_dogru5){
							progressLvl -= bool_dogru5;
							recalculateProgress();
							bool_dogru5 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 4){
						if (bool_dogru5 == 0 && bool_dogru10 > 0 && progressLvl >= bool_dogru10){
							progressLvl -= bool_dogru10;
							recalculateProgress();
							bool_dogru10 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 5){
						if (bool_dogru10 == 0 && bool_dogruinf > 0 && progressLvl >= bool_dogruinf){
							progressLvl -= bool_dogruinf;
							recalculateProgress();
							bool_dogruinf = 0;
                            purchase.play();
							saveShop();
						}
					}
				} else if (row == 3){
					if (col == 1){
						if (bool_bonus2 > 0 && bool_bonus2 <= progressLvl){
							progressLvl -= bool_bonus2;
							recalculateProgress();
							bool_bonus2 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 2){
						if (bool_bonus2 == 0 && bool_bonus4 > 0 && progressLvl >= bool_bonus4){
							progressLvl -= bool_bonus4;
							recalculateProgress();
							bool_bonus4 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 3){
						if (bool_bonus4 == 0 && bool_bonus8 > 0 && progressLvl >= bool_bonus8){
							progressLvl -= bool_bonus8;
							recalculateProgress();
							bool_bonus8 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 4){
						if (bool_bonus8 == 0 && bool_bonus16 > 0 && progressLvl >= bool_bonus16){
							progressLvl -= bool_bonus16;
							recalculateProgress();
							bool_bonus16 = 0;
                            purchase.play();
							saveShop();
						}
					} else if (col == 5){
						if (bool_bonus16 == 0 && bool_bonus32 > 0 && progressLvl >= bool_bonus32){
							progressLvl -= bool_bonus32;
							recalculateProgress();
							bool_bonus32 = 0;
                            purchase.play();
							saveShop();
						}
					}
				}

				return super.touchDown(screenX, screenY, pointer, button);
			}
		});
	}

	@Override
	public void render () {
		animationTimer += Gdx.graphics.getDeltaTime();
		Color backcolor = new Color(0x333333ff);
		if (bool_dark != 0) Gdx.gl.glClearColor(1, 1, 1, 1);
		else Gdx.gl.glClearColor(backcolor.r, backcolor.g, backcolor.b, backcolor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (basladi) {
			bonusTimer += Gdx.graphics.getDeltaTime();
			if (bonusBackScl > 0.5f) bonusBackScl -= Gdx.graphics.getDeltaTime() / 2f;
			else bonusBackScl = 0;
			if (timer > 0) {
				timer -= Gdx.graphics.getDeltaTime();
				System.out.println(timer);
				String yenigeriSayim = "" + (int) (timer + 1);
				if (!yenigeriSayim.equals(geriSayim)){
					count1.play();
					geriSayim = yenigeriSayim;
				}
				suanakadardogru = 0;
				say = 0;
				wmpUpdateTimer = 0;
				bonusTopla = 0;
				if (bool_dogru1 > 0) correctCount = 0;
				else if (bool_dogru3 > 0) correctCount = 1;
				else if (bool_dogru5 > 0) correctCount = 3;
				else if (bool_dogru10 > 0) correctCount = 5;
				else if (bool_dogruinf > 0) correctCount = 10;
				else correctCount = 1000000;
				if (disable_correct) correctCount = 0;
				played = 0;
			} else {
				geriSayim = "";
				if (played == 0){
					count2.play();
					played = 1;
				}
				goster = highlight(code);
				if (!bitti) {
					say += Gdx.graphics.getDeltaTime();
					wmpUpdateTimer += Gdx.graphics.getDeltaTime();
					if (wmpUpdateTimer > 1) {
						wmpUpdateTimer = 0;
						wpm = (int) ((suanakadardogru) / (5f * say / 60f));
					}
				}
			}
		}
		if (bonusTimer > 1) {
			bonusTimer = 0;
			for (int i = 0; i < (int) Math.log(bonusTopla + 1) * 3; i++) {
				Random r = new Random();
				experienceCloud.add(new Vector2(200 + r.nextInt(100) - 50, H - 300 + r.nextInt(100) - 50));
			}
			progressEkle(Math.max(0, bonusTopla));
			bonusTopla = -4;
			bonusString = "";
		}

		//------------------ interpolations -------------------------//


		// experienceCloud
		float hedefx = (W - 600) / 2f, hedefy = H - 50;
		for (Vector2 v : experienceCloud) {
			v.add(new Vector2(hedefx, hedefy).sub(v).scl(Gdx.graphics.getDeltaTime() * 100000f / (float) Math.pow((Math.sqrt((hedefx - v.x) * (hedefx - v.x) + (hedefy - v.y) * (hedefy - v.y))), 2)));
		}
		for (int i = experienceCloud.size() - 1; i >= 0; i--) {
			if (experienceCloud.get(i).y > H - 50){
				experienceCloud.remove(i);
				expplay++;
			}
		}
		exptimer -= Gdx.graphics.getDeltaTime();
		if (expplay>0 && exptimer < 0){
			expplay--;
			exp.play(0.1f);
			exptimer = 0.05f;
		}

		// bonus
		if (bool_sound == 0)
			progressActual += Math.min((progressCur - progressActual) * Gdx.graphics.getDeltaTime() * 10, progressMax / 50f);
		else progressActual = progressCur;

		if (progressActual > progressMax) { // lvl up
			progressLvl++;
			progressCur -= progressMax;
			progressActual = 0;
			recalculateProgress();
			if (basladi) upgrade.play(0.5f);
		}

		// cameraX
		if (bool_sound == 0)
			cameraX += (cameraXhedef - cameraX) * Gdx.graphics.getDeltaTime() * 10f;
		else cameraX = cameraXhedef;

		// menu
		if (bool_sound == 0) {
			shop_x += (shopXhedef - shop_x) * Gdx.graphics.getDeltaTime() * 5f;
			statistics_x += (statisticsXhedef - statistics_x) * Gdx.graphics.getDeltaTime() * 5f;
			basla_y += (baslaYhedef - basla_y) * Gdx.graphics.getDeltaTime() * 5f;
			loadingY += (loadingYhedef - loadingY) * Gdx.graphics.getDeltaTime() * 5f;
		} else {
			shop_x = shopXhedef;
			statistics_x = statisticsXhedef;
			basla_y = baslaYhedef;
			loadingY = loadingYhedef;
		}

		for (int i = 0; i < 13; i++) satir1.set(i, satir1.get(i)*(1-Gdx.graphics.getDeltaTime()*3f));
		for (int i = 0; i < 12; i++) satir2.set(i, satir2.get(i)*(1-Gdx.graphics.getDeltaTime()*3f));
		for (int i = 0; i < 12; i++) satir3.set(i, satir3.get(i)*(1-Gdx.graphics.getDeltaTime()*3f));
		for (int i = 0; i < 10; i++) satir4.set(i, satir4.get(i)*(1-Gdx.graphics.getDeltaTime()*3f));
		sayactus2 -= sayactus2*Gdx.graphics.getDeltaTime()*3f;
		sayactus3 -= sayactus3*Gdx.graphics.getDeltaTime()*3f;
		sayactus4 -= sayactus4*Gdx.graphics.getDeltaTime()*3f;
		sayactus5 -= sayactus5*Gdx.graphics.getDeltaTime()*3f;
		sayactus6 -= sayactus6*Gdx.graphics.getDeltaTime()*3f;
		sayactus7 -= sayactus7*Gdx.graphics.getDeltaTime()*3f;
		sayactus8 -= sayactus8*Gdx.graphics.getDeltaTime()*3f;

		// code H
		if (bool_sound == 0) kodActualH += (cursori - kodActualH)*Gdx.graphics.getDeltaTime()*20f;
		else kodActualH = cursori;

		// ----------------------- renders -----------------------------//

		int kodX = 100, kodY = 400;

		// hata kirmizi
		shapeRenderer.setColor(kirmizi);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if (bool_font == 0)
			shapeRenderer.rect(cameraX + (-5 + kodX + 30 * 0.6f * dogruint), cameraY + H - (-5 + kodY + 30), 18 * (typed.size() - dogruint), 30);
		shapeRenderer.end();

		// yazilar
		batch.begin();
		if (basladi && bool_font == 0)
			font.draw(batch, goster, cameraX + kodX, cameraY + H - kodY + 40 * kodActualH, W - 100, -1, true);
		else if (basladi)
			fontkotu.draw(batch, goster, cameraX + kodX, cameraY + H - kodY + 40 * kodActualH, W - 100, -1, true);
		geriSayimFont.draw(batch, geriSayim, cameraX, cameraY + H / 2f + 100, W, 1, false);


		// cursor
		if (basladi && bool_font == 0) {
			if (bool_imlec == 0)
				batch.draw(cursor_t, cameraX + (-5 + kodX + 30 * 0.6f * cursorj), cameraY + H - (-5 + kodY + 45), 10, 60);
			if (bool_imlec > 0)
				batch.draw(cursor_kotu, cameraX + (-5 + kodX + 30 * 0.6f * cursorj), cameraY + H - (-5 + kodY + 30), 5, 30);
		}

        // keyboard
        if (bool_imlec == 0) {
            float kw = 700;
            float m = kw / 500f;
            float kh = kw * (168f / 500f);
            float kx = W - kw - 10;
            float ky = 10 - 600 - basla_y;

            batch.draw(keyboard, kx, ky, kw, kh);

            for (int i = 0; i < 13; i++) {
                batch.setColor(1, 1, 1, satir1.get(i));
                batch.draw(tus1, kx + (1.5f + i * 34.8f) * m, ky + 140 * m, 27 * m, 27 * m);
            }
            batch.setColor(1, 1, 1, sayactus5);
            batch.draw(tus5, kx + 454f * m, ky + 140 * m, 45 * m, 27 * m);

            for (int i = 0; i < 12; i++) {
                batch.setColor(1, 1, 1, satir2.get(i));
                batch.draw(tus1, kx + (45 + i * 34.8f) * m, ky + 106 * m, 27 * m, 27 * m);
            }
            batch.setColor(1, 1, 1, sayactus2);
            batch.draw(tus2, kx + 1.5f * m, ky + 106 * m, 35 * m, 27 * m);

            for (int i = 0; i < 12; i++) {
                batch.setColor(1, 1, 1, satir3.get(i));
                batch.draw(tus1, kx + (54 + i * 34.8f) * m, ky + 71.5f * m, 27 * m, 27 * m);
            }
            batch.setColor(1, 1, 1, sayactus3);
            batch.draw(tus3, kx + 1.5f * m, ky + 72f * m, 45 * m, 27 * m);
            batch.setColor(1, 1, 1, sayactus6);
            batch.draw(tus6, kx + 460 * m, ky + 72 * m, 40 * m, 61 * m);

            for (int i = 0; i < 10; i++) {
                batch.setColor(1, 1, 1, satir4.get(i));
                batch.draw(tus1, kx + (63 + i * 34.8f) * m, ky + 36 * m, 27 * m, 27 * m);
            }
            batch.setColor(1, 1, 1, sayactus4);
            batch.draw(tus4, kx + 1.5f * m, ky + 36 * m, 55 * m, 27 * m);
            batch.setColor(1, 1, 1, sayactus7);
            batch.draw(tus7, kx + 412 * m, ky + 36 * m, 88 * m, 27 * m);

            batch.setColor(1, 1, 1, sayactus8);
            batch.draw(tus8, kx + 117 * m, ky + 1 * m, 230 * m, 27 * m);
        }
        batch.setColor(1, 1, 1, 1);
        batch.end();


        // yukari bar
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(mavi);
		shapeRenderer.rect(0, H - 100, W, 100);

		// Progress Bar

		shapeRenderer.setColor(mor);
		shapeRenderer.rect(400, H - 75, W - 800, 30);
		shapeRenderer.setColor(mavi);
		shapeRenderer.rect(400 + 3, H - 75 + 3, W - 800 - 6, 30 - 6);
		shapeRenderer.setColor(mor);
		shapeRenderer.rect(400, H - 75, (W - 800) * ((float) progressActual / progressMax), 30);

		// Race Progress Bar
		// This bar help user understand where he is and how much text remain
		if (basladi){
			// padding of our progress bar
			int barPadding = 3;
			// Background rect for race bar
			shapeRenderer.setColor(mor);
			shapeRenderer.rect(W/4, H-150 , W / 2 , 30);
			// Race Bar itself
			shapeRenderer.setColor(mavi);
			shapeRenderer.rect(W/4 + barPadding, H-150 + barPadding  , (W / 2 - 2*barPadding) * (suanakadardogru*1.0f/ totalcharacter) , 30 - 2 * barPadding);
		}
		shapeRenderer.end();

		batch.begin();
		// shop button
		batch.draw(shop, cameraX + shop_x + 50, 200, 300, 300);

		// statistics button
		batch.draw(stats, cameraX + statistics_x + W - 350, 200, 300, 300);

		// loading button
		batch.draw(loadingAnimation.getKeyFrame(animationTimer, true), cameraX + W / 2 - 50, loadingY + H / 2);
		batch.draw(fetching, cameraX + (W - 250) / 2f, loadingY + H / 2f - 60, 250, 36);

		// race button
		batch.draw(race, cameraX + (W - 400) / 2f, basla_y + 200, 400, 400);

		// exit button
		batch.draw(exit, cameraX + (W - 100) / 2f, basla_y + 25, 100, 100);

		batch.end();

		// stats tick
		shapeRenderer.begin();
		shapeRenderer.rect(cameraX + 1448, H - 371, 20, 20);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		// correct count
		shapeRenderer.setColor(mor);
		shapeRenderer.circle(220, H - 50, 40);
		shapeRenderer.end();
		batch.begin();
		yukaribuyukfont.draw(batch, correctCount > 10 ? "inf" : correctCount + "", 220, H - 33, 0, 1, false);

		// bonus
		batch.draw(text_bonus_back, cameraX + 200 - bonusBackScl * 50, H - 300 - bonusBackScl * 50, 100 * bonusBackScl, 100 * bonusBackScl, 0, 0, text_bonus_back.getWidth(), text_bonus_back.getHeight(), false, false);
		yukaribuyukfont.draw(batch, bonusTopla > 0 ? bonusTopla + "" : "", cameraX + 200, H - 280, 0, 1, false);
		bonusFont.draw(batch, bonusString, cameraX + 300, H - 280);

		// experience cloud
		for (Vector2 v : experienceCloud) {
			batch.draw(text_bonus_back, v.x - 10, v.y - 10, 20, 20);
		}

		// stats
		batch.setColor(1, 1, 1, 1);
		if (disable_correct) batch.draw(tick, cameraX + 1448, H - 371, 30, 30);
		statFont.draw(batch, "[#1565c0]Total characters written: [#6a1b9a]" + stat_yazilankarakter, cameraX + W, H - 200);
		statFont.draw(batch, "[#1565c0]Total experiences acquired: [#6a1b9a]" + stat_toplampuan, cameraX + W, H - 250);
		statFont.draw(batch, "[#1565c0]Total races: [#6a1b9a]" + stat_racecount, cameraX + W, H - 300);
		statFont.draw(batch, "[#1565c0]Disable error correcters", cameraX + W + 50, H - 350);
		statFont.draw(batch, String.format("[#1565c0]Most mistyped characters: ( %s ) -> %d,    ( %s ) -> %d,     ( %s ) -> %d",
				yuk1 == -1 ? ' ' : (stat_yanlis_karakterler.get(yuk1) == '\n' ? "enter" : stat_yanlis_karakterler.get(yuk1)),
				yuk1 == -1 ? 0 : stat_yanlis_sayilar.get(yuk1),
				yuk2 == -1 ? ' ' : (stat_yanlis_karakterler.get(yuk2) == '\n' ? "enter" : stat_yanlis_karakterler.get(yuk2)),
				yuk2 == -1 ? 0 : stat_yanlis_sayilar.get(yuk2),
				yuk3 == -1 ? ' ' : (stat_yanlis_karakterler.get(yuk3) == '\n' ? "enter" : stat_yanlis_karakterler.get(yuk3)),
				yuk3 == -1 ? 0 : stat_yanlis_sayilar.get(yuk3)),
				cameraX + W, H - 400);

		int last10av = 0;
		int av = 0;
		int avadet = 0;
		if (disable_correct) {
			for (int i = 0; i < stat_wpms_dis.size(); i++) {
				if (i >= stat_wpms_dis.size() - 10) {
					last10av += stat_wpms_dis.get(i);
					avadet++;
				}
				av += stat_wpms_dis.get(i);
			}
			if (stat_wpms_dis.size() == 0){
				last10av = 0;
				av = 0;
			} else {
				last10av /= avadet;
				av /= stat_wpms_dis.size();
			}
		} else {
			for (int i = 0; i < stat_wpms.size(); i++) {
				if (i >= stat_wpms.size() - 10) {
					last10av += stat_wpms.get(i);
					avadet++;
				}
				av += stat_wpms.get(i);
			}
			if (stat_wpms.size() == 0){
				last10av = 0;
				av = 0;
			} else {
				last10av /= avadet;
				av /= stat_wpms.size();
			}
		}

		statFont.draw(batch, "[#1565c0]Last 10 Average: [#6a1b9a]" + last10av, cameraX+W, H-200, W-500, 0, false);
		statFont.draw(batch, "[#1565c0]Best WPM: [#6a1b9a]" + (stat_best == 0 ? "-" : stat_best), cameraX+W, H-250, W-500, 0, false);
		statFont.draw(batch, "[#1565c0]Worst WPM: [#6a1b9a]" + (stat_worst == 10000 ? "-" : stat_worst), cameraX+W, H-300, W-500, 0, false);
		statFont.draw(batch, "[#1565c0]Overall Average: [#6a1b9a]" + av, cameraX+W, H-350, W-500, 0, false);

		batch.end();

		if (cameraX < -10) drawGraph();

		// shop
		batch.begin();
		batch.draw(text_font, cameraX + -W + 450, H - 300, 150, 150);
        batch.draw(text_dark, cameraX + -W + 650, H - 300, 150, 150);
        batch.draw(text_sound, cameraX + -W + 850, H - 300, 150, 150);
        batch.draw(text_syntax, cameraX + -W + 1050, H - 300, 150, 150);
        batch.draw(text_imlec, cameraX + -W + 1250, H - 300, 150, 150);

		batch.draw(text_dogru1, cameraX + -W + 450, H - 525, 150, 150);
		batch.draw(text_dogru3, cameraX + -W + 650, H - 525, 150, 150);
		batch.draw(text_dogru5, cameraX + -W + 850, H - 525, 150, 150);
		batch.draw(text_dogru10, cameraX + -W + 1050, H - 525, 150, 150);
		batch.draw(text_dogruinf, cameraX + -W + 1250, H - 525, 150, 150);

		batch.draw(text_bonus2, cameraX + -W + 450, H - 750, 150, 150);
		batch.draw(text_bonus4, cameraX + -W + 650, H - 750, 150, 150);
		batch.draw(text_bonus8, cameraX + -W + 850, H - 750, 150, 150);
		batch.draw(text_bonus16, cameraX + -W + 1050, H - 750, 150, 150);
		batch.draw(text_bonus32, cameraX + -W + 1250, H - 750, 150, 150);

		shopFont.draw(batch, (bool_font > 0) ? bool_font + " lvl" : "purchased", cameraX + -W + 450, H - 150, 150, 1, false);
		shopFont.draw(batch, (bool_dark > 0) ? ((bool_font == 0) ? bool_dark + " lvl" : "locked") : "purchased", cameraX + -W + 650, H - 150, 150, 1, false);
		shopFont.draw(batch, (bool_sound > 0) ? ((bool_dark == 0) ? bool_sound + " lvl" : "locked") : "purchased", cameraX + -W + 850, H - 150, 150, 1, false);
		shopFont.draw(batch, (bool_syntax > 0) ? ((bool_sound == 0) ? bool_syntax + " lvl" : "locked") : "purchased", cameraX + -W + 1050, H - 150, 150, 1, false);
		shopFont.draw(batch, (bool_imlec > 0) ? ((bool_syntax == 0) ? bool_imlec + " lvl" : "locked") : "purchased", cameraX + -W + 1250, H - 150, 150, 1, false);

		shopFont.draw(batch, (bool_dogru1 > 0) ? bool_dogru1 + " lvl" : "purchased", cameraX + -W + 450, H - 375, 150, 1, false);
		shopFont.draw(batch, (bool_dogru3 > 0) ? ((bool_dogru1 == 0) ? bool_dogru3 + " lvl" : "locked") : "purchased", cameraX + -W + 650, H - 375, 150, 1, false);
		shopFont.draw(batch, (bool_dogru5 > 0) ? ((bool_dogru3 == 0) ? bool_dogru5 + " lvl" : "locked") : "purchased", cameraX + -W + 850, H - 375, 150, 1, false);
		shopFont.draw(batch, (bool_dogru10 > 0) ? ((bool_dogru5 == 0) ? bool_dogru10 + " lvl" : "locked") : "purchased", cameraX + -W + 1050, H - 375, 150, 1, false);
		shopFont.draw(batch, (bool_dogruinf > 0) ? ((bool_dogru10 == 0) ? bool_dogruinf + " lvl" : "locked") : "purchased", cameraX + -W + 1250, H - 375, 150, 1, false);

		shopFont.draw(batch, (bool_bonus2 > 0) ? bool_bonus2 + " lvl" : "purchased", cameraX + -W + 450, H - 600, 150, 1, false);
		shopFont.draw(batch, (bool_bonus4 > 0) ? ((bool_bonus2 == 0) ? bool_bonus4 + " lvl" : "locked") : "purchased", cameraX + -W + 650, H - 600, 150, 1, false);
		shopFont.draw(batch, (bool_bonus8 > 0) ? ((bool_bonus4 == 0) ? bool_bonus8 + " lvl" : "locked") : "purchased", cameraX + -W + 850, H - 600, 150, 1, false);
		shopFont.draw(batch, (bool_bonus16 > 0) ? ((bool_bonus8 == 0) ? bool_bonus16 + " lvl" : "locked") : "purchased", cameraX + -W + 1050, H - 600, 150, 1, false);
		shopFont.draw(batch, (bool_bonus32 > 0) ? ((bool_bonus16 == 0) ? bool_bonus32 + " lvl" : "locked") : "purchased", cameraX + -W + 1250, H - 600, 150, 1, false);

		yukaribuyukfont.draw(batch, String.format("%.2f", say), 25, H - 25);
		yukaribuyukfont.draw(batch, ""+wpm +" wpm", 25, H - 25, W-100, 0, false);
		yukarikucukfont.draw(batch, "Level " + progressLvl, 0, H-15, W, 1, false);
		yukarikucukfont.draw(batch, (int)(progressActual+0.9f) + " xp", (W-600)/2f, H-50);
		yukarikucukfont.draw(batch, (int)(progressMax+0.9f) + "", (W-600)/2f, H-50, 600, 0, false);
		batch.end();

	}

	String getRandomCode(){
		/*return "#include <bits/stdc++.h>\n" +
				"int main(){\n" +
				"    for (int i = 0; i < n; i++){\n" +
				"        printf(\"hello!\");\n" +
				"    }\n" +
				"}\n";*/
		try {
			Random r = new Random();
			int contestId = r.nextInt(1442);
			if (contestId < 15){
			    return "print(\"thats it\");";
			}
			int submissionNumber = r.nextInt(1000) + 1;
			String randomcontest = "https://codeforces.com/api/contest.status?contestId=" + contestId + "&from=" + submissionNumber + "&count=1";
			System.out.println(randomcontest);
			URL apiUrl = new URL(randomcontest);
			HttpURLConnection apicon = (HttpURLConnection) apiUrl.openConnection();
			apicon.setRequestMethod("GET");
			apicon.setConnectTimeout(5000);
			apicon.setReadTimeout(5000);

			BufferedReader in = new BufferedReader(new InputStreamReader(apicon.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.replace(" ", "").replace("\n", "").isEmpty()) continue;
				content.append(trimTrailingBlanks(inputLine)).append('\n');
			}
			in.close();

			String idstr = content.toString();
			idstr = idstr.substring(idstr.indexOf("id"));
			idstr = idstr.substring(idstr.indexOf(":"));
			idstr = idstr.substring(1);
			idstr = idstr.substring(0, idstr.indexOf(","));
			int submissionId = Integer.parseInt(idstr);

			String urlString = "https://codeforces.com/contest/" + contestId + "/submission/" + submissionId;
            System.out.println(urlString);

			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			int status = con.getResponseCode();
			System.out.println("status: " + status);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.replace(" ", "").replace("\n", "").isEmpty()) continue;
				content.append(trimTrailingBlanks(inputLine)).append('\n');
			}
			in.close();
			con.disconnect();

			String str = content.toString();

			str = str.substring(str.indexOf("program-source-text"));
			str = str.substring(str.indexOf(">"));
			str = str.substring(1);
			str = str.substring(0, str.indexOf("<"));
			str = str.replaceAll("&lt;", "<");
			str = str.replaceAll("&gt;", ">");
			str = str.replace("&quot;", "\"");
            str = str.replace("&amp;", "&");
            str = str.replace("&#39;", "'");
            str = str.replace("\t", "    ");
			str = str.replaceAll("[^\\\\ ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!?'.,;:()\\[\\]{}<>|/@^$€\\-%+=#_&~*\\n]", "");

			int last = 0;
			while (str.substring(last).contains("\n")){
				System.out.println("while");
				int yeni = str.substring(last).indexOf("\n") + last;
				if (yeni-last > 74) return "Hata";
				last = yeni+1;
			}

//			return "01234567890123456789012345678901234567890123456789012345678901234567890123";
			return str;
		} catch (Exception e){
			return "Hata";
		}
	}

	String highlight(String[] code){
		try {
			if (bool_font != 0) {

				String ret = "[#00ff00ff]";
				for (int i = 0; i < cursori; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret += code[cursori].replace('[', 'ツ').substring(0, dogruint);
				ret += "[#ff0000ff]" + code[cursori].replace('[', 'ツ').substring(dogruint, Math.min(code[cursori].length(), typed.size()));
				ret += "[#000000ff]" + code[cursori].replace('[', 'ツ').substring(Math.min(code[cursori].length(), typed.size())) + '\n';
				for (int i = cursori + 1; i < code.length; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret = ret.replace("ツ", "[[");
				return ret;

			} else if (bool_dark != 0) {

				String ret = "[#00ff00ff]";
				for (int i = 0; i < cursori; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret += code[cursori].replace('[', 'ツ').substring(0, dogruint);
				ret += "[#000000ff]" + code[cursori].replace('[', 'ツ').substring(dogruint, Math.min(code[cursori].length(), typed.size()));
				ret += "[#000000ff]" + code[cursori].replace('[', 'ツ').substring(Math.min(code[cursori].length(), typed.size())) + '\n';
				for (int i = cursori + 1; i < code.length; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret = ret.replace("ツ", "[[");
				return ret;

			} else if (bool_syntax != 0) {

				String ret = "[#ffffffff]";
				for (int i = 0; i < cursori; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret += code[cursori].replace("[", "ツ").substring(0, dogruint);
				ret += "[#ffffffff]" + code[cursori].replace('[', 'ツ').substring(dogruint, Math.min(code[cursori].length(), typed.size()));
				ret += "[#888888ff]" + code[cursori].replace('[', 'ツ').substring(Math.min(code[cursori].length(), typed.size())) + '\n';
				for (int i = cursori + 1; i < code.length; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret = ret.replace("ツ", "[[");
				return ret;

			} else {

				String ret = "[#ffffffff]";
				for (int i = 0; i < cursori; i++) {
					ret += renklendir(code[i]) + '\n';
				}
				ret += renklendir(code[cursori].substring(0, dogruint));
				ret += "[#ffffffff]" + code[cursori].replace('[', 'ツ').substring(dogruint, Math.min(code[cursori].length(), typed.size()));
				ret += "[#888888ff]" + code[cursori].replace('[', 'ツ').substring(Math.min(code[cursori].length(), typed.size())) + '\n';
				for (int i = cursori + 1; i < code.length; i++) {
					ret += code[i].replace('[', 'ツ') + '\n';
				}
				ret = ret.replace("ツ", "[[");
				return ret;
			}
		} catch (Exception e){
			bitti();
			return "";
		}
	}

	String renklendir(String code){

		String[] syntax1 = {"int", "double", "float", "long", "char", "string", "vector"};
		String[] syntax2 = {"if","else", "while", "for", "return", "break", "continue", "switch", "case", "define", "typedef", "include", "using", "namespace", "import", "in"};
		String[] syntax3 = {"=", "+", "-", "*", "/", "<", ">", "^", "|", "&", "!", ".", ","};
		String[] syntax4 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] syntax5 = {"#", "(", ")", "{", "}", ";"};

		String color1 = "#4db6acff";
		String color2 = "#64b5f6ff";
		String color3 = "#ffeb3bff";
		String color4 = "#aed581ff";
		String color5 = "#ffab91ff";
		String scolor = "#aed581ff";

		code = code.replace('[', 'ツ');

//		for (String s: syntax4){
//			code = code.replace(s, "[" + color4 + "]" + s + "[]");
//		}

		for (String s: syntax5){
			code = code.replace(s, "[" + color5 + "]" + s + "[]");
		}

		for (String s: syntax1){
			int ind = 0;
			int thresh = 10;
			while (ind < code.length() && code.substring(ind).contains(s) && thresh-->0) {
				System.out.println("while1");
				ind = code.indexOf(s);
				if ((ind-1 < 0 || !Character.isLetter(code.charAt(ind-1))) &&
						(ind+s.length() >= code.length() || !Character.isLetter(code.charAt(ind+s.length())))){
					code = code.replaceFirst(s, "[" + color1 + "]" + "﷽" + "[]");
				}
				ind += s.length();
			}
			code = code.replace("﷽", s);
		}

		for (String s: syntax2){
			int ind = 0;
			int thresh = 10;
			while (ind < code.length() && code.substring(ind).contains(s) && thresh-->0) {
				System.out.println("while2");
				ind = code.indexOf(s);
				if ((ind-1 < 0 || !Character.isLetter(code.charAt(ind-1))) &&
						(ind+s.length() >= code.length() || !Character.isLetter(code.charAt(ind+s.length())))){
					code = code.replaceFirst(s, "[" + color2 + "]" + "﷽" + "[]");
				}
				ind += s.length();
			}
			code = code.replace("﷽", s);
		}

		for (String s: syntax3){
			code = code.replace(s, "[" + color3 + "]" + s + "[]");
		}

		int ilk = 0;
		String dondur = "";
		for (int i = 0; i < code.length(); i++){
			if (code.charAt(i) == '"'){
				if (ilk == 0){
					ilk = 1;
					dondur += "[" + scolor + "]\"";
				} else {
					ilk = 0;
					dondur += "\"[]";
				}
			} else {
				dondur += code.charAt(i);
			}
        }

		return dondur;
	}

	void hataliBasti(){
		bonusTimer = 1.2f;
		bonusBackScl = 0f;
		error.play();
	}

	void dogruBasti(){
		suanakadardogru++;
		if (bonusTopla >= 0) bonusBackScl = 1f;

		if (bonusTopla <= 10) 			progressEkle(1);
		else if (bonusTopla <= 30)	 	progressEkle(2);
		else if (bonusTopla <= 70)	 	progressEkle(4);
		else if (bonusTopla <= 150) 	progressEkle(8);
		else if (bonusTopla <= 310)		progressEkle(16);
		else 							progressEkle(32);

		if (bonusTopla <= 10) 			bonusTopla += getBonus(1);
		else if (bonusTopla <= 30)	 	bonusTopla += getBonus(2);
		else if (bonusTopla <= 70)	 	bonusTopla += getBonus(4);
		else if (bonusTopla <= 150) 	bonusTopla += getBonus(8);
		else if (bonusTopla <= 310)		bonusTopla += getBonus(16);
		else 							bonusTopla += getBonus(32);

		String yenibonus;

		if (bonusTopla <= 0) 		   	yenibonus = "";
		else if (bonusTopla <= 10) 		yenibonus = getBonusString(1);
		else if (bonusTopla <= 30)	 	yenibonus = getBonusString(2);
		else if (bonusTopla <= 70)	 	yenibonus = getBonusString(4);
		else if (bonusTopla <= 150) 	yenibonus = getBonusString(8);
		else if (bonusTopla <= 310)		yenibonus = getBonusString(16);
		else 							yenibonus = getBonusString(32);

		if (!yenibonus.equals(bonusString)){
			bonusString = yenibonus;
			if (bonusString.equals("Nice")) bonus1.play();
			else if (bonusString.equals("Amazing")) bonus2.play();
			else if (bonusString.equals("Incredible")) bonus3.play();
			else if (bonusString.equals("Insane")) bonus4.play();
			else if (bonusString.equals("Crazy!")) bonus5.play();
			else if (bonusString.equals("Thats It!")) bonus6.play();
		}

		stat_yazilankarakter++;
		bonusTimer = 0;
	}

	int getBonus(int a){
		if (bool_bonus2 > 0) return Math.min(a, 1);
		else if (bool_bonus4 > 0) return Math.min(a, 2);
		else if (bool_bonus8 > 0) return Math.min(a, 4);
		else if (bool_bonus16 > 0) return Math.min(a, 8);
		else if (bool_bonus32 > 0) return Math.min(a, 16);
		else return Math.min(a, 32);
	}

	String getBonusString(int a){
		if (getBonus(a) == 1) return "Nice";
		else if (getBonus(a) == 2) return "Amazing";
		else if (getBonus(a) == 4) return "Incredible";
		else if (getBonus(a) == 8) return "Insane";
		else if (getBonus(a) == 16) return "Crazy!";
		else return "Thats It!";
	}

	void progressEkle(int amount){
		progressCur += amount;
		stat_toplampuan += amount;
	}

	void bitti(){
		basladi = false;
		geriSayim = "";
		goster = "";
		typed.clear();
		dogruint = 0;
		bonusTimer = 2;
		shopXhedef = 0;
		statisticsXhedef = 0;
		baslaYhedef = 0;
		loadingYhedef = 0;
		bonusBackScl = 0f;
		wpm = (int) ((suanakadardogru) / (5f * say / 60f));
		if (disable_correct || bool_dogru1 > 0){
			stat_wpms_dis.add(wpm);
			stat_best_dis = Math.max(stat_best_dis, wpm);
			stat_worst_dis = Math.min(stat_worst_dis, wpm);
		}
		stat_wpms.add(wpm);
		stat_best = Math.max(stat_best, wpm);
		stat_worst = Math.min(stat_worst, wpm);
		stat_racecount++;
		calculateYuks();

		save();
	}

	void recalculateProgress(){
		progressMax = (int)(10*Math.pow(1.3f, progressLvl));
	}

	void calculateYuks(){
		yuk1 = -1;
		yuk2 = -1;
		yuk3 = -1;
		for (int i = 0; i < stat_yanlis_sayilar.size(); i++){
			if (yuk1 == -1 || stat_yanlis_sayilar.get(i) > stat_yanlis_sayilar.get(yuk1)){
				yuk2 = yuk1;
				yuk1 = i;
			} else if (yuk2 == -1 || stat_yanlis_sayilar.get(i) > stat_yanlis_sayilar.get(yuk2)){
				yuk3 = yuk2;
				yuk2 = i;
			} else if (yuk3 == -1 || stat_yanlis_sayilar.get(i) > stat_yanlis_sayilar.get(yuk3)){
				yuk3 = i;
			}
		}
	}

	public static String trimTrailingBlanks(String str) {
		str = str.replace("\t", "    ");
		int i = str.length();
		for (; i > 0; i--){
			if (str.charAt(i-1) != ' ') break;
		}
		return str.substring(0, i);
	}

	void drawGraph(){

		ArrayList<Integer> wpms = stat_wpms;
		if (disable_correct) wpms = stat_wpms_dis;

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if (bool_dark == 0) shapeRenderer.setColor(Color.WHITE);
		else shapeRenderer.setColor(Color.BLACK);
		int maxDeger = 0;
		for (int wpm: wpms){
			maxDeger = Math.max(maxDeger, wpm);
		}
		maxDeger = Math.max(5, maxDeger);
		maxDeger *= 1.3;

		Vector2 orijin = new Vector2(cameraX+W+50, 30);
		int boy = 300;
		int en = W-500;
		shapeRenderer.rectLine(orijin.x, orijin.y, orijin.x+en, orijin.y, 3);
		shapeRenderer.rectLine(orijin.x, orijin.y, orijin.x, orijin.y+boy, 3);

		for (int i = 10; i < maxDeger; i+=10){
			float he = orijin.y + boy*((float)i/maxDeger);
			shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
			shapeRenderer.rectLine(orijin.x, he, orijin.x+en, he, 2);
			shapeRenderer.setColor(1, 1, 1, 1);
			shapeRenderer.rectLine(orijin.x-10, he, orijin.x+10, he, 2);
		}

		for (int i = 10; i < wpms.size(); i += 10){
			float wi = orijin.x + en*((float)i/wpms.size());
			shapeRenderer.rectLine(wi, orijin.y-10, wi, orijin.y+10, 2);
		}

		shapeRenderer.setColor(mor);
		for (int i = 0; i < wpms.size(); i++){
			shapeRenderer.circle(orijin.x + en*((i+1)/(float)wpms.size()), orijin.y + boy*((float)wpms.get(i)/maxDeger), 2);
		}
		for (int i = 1; i < wpms.size(); i++){
			Vector2 p1 = new Vector2(orijin.x + en*((i+1)/(float)wpms.size()), orijin.y + boy*((float)wpms.get(i)/maxDeger));
			Vector2 p2 = new Vector2(orijin.x + en*((i)/(float)wpms.size()), orijin.y + boy*((float)wpms.get(i-1)/maxDeger));
			shapeRenderer.rectLine(p1, p2, 4);
		}

		ArrayList<Vector2> morlar = new ArrayList<>();
		shapeRenderer.setColor(mavi);
		for (int i = 0; i < wpms.size(); i++){
			float ortalama = 0;
			for (int j = Math.max(0, i-9); j <= i; j++){
				ortalama += wpms.get(j);
			}
			ortalama/=Math.min(10, i+1);
			shapeRenderer.circle(orijin.x + en*((i+1)/(float)wpms.size()), orijin.y + boy*(ortalama/maxDeger), 2);
			morlar.add(new Vector2(orijin.x + en*((i+1)/(float)wpms.size()), orijin.y + boy*(ortalama/maxDeger)));
		}

		for (int i = 1; i < morlar.size(); i++){
			shapeRenderer.rectLine(morlar.get(i), morlar.get(i-1), 4);
		}

		shapeRenderer.end();
		batch.begin();
		for (int i = 0; i < maxDeger; i+=10){
			float he = orijin.y + boy*((float)i/maxDeger);
			graphFont.draw(batch, i+"", orijin.x-15, he+8, 0, 0, false);
		}
		for (int i = 10; i < wpms.size(); i += 10){
			float wi = orijin.x + en*((float)i/wpms.size());
			graphFont.draw(batch, i+"", wi, orijin.y-10, 0, 1, false);
		}
		batch.end();
	}

	void typesound(){
		switch (new Random().nextInt(4)){
			case 0: type1.play(0.1f); break;
			case 1: type2.play(0.1f); break;
			case 2: type3.play(0.1f); break;
			case 3: type4.play(0.1f); break;

		}
	}

	void save(){
		preferences.putInteger("stat_yazilankarakter", stat_yazilankarakter);
		preferences.putInteger("stat_worst", stat_worst);
		preferences.putInteger("stat_best", stat_best);
		preferences.putInteger("stat_toplampuan", stat_toplampuan);
		preferences.putInteger("stat_racecount", stat_wpms.size());
		preferences.putInteger("stat_best_dis", stat_best_dis);
		preferences.putInteger("stat_worst_dis", stat_worst_dis);
		preferences.putInteger("stat_racecount_dis", stat_wpms_dis.size());
		for (int i = 0; i < stat_wpms.size(); i++)
			preferences.putInteger("stat_wpms_"+i, stat_wpms.get(i));
		for (int i = 0; i < stat_wpms_dis.size(); i++)
			preferences.putInteger("stat_wpms_dis_"+i, stat_wpms_dis.get(i));
		preferences.putInteger("progressCur", progressCur);
		preferences.putInteger("progressLvl", progressLvl);
		preferences.putInteger("stat_yanlis_count", stat_yanlis_karakterler.size());
		for (int i = 0; i < stat_yanlis_karakterler.size(); i++){
			preferences.putInteger("stat_yanlis_sayilar_" + i, stat_yanlis_sayilar.get(i));
			preferences.putString("stat_yanlis_karakterler_" + i, stat_yanlis_karakterler.get(i)+"");
		}
		preferences.flush();
	}

	void saveShop(){
		 preferences.putInteger("bool_font", bool_font);
		 preferences.putInteger("bool_dark", bool_dark);
		 preferences.putInteger("bool_sound", bool_sound);
		 preferences.putInteger("bool_syntax", bool_syntax);
		 preferences.putInteger("bool_imlec", bool_imlec);
		 preferences.putInteger("bool_dogru1", bool_dogru1);
		 preferences.putInteger("bool_dogru3", bool_dogru3);
		 preferences.putInteger("bool_dogru5", bool_dogru5);
		 preferences.putInteger("bool_dogru10", bool_dogru10);
		 preferences.putInteger("bool_dogruinf", bool_dogruinf);
		 preferences.putInteger("bool_bonus2", bool_bonus2);
		 preferences.putInteger("bool_bonus4", bool_bonus4);
		 preferences.putInteger("bool_bonus8", bool_bonus8);
		 preferences.putInteger("bool_bonus16", bool_bonus16);
		 preferences.putInteger("bool_bonus32", bool_bonus32);
		 preferences.putInteger("progressCur", progressCur);
		 preferences.putInteger("progressLvl", progressLvl);
		 preferences.flush();
	}

	void yanlisaekle(char c){
//		System.out.println("c = " + c);
		int ind = -1;
		for (int i = 0; i < stat_yanlis_karakterler.size(); i++){
			if (stat_yanlis_karakterler.get(i) == c){
				ind = i;
				break;
			}
		}
		if (ind == -1){
			stat_yanlis_karakterler.add(c);
			stat_yanlis_sayilar.add(1);
		} else {
			stat_yanlis_sayilar.set(ind, stat_yanlis_sayilar.get(ind)+1);
		}
	}

	@Override
	public void dispose () {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		super.resize(width, height);
	}
}
