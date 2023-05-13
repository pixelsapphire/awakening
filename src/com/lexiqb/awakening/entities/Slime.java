package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.world.World;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.TextureAtlas;
import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class Slime extends Entity {

    private static final float movementSpeed = 120;
    private final Texture[][] animations = GameContext.getInstance().getAssetsBundle().<TextureAtlas>get("texture.entity.player").getMatrix(32, 32, 5, 4);
    private final float[] loopTimes = {0.05f, 0.1f, 0.2f, 0.25f, 0.3f, 0.4f, 0.65f, 0.75f, 0.8f, 0.85f, 0.95f, 1f};
    private final float loopLength = 1.2f;
    private float movementTime = 0;
    private Direction facing = Direction.SOUTH;

    public Slime() {
        setSize(64, 64);
        setFillColor(Colors.MEDIUM_PURPLE);
        setPosition(200, 200);
        setOrigin(getSize().x / 2f, getSize().y);
    }

    private static int facingId(Direction facing) {
        return facing.ordinal() / 2;
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        movement(deltaTime);
        keepInWorldBounds(deltaTime);
    }

    protected Direction getDirection() {
        // TODO make little slimes follow player
        return Direction.NULL;
    }

    protected void movement(@NotNull Time deltaTime) {
        final var direction = getDirection();
        int animationIndex = 2;
        float modifier = 1.0f;

        // If requested movement or animation loop is unfinished, add time to finish animation
        if (direction != Direction.NULL || movementTime > 0)
            movementTime += deltaTime.asSeconds();
        if (direction != Direction.NULL) {
            if (movementTime >= 0.75f * loopLength)
                movementTime -= 0.75f * loopLength;
        }
        if ((movementTime >= loopLength * loopTimes[0] && movementTime < loopLength * loopTimes[1]) ||
            (movementTime >= loopLength * loopTimes[2] && movementTime < loopLength * loopTimes[3]) ||
            (movementTime >= loopLength * loopTimes[8] && movementTime < loopLength * loopTimes[9]) ||
            (movementTime >= loopLength * loopTimes[10] && movementTime < loopLength * loopTimes[11])) {
            animationIndex = 1;
        }
        if ((movementTime >= loopLength * loopTimes[1] && movementTime < loopLength * loopTimes[2]) ||
            (movementTime >= loopLength * loopTimes[9] && movementTime < loopLength * loopTimes[10])) {
            animationIndex = 0;
        }
        if ((movementTime >= loopLength * loopTimes[4] && movementTime < loopLength * loopTimes[5]) ||
            (movementTime >= loopLength * loopTimes[6] && movementTime < loopLength * loopTimes[7])) {
            animationIndex = 3;
        }
        if ((movementTime >= loopLength * loopTimes[5] && movementTime < loopLength * loopTimes[6])) {
            animationIndex = 4;
        }

        // Resetting the time of animation
        if (direction == Direction.NULL && animationIndex == 2 && (movementTime >= loopLength * loopTimes[8] || movementTime < loopLength * loopTimes[4]))
            movementTime = 0;

        if (animationIndex > 2) {
            if (Keyboard.isKeyPressed(Keyboard.Key.LSHIFT))
                modifier = 0.5f;
            else if (Keyboard.isKeyPressed(Keyboard.Key.LCONTROL)) modifier = 2.0f;
                setScale(1.0f);
            setVelocity(Vec2.multiply(facing.vector, movementSpeed * modifier));
        }
        else {
            if (direction != Direction.NULL)
                facing = direction;
            setVelocity(Vector2f.zero());
        }

        // Sneaking
        if (Keyboard.isKeyPressed(Keyboard.Key.LSHIFT))
            setScale(1.0f, 0.75f);
        else
            setScale(1.0f);

        // Setting texture
        Direction facingNow = switch (direction) {
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST, NORTH_WEST, SOUTH_WEST -> Direction.WEST;
            case EAST, NORTH_EAST, SOUTH_EAST -> Direction.EAST;
            default -> facing;
        };
        setTexture(animations[animationIndex][facingId(facingNow)]);
    }
}
