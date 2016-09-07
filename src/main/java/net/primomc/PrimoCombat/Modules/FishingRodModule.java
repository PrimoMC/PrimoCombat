package net.primomc.PrimoCombat.Modules;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.primomc.PrimoCombat.Annotations.Name;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.EnumMap;

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

@Name( value = "fishingrod" )
public class FishingRodModule extends AbstractModule
{
    public FishingRodModule( ConfigurationSection section )
    {
        super( section );
    }

    @EventHandler
    public void onPlayerFish( PlayerFishEvent event )
    {
        if ( event.getState() != PlayerFishEvent.State.CAUGHT_ENTITY )
        {
            return;
        }
        if ( !( event.getCaught() instanceof Player ) )
        {
            return;
        }
        Player shooter = event.getPlayer();
        Player caught = (Player) event.getCaught();
        if ( shooter.getUniqueId() == caught.getUniqueId() )
        {
            return;
        }
        Bukkit.getPluginManager().callEvent( createDamageEvent( shooter, caught ) );
        Location loc = caught.getLocation().add( 0, 0.5, 0 );
        caught.teleport( loc );
        caught.setVelocity( loc.subtract( shooter.getLocation() ).toVector().normalize().multiply( 0.4 ) );
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    private EntityDamageByEntityEvent createDamageEvent( Player rodder, Player player )
    {
        return new EntityDamageByEntityEvent( rodder, player, EntityDamageEvent.DamageCause.ENTITY_ATTACK, new EnumMap( ImmutableMap.of( EntityDamageEvent.DamageModifier.BASE, 0.1D ) ), new EnumMap( ImmutableMap.of( EntityDamageEvent.DamageModifier.BASE, Functions.constant( 0.1D ) ) ) );
    }
}
