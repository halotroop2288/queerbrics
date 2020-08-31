package io.github.queerbric.queerbrics.datatypes.entity;

import io.github.queerbric.queerbrics.QueerbricsMain;
import io.github.queerbric.queerbrics.registry.QueerbricsEntityTypes;
import io.github.queerbric.queerbrics.registry.QueerbricsItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ThrowableBrickEntity extends ThrownItemEntity {
	
	public ThrowableBrickEntity(EntityType<? extends ThrowableBrickEntity> entityType, World world) {
		super(entityType, world);
	}

	public ThrowableBrickEntity(World world, LivingEntity owner) {
		super(QueerbricsEntityTypes.THROWABLE_BRICK, owner, world);
		this.setItem(owner.getActiveItem());
	}

	public ThrowableBrickEntity(World world, double x, double y, double z) {
		super(QueerbricsEntityTypes.THROWABLE_BRICK, x, y, z, world);
	}

	@Override
	protected Item getDefaultItem() {
		return QueerbricsItems.Pride.RAINBOW.brick;
	}
	
	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleEffect() {
		ItemStack itemStack = this.getItem();
		return (new ItemStackParticleEffect(ParticleTypes.ITEM, (itemStack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemStack)));
	}
	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()),
				MathHelper.sqrt((
								getVelocity().x * getVelocity().x +
								getVelocity().y * getVelocity().y +
								getVelocity().z * getVelocity().z)));
	}
	
	@Override
	protected void onBlockCollision(BlockState state) {
		super.onBlockCollision(state);
	}
	
	@Override
	protected void onCollision(HitResult hitResult) {
		if (!world.isClient && hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = ((BlockHitResult)hitResult).getBlockPos();
			if (QueerbricsMain.GLASS_BLOCK_TAG.contains(world.getBlockState(pos).getBlock())) {
				world.breakBlock(pos, true, this.getOwner());
			}
		}
		super.onCollision(hitResult);
		this.remove();
	}
	
	@Override
	public void remove() {
		if (this.world.isClient) {
			ParticleEffect particleEffect = this.getParticleEffect();
			this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
		}
		super.remove();
	}
}
