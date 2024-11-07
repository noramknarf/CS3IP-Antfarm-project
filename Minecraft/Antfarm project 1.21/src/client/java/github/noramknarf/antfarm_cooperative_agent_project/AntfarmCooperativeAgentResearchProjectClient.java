package github.noramknarf.antfarm_cooperative_agent_project;

import github.noramknarf.antfarm_cooperative_agent_project.entity.CubeEntity;
import github.noramknarf.antfarm_cooperative_agent_project.model.CubeEntityModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import renderer.CubeEntityRenderer;


public class AntfarmCooperativeAgentResearchProjectClient implements ClientModInitializer {
	/*public static final EntityType<CubeEntity> CUBE = Registry.register( //This part is copied from the fabric wiki entity tutorial. I'm pretty sure it could go in its own registry
			Registries.ENTITY_TYPE,
			Identifier.of("antfarm_cooperative_agent_project", "cube"),
			EntityType.Builder.create(CubeEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build("cube")
	);*/

	public static final EntityModelLayer CUBE_MODEL_LAYER = new EntityModelLayer(Identifier.of("github.noramknarf.antfarm_cooperative_agent_project", "cube"), "main");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	/*	FabricDefaultAttributeRegistry.register(CUBE, CubeEntity.createMobAttributes()); //Registers the attributes (In-game stats) for the CUBE entity type. Game will crash if this is not done, apparently.
		//Like on line 24, I think this could be reformatted to the same as ItemsRegistry
*/
		EntityRendererRegistry.register(AntfarmCooperativeAgentResearchProject.CUBE, CubeEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(CUBE_MODEL_LAYER, CubeEntityModel::getTexturedModelData);
	}
}