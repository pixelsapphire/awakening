package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.GameObject;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.Sound;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.TextureAtlas;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Leech extends Entity {

    private static final AnimatedTexture animation = new AnimatedTexture(GameContext.getInstance().getAssetsBundle().<TextureAtlas>get("texture.entity.leech")
                                                                                    .getRow(110, 110, 8), 0.2f);
    private static final Sound roarSound = GameContext.getInstance().getAssetsBundle().get("sound.entity.leech.roar");
    private static final Sound nomSound = GameContext.getInstance().getAssetsBundle().get("sound.entity.leech.nom");
    private boolean awakened = false, nommingStarted = false, nommingEnded = false;
    private Time eatingTime = Time.ZERO;
    private Player food;

    public Leech() {
        setSize(256, 256);
        setOrigin(getSize().x / 2, getSize().y / 2);
        setFillColor(Colors.TRANSPARENT);
    }

    public void eat(@NotNull Player food) {
        this.food = food;
        food.disableMovement();
        roarSound.play();
        setPosition(food.getGlobalBounds().getCenter());
        awakened = true;
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        if (awakened) {
            eatingTime = Time.add(eatingTime, deltaTime);
            if (eatingTime.asSeconds() >= 2) {
                setTexture(animation);
                setFillColor(Colors.WHITE);
            }
            if (getTexture() instanceof AnimatedTexture) resumeAnimatedTexture();
            if (eatingTime.asSeconds() >= 2 + animation.getFrameDuration() * animation.getFramesCount() * 0.9f) {
                setTexture(animation.getFrame(animation.getFramesCount() - 1));
            }
            if (!nommingStarted && eatingTime.asSeconds() >= 2 + animation.getFrameDuration() * 1) {
                nomSound.play();
                nommingStarted = true;
            }
            if (!nommingEnded && eatingTime.asSeconds() >= 2 + animation.getFrameDuration() * 3) {
                nommingEnded = true;
                Objects.requireNonNull(food.getWorld()).stream().filter(o -> o instanceof GameObject && !(o instanceof Leech))
                       .forEach(o -> {
                           final var obj = (GameObject) o;
                           if (MathUtils.isInsideCircle(obj.getGlobalBounds().getCenter(),
                                                        getGlobalBounds().getCenter(), getSize().x / 2))
                               obj.setFillColor(Colors.TRANSPARENT);
                       });
            }
        } else if (getTexture() instanceof AnimatedTexture) freezeAnimatedTexture();
    }
}
