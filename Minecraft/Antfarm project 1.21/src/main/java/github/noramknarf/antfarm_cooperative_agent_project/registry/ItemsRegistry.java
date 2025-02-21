package github.noramknarf.antfarm_cooperative_agent_project.registry;
//Registry for items to be initialised

import github.noramknarf.antfarm_cooperative_agent_project.AntfarmCooperativeAgentResearchProject ;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemsRegistry {
    public static final Item EXAMPLE_ITEM = register( "example_item", new Item(new Item.Settings().maxCount(3)) );

    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, AntfarmCooperativeAgentResearchProject.id(name), item);
    }

    public static void init(){
        //This does nothing but is apparently needed to tell the mod to load this file
    }
}
