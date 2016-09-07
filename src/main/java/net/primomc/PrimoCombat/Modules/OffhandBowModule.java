package net.primomc.PrimoCombat.Modules;

import net.primomc.PrimoCombat.Annotations.Name;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright 2016 Luuk Jacobs
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Name( value = "offhandbow" )
public class OffhandBowModule extends AbstractModule
{
    public OffhandBowModule( ConfigurationSection section )
    {
        super( section );
    }

    @EventHandler
    public void inventoryClick( InventoryClickEvent event )
    {
        if ( event.getWhoClicked() instanceof Player && event.getSlot() == 40 && event.getCursor().getType().equals( Material.BOW ) )
        {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void inventorySwap( PlayerSwapHandItemsEvent event )
    {
        if ( event.getOffHandItem().getType().equals( Material.BOW ) )
        {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void inventoryDrag( InventoryDragEvent event )
    {
        if ( event.getInventorySlots().contains( 40 ) )
        {
            for ( ItemStack stack : event.getNewItems().values() )
            {
                if ( stack.getType().equals( Material.BOW ) )
                {
                    event.setCancelled( true );
                    return;
                }
            }

        }
    }
}
