package com.lexiqb.awakening.ui;

import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.MathUtils;
import org.jetbrains.annotations.NotNull;

public class GameplayHUD extends HUD {

    private final AssetsBundle assets = getContext().getAssetsBundle();

    private final RectangleShape healthIcon = new RectangleShape(32, 32);
    private final RectangleShape healthBar = new RectangleShape(128, 16);
    private final RectangleShape staminaIcon = new RectangleShape(32, 32);
    private final RectangleShape staminaBar = new RectangleShape(128, 16);
    private final RectangleShape noiseIcon = new RectangleShape(32, 32);
    private final RectangleShape noiseBar = new RectangleShape(128, 16);

    @Override
    protected void init() {
        final int spacing = 8;
        int posX = 16, posY = 16;
        createBar(healthIcon, assets.get("texture.ui.health"), healthBar, Colors.INDIAN_RED, spacing, posX, posY);
        posY += spacing + healthIcon.getSize().y;
        createBar(staminaIcon, assets.get("texture.ui.stamina"), staminaBar, Colors.DODGER_BLUE, spacing, posX, posY);
        posY += spacing + staminaIcon.getSize().y;
        createBar(noiseIcon, assets.get("texture.ui.noise"), noiseBar, Colors.DARK_ORANGE, spacing, posX, posY);
    }

    @SuppressWarnings("SameParameterValue")
    private void createBar(@NotNull RectangleShape icon, @NotNull Texture iconTexture,
                           @NotNull RectangleShape bar, @NotNull Color barColor,
                           int spacing, int posX, int posY) {
        icon.setTexture(iconTexture);
        icon.setPosition(posX, posY);
        bar.setFillColor(barColor);
        bar.setOutlineColor(new Color(0, 0, 0, 128));
        bar.setOutlineThickness(-3);
        bar.setPosition(posX + spacing + icon.getSize().x, posY + icon.getSize().y / 2 - bar.getSize().y / 2);
        add(icon, bar);
    }

    void setHealth(float percentage) {
        healthBar.setSize(128 * MathUtils.clamp(percentage, 0f, 1f), 16);
    }

    void setStamina(float percentage) {
        staminaBar.setSize(128 * MathUtils.clamp(percentage, 0f, 1f), 16);
    }

    void setNoise(float percentage) {
        noiseBar.setSize(128 * MathUtils.clamp(percentage, 0f, 1f), 16);
    }
}
