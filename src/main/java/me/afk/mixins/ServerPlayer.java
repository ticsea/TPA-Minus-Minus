package me.afk.mixins;

import com.mojang.authlib.GameProfile;
import me.afk.DeathInfo;
import me.afk.IMixin.IServerPlayerDeathInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.server.level.ServerPlayer.class)
public abstract class ServerPlayer extends Player implements IServerPlayerDeathInfo {

    public ServerPlayer(Level arg, BlockPos arg2, float f, GameProfile gameProfile) {
        super(arg, arg2, f, gameProfile);
    }

    @Shadow
    public abstract ServerLevel serverLevel();

    @Shadow
    @Final
    public MinecraftServer server;
    @Unique
    private DeathInfo afk$deathInfo = null;

    @Inject(method = "die", at = @At("HEAD"))
    private void log(DamageSource arg, CallbackInfo ci){
       this.afk$deathInfo = new DeathInfo(serverLevel(), position());
    }

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void log(net.minecraft.server.level.ServerPlayer arg, boolean bl, CallbackInfo ci) {
        IServerPlayerDeathInfo old = (IServerPlayerDeathInfo) arg;
        this.afk$deathInfo = old.getDeathInfo$tpamm();
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void loadDeathInfoFromNbt(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("tpamm_last_death")) {
            CompoundTag deathTag = tag.getCompound("tpamm_last_death");
            // parse dimension key
            ResourceKey<Level> dimKey = Level.RESOURCE_KEY_CODEC
                    .parse(NbtOps.INSTANCE, deathTag.get("dim"))
                    .result()
                    .orElse(this.level().dimension());
            // parse position Vec3
            double x = deathTag.getDouble("x");
            double y = deathTag.getDouble("y");
            double z = deathTag.getDouble("z");
            Vec3 pos = new Vec3(x, y, z);
            ServerLevel dim = this.server.getLevel(dimKey);
            this.afk$deathInfo = new DeathInfo(dim, pos);
        }
    }

    // 4. SAVE death info to NBT when player data saves (logout / auto-save)
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void saveDeathInfoToNbt(CompoundTag tag, CallbackInfo ci) {
        DeathInfo info = this.getDeathInfo$tpamm();
        CompoundTag deathTag = new CompoundTag();
        // save dimension
        ResourceLocation.CODEC.encodeStart(NbtOps.INSTANCE, info.level().dimension().location())
                .result()
                .ifPresent(nbt -> deathTag.put("dim", nbt));
        // save coordinates
        deathTag.putDouble("x", info.position().x);
        deathTag.putDouble("y", info.position().y);
        deathTag.putDouble("z", info.position().z);
        tag.put("tpamm_last_death", deathTag);
    }

    @Override
    public DeathInfo getDeathInfo$tpamm() {
        return this.afk$deathInfo;
    }
}
