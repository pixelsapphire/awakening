package com.lexiqb.awakening.world;

import com.lexiqb.awakening.GameObject;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class Portal extends GameObject implements AnimatedEntity {

    private final RectangleShape entrance;

    public Portal(Color color) {

        final var gateTexture = GameContext.getInstance().getAssetsBundle().<Texture>get("texture.world.gate");
        final var entranceTexture = GameContext.getInstance().getAssetsBundle().<AnimatedTexture>get("texture.world.portal");

        setSize(Vec2.multiply(gateTexture.getSize(), 2));
        setOrigin(getSize().x / 2, getSize().y * 0.85f);
        setTexture(GameContext.getInstance().getAssetsBundle().<Texture>get("texture.world.gate"));

        entrance = new RectangleShape(Vec2.multiply(entranceTexture.getSize(), 2));
        entrance.setSize(64, 104);
        entrance.setOrigin(getSize().x / 2, getSize().y);
        entrance.setFillColor(color);
        entrance.setTexture(GameContext.getInstance().getAssetsBundle().<AnimatedTexture>get("texture.world.portal"));
    }

    @Override
    public void setPosition(@NotNull Vector2f position) {
        super.setPosition(position);
        entrance.setPosition(Vec2.add(position, Vec2.f(getSize().x / 4, getSize().y / 2))); // da heck why / 4
    }

    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        super.draw(target, states);
        entrance.draw(target, states);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        entrance.updateAnimatedTexture();
    }
}
