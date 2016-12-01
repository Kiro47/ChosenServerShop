package com.chosencraft.purefocus.shops.menu;

import com.chosencraft.purefocus.shops.WrappedPlayer;
import org.bukkit.inventory.ItemStack;

public interface IMenuItem
{

	boolean onClick(WrappedPlayer player);

	ItemStack generateDisplay(WrappedPlayer player);

}
