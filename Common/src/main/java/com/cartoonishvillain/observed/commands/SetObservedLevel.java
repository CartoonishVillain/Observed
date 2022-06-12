package com.cartoonishvillain.observed.commands;

import com.cartoonishvillain.observed.platform.Services;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class SetObservedLevel {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("observed").then(Commands.literal("setobservedlevel")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("target", GameProfileArgument.gameProfile()).then(Commands.argument("level", FloatArgumentType.floatArg(0, 100)).executes(context -> setObserveLevel(context.getSource(), GameProfileArgument.getGameProfiles(context, "target"), FloatArgumentType.getFloat(context, "level")))))

        ));
    }


    private static int setObserveLevel(CommandSourceStack source, Collection<GameProfile> profiles, float level){
        for(GameProfile gameProfile : profiles){
            ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            Services.PLATFORM.setValue(serverPlayerEntity, level);
        }
        source.sendSuccess(Component.translatable("command.observed.success", level), false);
        return 0;
    }
}
