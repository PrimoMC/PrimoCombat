package net.primomc.PrimoCombat.Modules;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

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

public abstract class AbstractModule implements Listener
{
    private boolean enabled;

    public AbstractModule( ConfigurationSection section )
    {
        setEnabled( section.getBoolean( "enabled" ) );
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }
}
