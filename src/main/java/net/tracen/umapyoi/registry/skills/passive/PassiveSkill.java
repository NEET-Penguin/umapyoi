package net.tracen.umapyoi.registry.skills.passive;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.registry.skills.SkillType;
import net.tracen.umapyoi.registry.skills.UmaSkill;

import net.tracen.umapyoi.registry.skills.UmaSkill.Builder;

public class PassiveSkill extends UmaSkill {

    public PassiveSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        if(user instanceof Player player)
            player.displayClientMessage(Component.translatable("umapyoi.skill.passive"), true);
    }
    
    @Override
    public SkillType getType() {
        return SkillType.PASSIVE;
    }
    
    @Override
    public int getActionPoint() {
        return 0;
    }
    
}
