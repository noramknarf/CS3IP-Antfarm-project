package renderer;

import github.noramknarf.antfarm_cooperative_agent_project.entity.custom.CubeEntity;
import github.noramknarf.antfarm_cooperative_agent_project.AntfarmCooperativeAgentResearchProjectClient;
import github.noramknarf.antfarm_cooperative_agent_project.model.CubeEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CubeEntityRenderer extends MobEntityRenderer<CubeEntity, CubeEntityModel> {

    public CubeEntityRenderer(EntityRendererFactory.Context context){
        super(context, new CubeEntityModel(context.getPart(AntfarmCooperativeAgentResearchProjectClient.CUBE_MODEL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(CubeEntity entity){
        return Identifier.of("AntfarmCooperativeAgentResearchProject", ""/*This is where the texture path goes*/);
    }
}
