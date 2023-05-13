package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Player extends Slime {

    @Override
    public void update(@NotNull Time deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public Direction getDirection() {
        return Keyboard.getDirection(Keyboard.StandardControls.WASD);
    }
}
