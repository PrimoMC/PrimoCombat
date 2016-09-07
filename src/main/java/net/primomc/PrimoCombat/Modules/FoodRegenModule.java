package net.primomc.PrimoCombat.Modules;

import net.primomc.PrimoCombat.Annotations.Name;
import net.primomc.PrimoCombat.PrimoCombat;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

@Name( value = "customfoodregen" )
public class FoodRegenModule extends AbstractModule
{
    int maxRegen;
    double foodPerHealth;
    int regenTime;
    Set<UUID> set = Collections.synchronizedSet( new HashSet<UUID>() );

    public FoodRegenModule( ConfigurationSection section )
    {
        super( section );
        maxRegen = section.getInt( "max-regen" );
        foodPerHealth = section.getDouble( "food-per-health" );
        regenTime = section.getInt( "regen-time" );
    }

    @EventHandler
    public void onRegen( EntityRegainHealthEvent event )
    {
        if ( event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED )
        {
            event.setCancelled( true );
        }
    }

    @EventHandler
    public void onEat( FoodLevelChangeEvent event )
    {
        if ( event.getEntity() instanceof Player )
        {
            final Player player = (Player) event.getEntity();
            if ( event.getFoodLevel() >= 20 && !set.contains( player.getUniqueId() ) )
            {
                new BukkitRunnable()
                {
                    int i = 0;
                    int regened = 0;
                    double fraction = 0;

                    @Override
                    public void run()
                    {
                        if ( i % regenTime == 0 )
                        {

                            int decimalFoodPerHealth = (int) ( foodPerHealth + fraction );
                            fraction = foodPerHealth - decimalFoodPerHealth;
                            if ( player.getHealth() < player.getMaxHealth() )
                            {
                                player.setHealth( player.getHealth() + 1 );
                                player.setFoodLevel( player.getFoodLevel() - decimalFoodPerHealth > 0 ? player.getFoodLevel() - decimalFoodPerHealth : 0 );
                                regened++;
                            }

                            if ( regened >= maxRegen )
                            {
                                this.cancel();
                            }
                        }
                        i++;
                    }
                }.runTaskTimer( PrimoCombat.getInstance(), 1, 1 );
            }
        }
    }
}
