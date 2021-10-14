package com.cartoonishvillain.observed.commands;

import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

public class SetObservedLevel {
    public static void register(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("setobservedlevel")
                .requires(cs -> {return cs.hasPermission(2);})
                .then(Commands.argument("target", GameProfileArgument.gameProfile()).then(Commands.argument("level", FloatArgumentType.floatArg(0, 100)).executes(context -> {
                    return setHauntChance(context.getSource(), GameProfileArgument.getGameProfiles(context, "target"), FloatArgumentType.getFloat(context, "level"));
                })))

        );
    }


    private static int setHauntChance(CommandSource source, Collection<GameProfile> profiles, float level){
        for(GameProfile gameProfile : profiles){
            ServerPlayerEntity serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            serverPlayerEntity.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                h.setObserveLevel(level);
            });
        }
        source.sendSuccess(new TranslationTextComponent("command.observed.success", level), false);
        return 0;
    }
}
