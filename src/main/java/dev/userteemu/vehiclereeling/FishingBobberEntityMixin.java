package dev.userteemu.vehiclereeling;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
	@Shadow
	protected void pullHookedEntity(Entity entity) {}

	@Inject(method = "handleStatus", at = @At("HEAD"))
	private void handle(byte id, CallbackInfo ci) {
		if (id != 31) return;

		FishingBobberEntity instance = ((FishingBobberEntity)((Object)this));
		if (instance != null && instance.getWorld().isClient()) {
			Entity hookedEntity = instance.getHookedEntity();
			if (hookedEntity != null) {
				Entity controllerEntity = hookedEntity.getControllingPassenger();
				if (controllerEntity instanceof PlayerEntity && ((PlayerEntity)controllerEntity).isMainPlayer()) {
					this.pullHookedEntity(hookedEntity);
				}
			}
		}
	}
}
