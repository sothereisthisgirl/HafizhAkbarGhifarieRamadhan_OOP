package com.HafizhAkbarGhifarieRamadhan.frontend.states;

import com.HafizhAkbarGhifarieRamadhan.frontend.GameManager;
import com.HafizhAkbarGhifarieRamadhan.frontend.states.GameState;
import com.HafizhAkbarGhifarieRamadhan.frontend.states.GameStateManager;
import com.HafizhAkbarGhifarieRamadhan.frontend.states.PlayingState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuState implements GameState {

    private final GameStateManager gsm;
    private final Stage stage;
    private Skin skin;
    private TextField nameField;
    private TextButton startButton;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createBasicSkin();
        buildUI();
    }

    private void createBasicSkin() {
        skin = new Skin();

        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap pixmapWhite = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapWhite.setColor(Color.WHITE);
        pixmapWhite.fill();
        skin.add("white", new Texture(pixmapWhite));
        pixmapWhite.dispose();

        Pixmap pixmapGray = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapGray.setColor(Color.GRAY);
        pixmapGray.fill();
        skin.add("gray", new Texture(pixmapGray));
        pixmapGray.dispose();
        Pixmap pixmapDarkGray = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapDarkGray.setColor(Color.DARK_GRAY);
        pixmapDarkGray.fill();
        skin.add("dark_gray", new Texture(pixmapDarkGray));
        pixmapDarkGray.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.getDrawable("dark_gray");
        textFieldStyle.cursor = skin.getDrawable("white");
        textFieldStyle.selection = skin.getDrawable("gray");
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable("gray");
        buttonStyle.down = skin.getDrawable("white");
        buttonStyle.over = skin.getDrawable("dark_gray");
        skin.add("default", buttonStyle);
    }

    private void buildUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("NETLAB JOYRIDE", skin);
        titleLabel.setFontScale(2f);
        titleLabel.setAlignment(Align.center);

        Label promptLabel = new Label("Enter Your Name:", skin);

        nameField = new TextField("", skin);
        nameField.setMessageText("Username...");
        nameField.setAlignment(Align.center);

        startButton = new TextButton("START GAME", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String inputName = nameField.getText().trim();
                if (inputName.isEmpty()) {
                    inputName = "Guest";
                }

                GameManager.getInstance().registerPlayer(inputName);
                gsm.set(new PlayingState(gsm));
            }
        });

        table.defaults().padBottom(20f);
        table.add(titleLabel).center();
        table.row();

        table.add(promptLabel).center();
        table.row();

        table.add(nameField).width(300f).height(40f);
        table.row();

        table.add(startButton).width(200f).height(50f);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (skin != null) {
            skin.dispose();
        }
    }
}
