package com.lexiqb.awakening.ui;

import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.MathUtils;
import org.jetbrains.annotations.NotNull;

public class GameplayHUD extends HUD {

    private final AssetsBundle assets = getContext().getAssetsBundle();
    private final RectangleShape staminaIcon = new RectangleShape(32, 32);
    private final RectangleShape staminaBar = new RectangleShape(128, 16);
    private final RectangleShape staminaBarBg = new RectangleShape(128, 16);
    private final RectangleShape noiseIcon = new RectangleShape(32, 32);
    private final RectangleShape noiseBar = new RectangleShape(128, 16);
    private final RectangleShape noiseBarBg = new RectangleShape(128, 16);
    private final RectangleShape slimeIcon = new RectangleShape(32, 32);
    private final Text slimeCount = new Text();

    @Override
    protected void init() {
        final int spacing = 8;
        int posX = 16, posY = 16;
//        createBar(healthIcon, assets.get("texture.ui.health"), healthBar, Colors.INDIAN_RED, spacing, posX, posY);
//        posY += spacing + healthIcon.getSize().y;
        createBarWithIcon(staminaIcon, assets.get("texture.ui.stamina"), staminaBar, staminaBarBg, Colors.DODGER_BLUE, spacing, posX, posY);
        posY += spacing + staminaIcon.getSize().y;
        createBarWithIcon(noiseIcon, assets.get("texture.ui.noise"), noiseBar, noiseBarBg, Colors.DARK_ORANGE, spacing, posX, posY);
        posY += spacing + noiseIcon.getSize().y;
        slimeIcon.setTexture(assets.<TextureAtlas>get("texture.entity.slime").getMatrix(32, 32, 5, 4)[2][2]);
        slimeIcon.setPosition(posX, posY);
        slimeIcon.setSize(32, 32);
        slimeCount.setPosition(posX + spacing + slimeIcon.getSize().x, posY);
        add(slimeIcon, slimeCount);
    }

    @SuppressWarnings("SameParameterValue")
    private void createBarWithIcon(@NotNull RectangleShape icon, @NotNull Texture iconTexture,
                                   @NotNull RectangleShape bar, @NotNull RectangleShape barBg, @NotNull Color barColor,
                                   int spacing, int posX, int posY) {
        icon.setTexture(iconTexture);
        icon.setPosition(posX, posY);
        createBar(icon, bar, bar, barColor, spacing, posX, posY, true);
        createBar(icon, bar, barBg, barColor.darker(0.75f).withOpacity(0.85f), spacing, posX, posY, false);
        add(icon, barBg, bar);
    }

    private void createBar(@NotNull RectangleShape icon, @NotNull RectangleShape bar, @NotNull RectangleShape barBg,
                           @NotNull Color barColor, int spacing, int posX, int posY, boolean outline) {
        barBg.setFillColor(barColor);
        if (outline) {
            barBg.setOutlineThickness(-3);
            barBg.setOutlineColor(new Color(0, 0, 0, 128));
        }
        barBg.setPosition(posX + spacing + icon.getSize().x, posY + icon.getSize().y / 2 - bar.getSize().y / 2);
    }

    public void setStamina(float percentage) {
        staminaBar.setSize(128 * MathUtils.clamp(percentage, 6 / 128f, 1f), 16);
    }

    public void setNoise(float percentage) {
        noiseBar.setSize(128 * MathUtils.clamp(percentage, 6 / 128f, 1f), 16);
    }

    public void setSlimeCount(int count) {
        slimeCount.setText("" + count);
    }
}
