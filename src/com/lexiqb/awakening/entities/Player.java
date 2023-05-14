package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.Sound;
import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Player extends Slime {

    private static final Sound wakeSound = GameContext.getInstance().getAssetsBundle().get("sound.entity.player.spell");

    private static final float maxStamina = 100, staminaRegen = 15, sUsSprint = 30, sUsRegular = 20, sUsSneak = 5, betweenYells = 2;
    private float stamina = maxStamina, yellCooldown = 0f;

    public Player() {
        super(SizeClass.PRETTY_AVERAGE);
        awaken();
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        super.update(deltaTime);
        float staminaUsage = 0;
        switch (getMotion()) {
            case SNEAK -> staminaUsage = sUsSneak;
            case REGULAR -> staminaUsage = sUsRegular;
            case SPRINT -> staminaUsage = sUsSprint;
        }
        final var usageMultiplier = 0.2f + Math.pow(Objects.requireNonNull(getWorld()).getDangerLevelPercentage(), 1 / 5f);
        stamina += (staminaRegen - staminaUsage * usageMultiplier) * deltaTime.asSeconds();
        if (stamina > maxStamina) stamina = maxStamina;
        if (stamina < 0 && !restricted) restricted = true;
        if (stamina >= 10 && restricted) restricted = false;

        if (yellCooldown > 0) yellCooldown -= deltaTime.asSeconds();
        if (yellCooldown < 0) yellCooldown = 0;
        if (yellCooldown == 0) {
            if (Keyboard.isKeyPressed(Keyboard.Key.SPACE)) {
                yellCooldown = betweenYells;
                GameContext.getInstance().getAudioHandler().playSound(wakeSound, "player", 100.0f, 1.0f, false);
                assert getWorld() != null;
                getWorld().makeNoise(getPosition(), 75);
                for (final Slime slam : getWorld().getSlimes())
                    if (MathUtils.distance(getPosition(), slam.getPosition()) < 150) slam.awaken();
            }
        }

        // Sneaking
        if (movementEnabled) {
            if (Keyboard.isKeyPressed(Keyboard.Key.LSHIFT)) setScale(1.0f, 0.75f);
            else setScale(1.0f);
        }
    }

    @Override
    public Direction getDirection() {
        return Keyboard.getDirection(Keyboard.StandardControls.WASD);
    }

    public float getStaminaPercentage() {
        return Math.max(stamina / maxStamina, 0);
    }
}
