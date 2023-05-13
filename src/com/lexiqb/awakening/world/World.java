package com.lexiqb.awakening.world;

import com.lexiqb.awakening.entities.Player;
import com.lexiqb.awakening.ui.GameplayHUD;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.graphics.ConstView;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.graphics.View;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

public class World extends Scene {

    private final Vector2i size;
    private final Window window;
    private Player player;
    private Sensor[] sensors;

    public World(@NotNull Vector2i size, @NotNull Texture background) {
        this(size.x, size.y, background);
    }

    public World(int width, int height, @NotNull Texture background) {
        size = Vec2.i(width, height);
        window = getContext().getWindow();
        setOrderingPolicy(OrderingPolicy.Y_BASED);
        final var backgroundShep = new RectangleShape(width, height);
        backgroundShep.setTexture(background);
        backgroundShep.setLayer(-1);
        add(backgroundShep);
    }

    public Player getPlayer() {
        return player;
    }

    public void attachPlayer(@NotNull Player player) {
        this.player = player;
        add(player);
        player.assignWorld(this);
    }

    @Override
    protected void loop() {

        final var playerPosition = player.getPosition();
        final var windowSize = window.getSize();
        final boolean followOnX = playerPosition.x > windowSize.x / 2f && playerPosition.x < size.x - windowSize.x / 2f;
        final boolean followOnY = playerPosition.y > windowSize.y / 2f && playerPosition.y < size.y - windowSize.y / 2f;

        final ConstView view = getContext().getWindow().getView();
        final var playerCenter = player.getGlobalBounds().getCenter();
        float viewCenterX = view.getCenter().x, viewCenterY = view.getCenter().y;
        if (followOnX) viewCenterX = playerCenter.x;
        if (followOnY) viewCenterY = playerCenter.y + player.getGlobalBounds().height / 2f;
        ((View) view).setCenter(Vec2.f(viewCenterX, viewCenterY));
        getContext().getWindow().setView(view);

        player.update(getDeltaTime());

        getContext().getWindow().<GameplayHUD>getHUD().setStamina(player.getStaminaPercentage());

        updateOrder();
    }

    @Override
    protected void init() {
    }

    public IntRect getBounds() {
        return new IntRect(0, 0, size.x, size.y);
    }

    public void makeNoise() {
        // TODO calculate strength of noise, alert sensors
    }
}
