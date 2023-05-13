package com.lexiqb.awakening.world;

import com.lexiqb.awakening.entities.Entity;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Sensor extends Entity {
    private float disturbedTime = 0.0f;
    private boolean disturbed = false;

    public Sensor() {
        final var entranceTexture = GameContext.getInstance().getAssetsBundle().<AnimatedTexture>get("texture.world.portal");

        setSize(40, 60);
        setTexture(entranceTexture);
        setFillColor(Colors.PURPLE);
        setOrigin(getSize().x / 2f, getSize().y);
    }

    public void disturb(float volume) {
        if (volume > 50) {
            var converted = volume / 20f - 2f;
            if (!disturbed) {
                disturbed = true;
                resumeAnimatedTexture();
            }
            if (disturbedTime < converted)
                disturbedTime = converted;
            if (getWorld() != null) getWorld().increaseDangerLevel(converted - 0.5f);
        }
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        if (disturbedTime > 0) {
            disturbedTime -= deltaTime.asSeconds();
        }
        if (disturbedTime <= 0 && disturbed) {
            freezeAnimatedTexture();
            disturbedTime = 0;
            disturbed = false;
        }
    }
}
