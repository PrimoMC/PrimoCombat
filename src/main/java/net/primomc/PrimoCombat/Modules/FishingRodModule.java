package net.primomc.PrimoCombat.Modules;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.primomc.PrimoCombat.Annotations.Name;
import net.primomc.PrimoCombat.PrimoCombat;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class FishingRodModule extends AbstractModule implements Listener
{
    Map<UUID, UUID> isCaught = new HashMap<>();

    public FishingRodModule( ConfigurationSection section )
    {
        super( section );
    }

    @EventHandler
    public void onPlayerFish( ProjectileHitEvent event )
    {
        if ( event.getEntity().getType() != EntityType.FISHING_HOOK )
        {
            return;
        }
        if ( !( event.getEntity().getShooter() instanceof Player ) )
        {
            return;
        }
        Player shooter = (Player) event.getEntity().getShooter();

        Collection<Entity> entities = event.getEntity().getNearbyEntities( 0.3, 0.3, 0.3 );

        for ( Entity caught : entities )
        {
            if ( shooter.getUniqueId() == caught.getUniqueId() )
            {
                continue;
            }

            if ( isCaught.containsKey( shooter.getUniqueId() ) && isCaught.get( shooter.getUniqueId() ) == caught.getUniqueId() )
            {
                continue;
            }
            PrimoCombat.getInstance().getLogger().info( "fake damage packet" );
            PacketContainer damageAnimation = new PacketContainer( PacketType.Play.Server.ENTITY_STATUS );
            damageAnimation.getIntegers().write( 0, caught.getEntityId() );
            damageAnimation.getBytes().write( 0, (byte) 2 );
            for ( Player player : caught.getNearbyEntities( 32, 32, 32 ).stream().filter( e -> e instanceof Player ).map( e -> (Player) e ).collect( Collectors.toSet() ) )
            {
                try
                {
                    ProtocolLibrary.getProtocolManager().sendServerPacket( player, damageAnimation );
                }
                catch ( InvocationTargetException e )
                {
                    e.printStackTrace();
                }
            }
            Location loc = caught.getLocation().add( 0, 0.5, 0 );
            caught.teleport( loc );
            caught.setVelocity( loc.subtract( shooter.getLocation() ).toVector().normalize().multiply( 0.4 ) );
            isCaught.put( shooter.getUniqueId(), caught.getUniqueId() );
            break;
        }
    }

    @EventHandler
    public void onFish( PlayerFishEvent event )
    {
        if ( event.getState() != PlayerFishEvent.State.FISHING )
        {
            if ( isCaught.containsKey( event.getPlayer().getUniqueId() ) )
            {
                PrimoCombat.getInstance().getLogger().info( "is no longer caught" );
                isCaught.remove( event.getPlayer().getUniqueId() );
            }
        }
    }
}
