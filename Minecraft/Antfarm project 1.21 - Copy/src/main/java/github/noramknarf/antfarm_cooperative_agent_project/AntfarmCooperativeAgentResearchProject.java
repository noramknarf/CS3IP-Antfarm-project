package github.noramknarf.antfarm_cooperative_agent_project;

import github.noramknarf.antfarm_cooperative_agent_project.registry.ItemsRegistry;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntfarmCooperativeAgentResearchProject implements ModInitializer {
	public static final String MOD_ID = "antfarm_cooperative_agent_project";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("antfarm_cooperative_agent_project");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("This is a message :D");
		ItemsRegistry.init();
	}

	public static Identifier id(String path){
		return Identifier.of(MOD_ID, path);
	}

}