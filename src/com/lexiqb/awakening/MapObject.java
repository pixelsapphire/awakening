package com.lexiqb.awakening;

import com.rubynaxela.kyanite.game.Context;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.Nullable;

public abstract class MapObject extends RectangleShape implements Context {

    private Vector2f positionOnMap = Vector2f.zero();
    private Vector2f mapOffset = Vector2f.zero();
    protected Vector2f mapSize = Vector2f.zero();
    private FloatRect hitBox;

    public @Nullable FloatRect getHitBox() {
        return hitBox;
    }

    public void setHitBox(@Nullable FloatRect hitBox) {
        this.hitBox = hitBox;
    }

    public Vector2f getOffsetOnMap() {
        return mapOffset;
    }

    public Vector2f getPositionOnMap() {
        return positionOnMap;
    }

    public void moveOnMap(Vector2f offset) {
        positionOnMap = new Vector2f(positionOnMap.x + offset.x, positionOnMap.y + offset.y);
    }

    public void moveOnMap(float offsetX, float offsetY) {
        positionOnMap = new Vector2f(positionOnMap.x + offsetX, positionOnMap.y + offsetY);
    }

    public FloatRect gGB() {
        return new FloatRect(Vec2.add(Vec2.f(getGlobalBounds().left, getGlobalBounds().top), mapOffset),
                             Vec2.f(getGlobalBounds().width, getGlobalBounds().height));
    }
}
