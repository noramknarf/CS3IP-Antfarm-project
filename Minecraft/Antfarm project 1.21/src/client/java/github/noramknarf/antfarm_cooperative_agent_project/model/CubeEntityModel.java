package github.noramknarf.antfarm_cooperative_agent_project.model;

import github.noramknarf.antfarm_cooperative_agent_project.entity.CubeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;


public class CubeEntityModel extends EntityModel<CubeEntity> {

    private final ModelPart base;

    public CubeEntityModel(ModelPart modelPart){
        this.base = modelPart.getChild(EntityModelPartNames.CUBE);
    }

    public static TexturedModelData getTexturedModelData() {    //Copy-pasted directly from the fabric wiki. This is just a simple piece of code to generate a plain white cube as the texture.
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-6F, 12F, -6F, 12F, 12F, 12F), ModelTransform.pivot(0F, 0F, 0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(CubeEntity entity, float limbAngle, float limbDistances, float animationProgress, float headYaw, float headPitch){
    //This does nothing. It would be where we define the angles of the model's parts, but as this is just a cube, we only need to define this method because it exists in the parent class
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color){
        base.render(matrices, vertices, light, overlay, color);
    }
}
