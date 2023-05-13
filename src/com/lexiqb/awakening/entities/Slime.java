package com.lexiqb.awakening.entities;

import com.lexiqb.awakening.world.World;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.TextureAtlas;
import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class Slime extends Entity {

    private static final float movementSpeed = 120;
    private final AnimatedTexture[] animations;
    private Direction facing = Direction.SOUTH;
    private World world;

    public Slime() {
        final var atlas = GameContext.getInstance().getAssetsBundle().<TextureAtlas>get("texture.entity.player");
        animations = Stream.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)
                           .map(facing -> new AnimatedTexture(atlas.getRow(0, 32 * facingId(facing), 32, 32, 5), 1 / 9f))
                           .toArray(AnimatedTexture[]::new);
        setSize(64, 64);
        setTexture(animations[facingId(facing)]);
        setFillColor(Colors.MEDIUM_PURPLE);
        setPosition(200, 200);
        setOrigin(Vec2.divide(getSize(), 2));
    }

    private static int facingId(Direction facing) {
        return facing.ordinal() / 2;
    }

    public @Nullable World getWorld() {
        return world;
    }

    public void assignWorld(@Nullable World world) {
        this.world = world;
    }

    @Override
    public void update(@NotNull Time deltaTime) {
        final var direction = Keyboard.getDirection(Keyboard.StandardControls.WASD);
        float modifier = 1.0f;
        if (Keyboard.isKeyPressed(Keyboard.Key.LCONTROL)) modifier = 2.0f;
        else if (Keyboard.isKeyPressed(Keyboard.Key.LSHIFT)) modifier = 0.5f;
        setVelocity(Vec2.multiply(direction.vector, movementSpeed * modifier));
        facing = switch (direction) {
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST, NORTH_WEST, SOUTH_WEST -> Direction.WEST;
            case EAST, NORTH_EAST, SOUTH_EAST -> Direction.EAST;
            default -> facing;
        };
        setTexture(animations[facingId(facing)]);
    }
}
