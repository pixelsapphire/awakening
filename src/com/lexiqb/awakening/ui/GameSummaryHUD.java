package com.lexiqb.awakening.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.Vec2;

public class GameSummaryHUD  extends HUD {

    private final AssetsBundle assets = getContext().getAssetsBundle();

    private final RectangleShape background = new RectangleShape();
    private final RectangleShape icon = new RectangleShape();
    private final Text title = new Text();
    private final Text againText = new Text("Press RETURN to play again");
    private final Text quitText = new Text("Press ESCAPE to exit");

    public GameSummaryHUD(RESULT result) {
        background.setSize(result.background.getSize());
        background.setTexture(result.background);
        icon.setSize(result.icon.getSize());
        icon.setTexture(result.icon);
        title.setText(result.title);
    }

    @Override
    protected void init() {
        background.setOrigin(Vec2.divide(background.getSize(), 2));
        icon.setOrigin(Vec2.divide(icon.getSize(), 2));
        title.setAlignment(Alignment.CENTER);
        againText.setAlignment(Alignment.CENTER);
        quitText.setAlignment(Alignment.CENTER);

        background.setPosition(Vec2.divide(Vec2.f(getContext().getWindow().getSize()), 2));
        icon.setPosition(getContext().getWindow().getSize().x / 2f, getContext().getWindow().getSize().y * 0.45f);
        title.setPosition(getContext().getWindow().getSize().x / 2f, getContext().getWindow().getSize().y * 0.35f);
        againText.setPosition(getContext().getWindow().getSize().x / 2f, getContext().getWindow().getSize().y * 0.55f);
        quitText.setPosition(getContext().getWindow().getSize().x / 2f, getContext().getWindow().getSize().y * 0.65f);
        add(background, icon, title, againText, quitText);
    }

    public enum RESULT {
        VICTORY(GameContext.getInstance().getAssetsBundle().get("texture.ui.background"), GameContext.getInstance().getAssetsBundle().<TextureAtlas>get("texture.entity.slime").getMatrix(32, 32, 5, 4)[2][2], "Victory!"),
        DEFEAT(GameContext.getInstance().getAssetsBundle().get("texture.ui.background"), GameContext.getInstance().getAssetsBundle().<AnimatedTexture>get("texture.world.sensor").getFrame(1), "Defeat!");

        final Texture background;
        final ConstTexture icon;
        final String title;

        RESULT(Texture background, ConstTexture icon, String title) {
            this.background = background;
            this.icon = icon;
            this.title = title;
        }
    }
}
