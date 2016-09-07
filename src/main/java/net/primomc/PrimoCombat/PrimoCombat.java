package net.primomc.PrimoCombat;

import net.primomc.PrimoCombat.Annotations.Name;
import net.primomc.PrimoCombat.Modules.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class PrimoCombat extends JavaPlugin
{
    private Map<String, Class<? extends AbstractModule>> moduleClasses = new HashMap<>();
    private Map<String, AbstractModule> modules = new HashMap<>();
    private static PrimoCombat instance;

    @Override
    public void onEnable()
    {
        instance = this;
        initModules();
        getServer().getPluginCommand( "primocombat" ).setExecutor( new CommandExecutor( this ) );
    }

    @Override
    public void onDisable()
    {

    }

    private void initModules()
    {
        findModules();
        loadModules();
    }

    private void findModules()
    {
        List<Class<? extends AbstractModule>> modules = Arrays.asList( BowBoostingModule.class, FishingRodModule.class, OffhandBowModule.class, FoodRegenModule.class );
        for ( Class<? extends AbstractModule> clazz : modules )
        {
            String name = clazz.getAnnotation( Name.class ).value();
            this.moduleClasses.put( name.toLowerCase(), clazz );
        }
    }

    private void loadModules()
    {
        ConfigurationSection section = getConfig().getConfigurationSection( "modules" );
        for ( String key : section.getKeys( false ) )
        {
            if ( section.isConfigurationSection( key ) && moduleClasses.containsKey( key.toLowerCase() ) )
            {
                initModule( key, section.getConfigurationSection( key ) );
            }
        }
    }

    public void initModule( String name, ConfigurationSection section )
    {
        name = name.toLowerCase();
        try
        {
            Class<? extends AbstractModule> moduleClass = moduleClasses.get( name );
            final Constructor<? extends AbstractModule> constructor = moduleClass.getConstructor( ConfigurationSection.class );
            AbstractModule module = constructor.newInstance( section );
            modules.put( name, module );
            if ( module.isEnabled() )
            {
                Bukkit.getServer().getPluginManager().registerEvents( module, this );
            }
        }
        catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e )
        {
            e.printStackTrace();
        }
    }

    public boolean disableModule( String name )
    {
        name = name.toLowerCase();
        if ( modules.containsKey( name ) )
        {
            HandlerList.unregisterAll( modules.get( name ) );
            return true;
        }
        return false;
    }

    public boolean enableModule( String name )
    {
        name = name.toLowerCase();
        if ( modules.containsKey( name ) )
        {
            Bukkit.getServer().getPluginManager().registerEvents( modules.get( name ), this );
            return true;
        }
        return false;
    }

    public static PrimoCombat getInstance()
    {
        return instance;
    }

}
