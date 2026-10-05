package net.rosemarythyme.simplymore.client.animations;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class CrowAnimations {
	public static final Animation FLAP = Animation.Builder.create(0.5F)
			.addBoneAnimation("wing0", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-8.8394F, -6.6959F, 106.9761F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.addBoneAnimation("wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-8.8394F, 6.6959F, -106.9761F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
			))
			.build();

	public static final Animation SWOOP = Animation.Builder.create(0.0F).looping()
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-47.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg0", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(47.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(47.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("wing0", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-3.1339F, -17.2257F, 50.4749F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("wing1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-3.1339F, 17.2257F, -50.4749F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("all", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(60.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();
}