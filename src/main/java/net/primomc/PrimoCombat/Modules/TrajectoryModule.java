package net.primomc.PrimoCombat.Modules;

import net.primomc.PrimoCombat.Annotations.Name;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

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

@Name( value = "trajectory" )
public class TrajectoryModule extends AbstractModule
{
    public TrajectoryModule( ConfigurationSection section )
    {
        super( section );
    }

    @EventHandler
    public void projectileLaunch( ProjectileLaunchEvent event )
    {
        if ( event.getEntity().getShooter() instanceof Player )
        {
            double velocityMultiplier = event.getEntity().getVelocity().length();
            Player player = (Player) event.getEntity().getShooter();
            Vector direction = player.getLocation().getDirection().clone();
            direction.normalize().multiply( velocityMultiplier );
            event.getEntity().setVelocity( direction );
        }
    }
}
