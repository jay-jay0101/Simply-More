package net.rosemarythyme.simplymore.item.uniques;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.MimicryAttributesConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.registry.ModTagRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MimicryItem extends SimplyMoreUniqueSwordItem {
    protected final Style rightClickStyle = HelperMethods.getStyle("rightclick");
    protected final Style abilityStyle = HelperMethods.getStyle("ability");
    protected final Style textStyle = HelperMethods.getStyle("text");

    protected static MimicryAttributesConfig mimicryAttributes = config.mimicry;

    int skillBetweenComboCooldown = effect.getMimicryCooldownBetweenTypes();
    int skillCooldown = effect.getMimicryTypeCooldown();
    public static final int usageEffectTime = 9999999;

    public MimicryItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(user.hasStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get())) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return itemStack.getDamage() >= itemStack.getMaxDamage() - 1
                ? TypedActionResult.fail(itemStack)
                : TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user.getWorld().isClient || !(user instanceof PlayerEntity)) {
            super.usageTick(world, user, stack, remainingUseTicks);
            return;
        }

        if(remainingUseTicks == 1 && getAmplifier(stack.getItem()) != -1) {
            user.stopUsingItem();

            ((PlayerEntity) user).getItemCooldownManager().set(this, 1000);

            user.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.MIMICRY_HAPPENING.get(),
                            usageEffectTime,
                            getAmplifier(stack.getItem())
                    )
            );

            stack.getOrCreateNbt().putBoolean("simplymore:change", true);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }


    public static int getAmplifier(Item item) {
        for (RegistrySupplier<Item> registry : ModItemsRegistry.MIMICRY_AMPLIFIERS) {
            if(registry.get() == item) return ModItemsRegistry.MIMICRY_AMPLIFIERS.indexOf(registry);
        }

        return -1;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return effect.getMimicryWindup();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    int stepMod = 0;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getOrCreateNbt().contains("simplymore:change")
                && stack.getOrCreateNbt().getBoolean("simplymore:change")
                && entity instanceof PlayerEntity playerEntity
                && !playerEntity.hasStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get())
                && !playerEntity.getWorld().isClient
        ) {
            playerEntity.getItemCooldownManager().set(stack.getItem(), skillCooldown);
            String currentForm = null;

            for (Map.Entry<String, RegistrySupplier<Item>> itemEntry : ModItemsRegistry.MIMICRY_ITEMS.entrySet()) {
                if(itemEntry.getValue().get() == stack.getItem()) {
                    currentForm = itemEntry.getKey();
                }
            }

            String newForm = getWeightedRandomForm(currentForm, playerEntity);
            Item newItem = ModItemsRegistry.MIMICRY_ITEMS.get(newForm).get();
            playerEntity.getItemCooldownManager().set(newItem, skillBetweenComboCooldown);

            if(newItem instanceof MimicryItem mimicryItemToTransformInto) {
                NbtCompound nbt = stack.getNbt();
                ItemStack newItemStack = mimicryItemToTransformInto.getDefaultStack();
                nbt = nbt == null ? new NbtCompound() : nbt.copy();
                newItemStack.setNbt(nbt);
                newItemStack.getOrCreateNbt().putBoolean("simplymore:change", false);

                int slotIndex = playerEntity.getInventory().getSlotWithStack(stack);
                if(slotIndex != -1) {
                    playerEntity.getInventory().setStack(slotIndex, newItemStack);
                } else {
                    if(playerEntity.getOffHandStack() == stack) {
                        playerEntity.setStackInHand(Hand.OFF_HAND, newItemStack);
                    }
                }
            }
        }

        if(!stack.getOrCreateNbt().contains("simplymore:change")) {
            stack.getOrCreateNbt().putBoolean("simplymore:change", false);
        }

        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public Text getMimicryFormName() {
        return null;
    }


    public String checkItem(Item item) {
        for(Map.Entry<String, TagKey<Item>> tagKeyEntry : ModTagRegistry.MIMICRY_TAGS.entrySet()) {
            TagKey<Item> itemTagKey = tagKeyEntry.getValue();

            if (Registries.ITEM.getEntry(item).isIn(itemTagKey))
                return tagKeyEntry.getKey();
        }

        return null;
    }

    public boolean isFormEnabled(MimicryItem item, PlayerEntity user) {
        return !user.getItemCooldownManager().isCoolingDown(item) && !item.isFormDisabledInConfig();
    }

    public boolean isFormDisabledInConfig() {
        return false;
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if(otherStack.isIn(ModTagRegistry.ALL) && !player.hasStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get())) {
            String clickedItemType = checkItem(otherStack.getItem());
            Item itemToTransformInto = ModItemsRegistry.MIMICRY_ITEMS.get(clickedItemType).get();

            if(itemToTransformInto instanceof MimicryItem mimicryItemToTransformInto && isFormEnabled(mimicryItemToTransformInto, player)) {
                NbtCompound nbt = stack.getNbt();
                ItemStack newItem = mimicryItemToTransformInto.getDefaultStack();
                nbt = nbt == null? new NbtCompound():nbt.copy();
                newItem.setNbt(nbt);

                int slotIndex = player.getInventory().getSlotWithStack(stack);
                if(slotIndex != -1) {
                    player.getInventory().setStack(slotIndex, newItem);

                    player.giveItemStack(otherStack);
                    return true;
                }
            }
        }

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    public String getWeightedRandomForm(String currentForm, PlayerEntity player) {
        List<String> availableForms = new ArrayList<>();

        // list weapon items in inventory
        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = player.getInventory().getStack(i);

            if(itemStack.isIn(ModTagRegistry.ALL)) {
                String formString = checkItem(itemStack.getItem());
                if(formString != null && isFormEnabled(((MimicryItem) ModItemsRegistry.MIMICRY_ITEMS.get(formString).get()), player)) {
                    if(!availableForms.contains(formString)) {
                        availableForms.add(formString);
                    }
                }
            }
        }

        // Chance to turn into an item in your inventory, dependent on the amount of weapons in your inventory
        // max chance 50%
        int chance = Math.min(15 + (availableForms.size() * 10), 50);
        if(player.getRandom().nextBetween(1,100) < chance) {
            availableForms.clear();
        }

        // Cannot change into the current form
        availableForms.remove(currentForm);

        /* otherwise, choose randomly */
        if(availableForms.isEmpty()) {
            for(Map.Entry<String, RegistrySupplier<Item>> itemEntry : ModItemsRegistry.MIMICRY_ITEMS.entrySet()) {
                String formString =  itemEntry.getKey();
                Item formItem = itemEntry.getValue().get();

                if(isFormEnabled((MimicryItem) formItem, player) && !availableForms.contains(formString) && !formString.equals(currentForm)) {
                    availableForms.add(formString);
                }
            }
        }

        return availableForms.get(new Random().nextInt(availableForms.size()));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip7").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.mimicry.tooltip8",
                getMimicryFormName()).setStyle(rightClickStyle));
        appendSpecificTooltip(itemStack, world, tooltip, tooltipContext);

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    }

    public void knockback(PlayerEntity player, LivingEntity target, float strength) {
        Vec3d userPosition = player.getPos();
        Vec3d entityPosition = target.getPos();

        double deltaX = entityPosition.getX() - userPosition.getX();
        double deltaZ = entityPosition.getZ() - userPosition.getZ();
        double distance = Math.hypot(deltaX, deltaZ);

        if (distance == 0) {
            return;
        }

        double normalizedDeltaX = deltaX / distance;
        double normalizedDeltaZ = deltaZ / distance;

        target.setVelocity(normalizedDeltaX * strength, 0.4, normalizedDeltaZ * strength);
        target.velocityModified = true;
    }

    public void jump(LivingEntity target, float xzStrength, float yStrength) {
        Vector3d facingVector = SimplyMoreHelperMethods.getNormalised2dVector(target.getYaw()).mul(xzStrength);
        target.setVelocity(facingVector.x(), yStrength, facingVector.z());
        target.velocityModified = true;
    }

    public List<LivingEntity> sweepAttack(PlayerEntity player, float range) {
        return sweepAttack(player, range, 0);
    }

    public List<LivingEntity> sweepAttack(PlayerEntity player, float range, int angle) {
        Vec3d position = player.getEyePos();
        Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised2dVector(player.getYaw() + angle);

        Vec3d particlePos = new Vec3d(
                position.getX() + (normalisedVector.x() * range),
                position.getY(),
                position.getZ() + (normalisedVector.z() * range)
        );

        Box box = new Box(
                particlePos.getX() - range,
                particlePos.getY() - range,
                particlePos.getZ() - range,
                particlePos.getX() + range,
                particlePos.getY() + range,
                particlePos.getZ() + range
        );

        player.getWorld().playSound(null, particlePos.getX(), particlePos.getY(), particlePos.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1,0.5f);


        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 1, 0, 0 , 0, 0);

        return player.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                livingEntity -> livingEntity != player && !livingEntity.isTeammate(player)
        ).toList();
    }

    public List<LivingEntity> katanaAttack(PlayerEntity player, double x, double y, double z, float range) {

        Box box = new Box(
                x - range,
                y - range,
                z - range,
                x + range,
                y + range,
                z + range
        );

        ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, x, y, z, (int)Math.pow(range, 2)*10, range, 2, range, 1f);
        player.getWorld().playSound(null, x, y, z, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1f, 1.5f);


        return player.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                livingEntity -> livingEntity != player && !livingEntity.isTeammate(player)
        ).toList();
    }

    public List<LivingEntity> spinAttack(PlayerEntity player, float range) {

        Box box = new Box(
                player.getX() - range,
                player.getY() - 1.5,
                player.getZ() - range,
                player.getX() + range,
                player.getY() + 1.5,
                player.getZ() + range
        );

        for(int i = 0; i < 10; i++) {
            float particleRange = range / 1.5f;

            Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised2dVector(i * 36);

            double xPos = player.getX() + (normalisedVector.x() * particleRange);
            double yPos = player.getEyeY();
            double zPos = player.getZ() + (normalisedVector.z() * particleRange);

            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, xPos, yPos, zPos, 1, 0, 0 , 0, 0);
            player.getWorld().playSound(null, xPos, yPos, zPos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, player.getRandom().nextBetween(9,14)/10f);
        }

        return player.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                livingEntity -> livingEntity != player && !livingEntity.isTeammate(player)
        ).toList();
    }

    public List<LivingEntity> slamAttack(PlayerEntity player, float range) {

        Box box = new Box(
                player.getX() - range,
                player.getY() - 1.5,
                player.getZ() - range,
                player.getX() + range,
                player.getY() + 1.5,
                player.getZ() + range
        );

        ((ServerWorld) player.getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState()), player.getX(), player.getY(), player.getZ(), (int)Math.pow(range, 2)*10, range, 2, range, 1f);
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 1);


        return player.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                livingEntity -> livingEntity != player && !livingEntity.isTeammate(player)
        ).toList();
    }

    public List<LivingEntity> stabAttack(PlayerEntity player, int range, float coverage) {
        List<LivingEntity> targets = new ArrayList<>();

        for (int j = 0; j < range/coverage; j++) {

            Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised3dVector(player);



            double dX = normalisedVector.x() * j * coverage * 1.2f;
            double dY = normalisedVector.y() * j * coverage * 1.2f;
            double dZ = normalisedVector.z() * j * coverage * 1.2f;

            double x = player.getX() + dX;
            double y = player.getEyeY() + dY;
            double z = player.getZ() + dZ;

            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.CRIT, x, y, z, 20, 0.1f, 0.1f , 0.1f, 0.2f);
            player.getWorld().playSound(null, x, y, z, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 0.5f, 1);

            Box box = new Box(
                    x - coverage,
                    y - coverage,
                    z - coverage,
                    x + coverage,
                    y + coverage,
                    z + coverage
            );

            List<LivingEntity> entities = player.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                    livingEntity -> livingEntity != player && !livingEntity.isTeammate(player) && !targets.contains(livingEntity)

            ).toList();

            targets.addAll(entities);
        }

        return targets;
    }

    public static void breakShield(LivingEntity target) {
        if(target.isBlocking() && target instanceof PlayerEntity playerEntity) playerEntity.disableShield(true);
    }
}
