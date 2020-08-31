package io.github.queerbric.queerbrics;

import io.github.queerbric.queerbrics.registry.QueerbricsEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class QueerbricsClient implements ClientModInitializer {
	public static final DefaultParticleType THROWABLE_BRICK;
	
	static {
		THROWABLE_BRICK = Registry.register(Registry.PARTICLE_TYPE, QueerbricsMain.getId("item_throwable_brick"),
				new DefaultParticleType(false));
	}
	
	private static class DefaultParticleType extends net.minecraft.particle.DefaultParticleType {
		protected DefaultParticleType(boolean bl) {
			super(bl);
		}
	}
	
	@Override
	public void onInitializeClient() {
		initEntityRenderers();
	}
	
	private static void initEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(QueerbricsEntityTypes.THROWABLE_BRICK,
				(dispatcher, ctx) -> new FlyingItemEntityRenderer<>(dispatcher, ctx.getItemRenderer()));
	}
}
