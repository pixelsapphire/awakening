package com.lexiqb.awakening;

import com.lexiqb.awakening.data.WorldData;
import com.lexiqb.awakening.entities.Player;
import com.lexiqb.awakening.ui.GameplayHUD;
import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Sound;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.ConstTexture;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.graphics.TextureAtlas;
import com.rubynaxela.kyanite.util.Utils;

import java.util.stream.Stream;

public class Awakening extends Game {

    public static void main(String[] args) {
        Game.run(Awakening.class, args);
    }

    @Override
    protected void preInit() {
        final AssetsBundle assets = getContext().getAssetsBundle();

        // ================================[ Textures ]================================

        assets.register("texture.entity.slime", new TextureAtlas("assets/textures/entity/slime_atlas.png"));
        assets.register("texture.entity.leech", new TextureAtlas("assets/textures/entity/leech_atlas.png"));

        assets.register("texture.ui.health", new Texture("assets/textures/ui/health.png"));
        assets.register("texture.ui.stamina", new Texture("assets/textures/ui/stamina.png"));
        assets.register("texture.ui.noise", new Texture("assets/textures/ui/noise.png"));

        assets.register("texture.world.gate", new Texture("assets/textures/world/gate.png"));
        assets.register("texture.world.gate_alt", new Texture("assets/textures/world/gate_alt.png"));

        assets.register("texture.world.boulders.boulder1", new Texture("assets/textures/world/decor/boulder1.png"));
        assets.register("texture.world.boulders.boulder2", new Texture("assets/textures/world/decor/boulder2.png"));
        assets.register("texture.world.boulders.boulder3", new Texture("assets/textures/world/decor/boulder3.png"));
        assets.register("texture.world.bush.bush1", new Texture("assets/textures/world/decor/bush1.png"));
        assets.register("texture.world.bush.bush2", new Texture("assets/textures/world/decor/bush2.png"));
        assets.register("texture.world.bush.tree1", new Texture("assets/textures/world/decor/tree1.png"));
        assets.register("texture.world.bush.tree2", new Texture("assets/textures/world/decor/tree2.png"));
        assets.register("texture.world.bush.tree3", new Texture("assets/textures/world/decor/tree3.png"));

        assets.register("texture.world.lobby.background", new Texture("assets/textures/world/touch_sum_grass.png"));

        assets.register("texture.world.portal",
                        new AnimatedTexture(Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8)
                                                  .map(i -> new Texture("assets/textures/world/portal/stage" + i + ".png"))
                                                  .toArray(ConstTexture[]::new), 1 / 9f));
        assets.register("texture.world.sensor",
                        new AnimatedTexture(Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
                                                  .map(i -> new Texture("assets/textures/world/sensor/stage" + String.format("%02d", i) + ".png"))
                                                  .toArray(ConstTexture[]::new), 1 / 9f));

        // ================================[ Sounds ]================================

        getContext().getAudioHandler().createChannel("player");
        getContext().getAudioHandler().createChannel("environment");
        assets.register("sound.entity.leech.roar", Utils.lambdaInit(new Sound("assets/sounds/entity/leech.ogg")));
        assets.register("sound.entity.leech.nom", new Sound("assets/sounds/entity/nom.ogg"));
        assets.register("sound.entity.slime.takeoff", new Sound("assets/sounds/entity/slime/takeoff.ogg"));
        assets.register("sound.entity.slime.land", new Sound("assets/sounds/entity/slime/splat.ogg"));
        assets.register("sound.world.shrensor.disturb", new Sound("assets/sounds/world/shrensor.ogg"));
        assets.register("sound.world.portal.enter", new Sound("assets/sounds/world/portal.ogg"));

        // ================================[ Worlds ]================================

        assets.register("data.world.lobby", new DataAsset("assets/worlds/lobby.json"));
    }

    @Override
    protected void init() {
        getContext().setupWindow(1280, 720, "Awakening of the Rarely Observed Big Unidentified Sus Thing");
        final var player = new Player();
        getContext().getWindow().setHUD(new GameplayHUD());
        final var lobby = getContext().getAssetsBundle().<DataAsset>get("data.world.lobby").convertTo(WorldData.class).build();
        lobby.attachPlayer(player);
        getContext().getWindow().setScene(lobby);
    }
}