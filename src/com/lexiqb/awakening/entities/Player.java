package com.lexiqb.awakening.entities;

import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Player extends Entity {

    private final float movementSpeed = 120;
    private Direction textureFacing = Direction.SOUTH;

    public void update(@NotNull Time deltaTime) {
        final var direction = Keyboard.getDirection(Keyboard.StandardControls.WASD);
        float modifier = 1.0f;
        if (Keyboard.isKeyPressed(Keyboard.Key.LCONTROL))
            modifier = 2.0f;
        setVelocity(Vec2.multiply(Vec2.multiply(direction.vector, movementSpeed), modifier));
        textureFacing = switch (direction) {
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST, NORTH_WEST, SOUTH_WEST -> Direction.WEST;
            case EAST, NORTH_EAST, SOUTH_EAST -> Direction.EAST;
            default -> textureFacing;
        };
        //setTexture();//[direction.ordinal() / 2]
        // Limit movement
    }
}
