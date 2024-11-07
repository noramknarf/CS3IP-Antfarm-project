package github.noramknarf.antfarm_cooperative_agent_project;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import renderer.CubeEntityRenderer;

public class AntfarmCooperativeAgentResearchProjectClient implements ClientModInitializer {
	public static final EntityModelLayer CUBE_MODEL_LAYER = new EntityModelLayer(Identifier.of("github.noramknarf.antfarm_cooperative_agent_project", "cube"), "main");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		EntityRendererRegistry.register(ModEntities.CUBE, (context) ->{
			return new CubeEntityRenderer(context);
		});
	}
}