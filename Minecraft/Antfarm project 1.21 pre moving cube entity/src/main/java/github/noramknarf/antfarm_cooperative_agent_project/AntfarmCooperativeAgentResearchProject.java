package github.noramknarf.antfarm_cooperative_agent_project;

import github.noramknarf.antfarm_cooperative_agent_project.registry.ItemsRegistry;
import github.noramknarf.antfarm_cooperative_agent_project.entity.custom.CubeEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntfarmCooperativeAgentResearchProject implements ModInitializer {
	public static final String MOD_ID = "antfarm_cooperative_agent_project";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("antfarm_cooperative_agent_project");

	public static final EntityType<CubeEntity> CUBE = Registry.register( //This part is copied from the fabric wiki entity tutorial. I'm pretty sure it could go in its own registry
			Registries.ENTITY_TYPE,
			Identifier.of(MOD_ID, "cube"),
			EntityType.Builder.create(CubeEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build("cube")
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("This is a message :D");
		ItemsRegistry.init(); //pokes the ItemsRegistry class with a meaningless method to add it to the load list
		FabricDefaultAttributeRegistry.register(CUBE, CubeEntity.createMobAttributes()); //Registers the attributes (In-game stats) for the CUBE entity type. Game will crash if this is not done, apparently.
		//Like on line 24, I think this could be reformatted to the same as ItemsRegistry
	}

	public static Identifier id(String path){
		return Identifier.of(MOD_ID, path); //This is just something to call in place of "Identifier.of(AntfarmCooperativeAgentResearchProject.MOD_ID, name)".
	}										//Basically, it just saves a few characters.

}