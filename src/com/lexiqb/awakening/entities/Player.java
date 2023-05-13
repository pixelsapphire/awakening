package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Player extends Slime {

    private static final float maxStamina = 100, staminaRegen = 15, sUsSprint = 30, sUsRegular = 20, sUsSneak = 5;
    private float stamina = maxStamina;

    public Player() {
        super(SizeClass.PRETTY_AVERAGE);
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
        stamina += (staminaRegen - staminaUsage) * deltaTime.asSeconds();
        if (stamina > maxStamina) stamina = maxStamina;
        if (stamina < 0 && !restricted) restricted = true;
        if (stamina >= 0.125 * maxStamina && restricted) restricted = false;
    }

    @Override
    public Direction getDirection() {
        return Keyboard.getDirection(Keyboard.StandardControls.WASD);
    }

    @Override
    protected void land() {
        super.land();
        assert getWorld() != null;
        getWorld().makeNoise();
    }

    public float getStaminaPercentage() {
        return Math.max(stamina / maxStamina, 0);
    }
}
