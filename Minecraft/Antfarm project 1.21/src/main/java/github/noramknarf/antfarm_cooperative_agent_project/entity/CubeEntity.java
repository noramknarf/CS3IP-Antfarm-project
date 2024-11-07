package github.noramknarf.antfarm_cooperative_agent_project.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class CubeEntity extends PathAwareEntity {

    public CubeEntity(EntityType<? extends PathAwareEntity> entitytype, World world){
        super(entitytype, world);
    }
}
