package net.primomc.PrimoCombat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

import static net.primomc.PrimoCombat.Messages.Messages.getBasicMessage;
import static net.primomc.PrimoCombat.Messages.Messages.tl;

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

public class CommandExecutor implements org.bukkit.command.CommandExecutor
{
    private PrimoCombat plugin;

    public CommandExecutor( PrimoCombat plugin )
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args )
    {
        if ( args.length < 1 )
        {
            sendHelp( sender, false, "" );
        }
        else
        {
            String[] subArgs = new String[0];
            if ( args.length > 1 )
            {
                subArgs = Arrays.copyOfRange( args, 1, args.length );
            }
            switch ( args[0] )
            {
                case "enable":
                    enableCommand( sender, subArgs );
                    break;
                case "disable":
                    disableCommand( sender, subArgs );
                    break;
                default:
                    sendHelp( sender, true, args[0] );
            }
        }
        return true;

    }

    private void disableCommand( CommandSender sender, String[] args )
    {
        if ( args.length < 1 )
        {
            sender.sendMessage( tl( "notenoughargs", "/primocombat disable <module>" ) );
        }
        if ( plugin.disableModule( args[0] ) )
        {
            sender.sendMessage( getBasicMessage( true, "disabled", args[0] ) );
        }
        else
        {
            sender.sendMessage( getBasicMessage( true, "nosuchmodule", args[0] ) );
        }
    }

    private void enableCommand( CommandSender sender, String[] args )
    {

        if ( args.length < 1 )
        {
            sender.sendMessage( tl( "notenoughargs", "/primocombat disable <module>" ) );
        }
        if ( plugin.enableModule( args[0] ) )
        {
            sender.sendMessage( getBasicMessage( true, "enabled", args[0] ) );
        }
        else
        {
            sender.sendMessage( getBasicMessage( true, "nosuchmodule", args[0] ) );
        }
    }

    private void sendHelp( CommandSender sender, boolean wrongcommand, String label )
    {
        if ( wrongcommand )
        {
            sender.sendMessage( getBasicMessage( true, "wrongcommand", label ) );
        }
        sender.sendMessage( tl( "helpheader" ) );
        sender.sendMessage( tl( "disablehelp" ) );
        sender.sendMessage( tl( "enablehelp" ) );
    }
}
