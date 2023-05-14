package com.lexiqb.awakening.world;

import com.lexiqb.awakening.entities.*;
import com.lexiqb.awakening.ui.GameplayHUD;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.*;
import com.rubynaxela.kyanite.util.Unit;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class World extends Scene {

    private static final float dissipationFactor = 1f, criticalLevel = 25f, portalSuccTime = 2, portalSuccSpeed = 0.8f, disappearScale = 0.1f;
    private final Vector2i size;
    private final Window window;
    private final ArrayList<Slime> slimes = new ArrayList<>();
    private final ArrayList<Shrensor> shrensors = new ArrayList<>();
    private final Leech rarelyObservedUnidentifiedSusThing = new Leech();
    private Player player;
    private float dangerLevel = 0f;

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

    public void attachPlayer(@NotNull Player player) {
        this.player = player;
        add(player);
        player.assignWorld(this);
    }

    @Override
    protected void init() {
        add(rarelyObservedUnidentifiedSusThing);
        Shrensor testS = new Shrensor();
        testS.setPosition(600, 200);
        testS.assignWorld(this);
        shrensors.add(testS);
        Slime slims = new Slime(Slime.SizeClass.SMOL_GUY);
        slims.setFillColor(Colors.CADET_BLUE);
        slims.setPosition(200, 800);
        slims.assignWorld(this);
        slimes.add(slims);
        add(testS, slims);

        updateOrder();
    }

    @Override
    protected void loop() {
        if (dangerLevel > criticalLevel) rarelyObservedUnidentifiedSusThing.eat(player);
        dangerLevel -= 0.5f * getDeltaTime().asSeconds();
        if (dangerLevel < 0f) dangerLevel = 0f;

        readjustView();

        stream().filter(o -> o instanceof Entity).forEach(e -> ((Entity) e).update(getDeltaTime()));

        succ();

        getContext().getWindow().<GameplayHUD>getHUD().setStamina(player.getStaminaPercentage());
        getContext().getWindow().<GameplayHUD>getHUD().setNoise(dangerLevel / criticalLevel);

        updateOrder();
    }

    private void readjustView() {
        final var playerPosition = player.getPosition();
        final var windowSize = window.getSize();
        final var offsetY = player.getGlobalBounds().height / player.getScale().y / 3f;
        final boolean followOnX = playerPosition.x > windowSize.x / 2f && playerPosition.x < size.x - windowSize.x / 2f;
        final boolean followOnY = playerPosition.y > windowSize.y / 2f + offsetY && playerPosition.y < size.y - windowSize.y / 2f + offsetY;

        final ConstView view = getContext().getWindow().getView();
        final var playerCenter = player.getGlobalBounds().getCenter();
        float viewCenterX = view.getCenter().x, viewCenterY = view.getCenter().y;
        if (followOnX) viewCenterX = playerCenter.x;
        if (followOnY) viewCenterY = playerCenter.y + player.getGlobalBounds().height / 2f - offsetY;
        ((View) view).setCenter(Vec2.f(viewCenterX, viewCenterY));
        getContext().getWindow().setView(view);
    }

    private void succ() {
        boolean allSafe = true;
        int remainingBois = 0;
        ArrayList<Slime> toRemove = new ArrayList<>();
        for (final var slime : slimes) {
            if (!slime.inPortal) {
                for (var p : stream().filter(o -> o instanceof Portal).toList()) {
                    if (((Portal) p).getGlobalBounds().contains(slime.getPosition())) {
                        slime.portal = (Portal) p;
                        slime.inPortal = true;
                        slime.disableMovement();
                        break;
                    }
                }
                remainingBois++;
                allSafe = false;
            } else {
                slime.scale((float) Math.exp(Math.log(disappearScale) * getDeltaTime().asSeconds() / portalSuccTime));
                slime.move(Vec2.multiply(Vec2.subtract(Vec2.add(slime.portal.getGlobalBounds().getCenter(), Vec2.multiply(slime.portal.getSize(), 0.1f)), slime.getGlobalBounds().getCenter()), portalSuccSpeed * getDeltaTime().asSeconds()));
                if (slime.getScale().x < disappearScale) {
                    toRemove.add(slime);
                    scheduleToRemove(slime);
                }
            }
        }
        getContext().getWindow().<GameplayHUD>getHUD().setSlimeCount(remainingBois);
        for (var s : toRemove) slimes.remove(s);
        if (allSafe) {
            if (!getPlayer().inPortal) {
                for (var p : stream().filter(o -> o instanceof Portal).toList()) {
                    if (((Portal) p).getGlobalBounds().contains(getPlayer().getPosition())) {
                        getPlayer().portal = (Portal) p;
                        getPlayer().inPortal = true;
                        getPlayer().disableMovement();
                        break;
                    }
                }
            } else {
                getPlayer().scale((float) Math.exp(Math.log(disappearScale) * getDeltaTime().asSeconds() / portalSuccTime));
                getPlayer().move(Vec2.multiply(Vec2.subtract(Vec2.add(getPlayer().portal.getGlobalBounds().getCenter(), Vec2.multiply(getPlayer().portal.getSize(), 0.1f)), getPlayer().getGlobalBounds().getCenter()), portalSuccSpeed * getDeltaTime().asSeconds()));  // huh?

                if (getPlayer().getScale().x < disappearScale) {
                    getContext().getWindow().close(); // TODO change this to transport to new map / make another HUD appear
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public IntRect getBounds() {
        return new IntRect(0, 0, size.x, size.y);
    }

    public void makeNoise(Vector2f position, @Unit("dB") float volume) {
        for (var s : shrensors) {
            float distSqr = (MathUtils.pow((long) (s.getPosition().x - position.x), 2) + MathUtils.pow((long) (s.getPosition().y - position.y), 2)) / 10000f;
            s.disturb(convertTodB(convertFromdB(volume) / (distSqr * dissipationFactor)));
        }
    }

    public float convertTodB(@Unit("W/m^2") float value) {
        return (float) (10 * Math.log10(value * Math.pow(10, 12)));
    }

    public float convertFromdB(@Unit("dB") float value) {
        return (float) Math.pow(10, (value / 10f - 12));
    }

    public void increaseDangerLevel(float value) {
        dangerLevel += value;
    }

    public ArrayList<Slime> getSlimes() {
        return slimes;
    }
}
