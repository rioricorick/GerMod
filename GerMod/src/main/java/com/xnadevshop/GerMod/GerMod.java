package com.xnadevshop.GerMod;

import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class GerMod extends JavaPlugin {
	@Override
    public void onEnable() {
		for (int i = 0; i < 10; i++)
			getLogger().info("Thanks for using Ger & Locke's special mod!!");
		// TODO Insert logic to be performed when the plugin is enabled
    }
 
    @Override
    public void onDisable() {
    	for (int i = 0; i < 10; i++)
    		getLogger().info("You have disabled Ger & Locke's Mod!!!!!!!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("Ger")) {
    		getLogger().info("You use the Ger command!! Very Nice!");
    		Server server = getServer();
    		Player rick = server.getPlayer("rioricorick");
    		rick.sendMessage(ChatColor.GREEN + "GerMod Activated!!!!");
    		getLogger().info(rick.getLocation().toString());
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("gloc")) {
    		//Server server = getServer();
    		if (args.length > 0)
    		{
    			if (sender instanceof Player)
    			{
    				Player player = (Player) sender;
    				getServer().getPlayer(args[0]);
    				Location playerLoc = player.getLocation();
    				Location targetLoc = getServer().getPlayer(args[0]).getLocation();
    				player.sendMessage(ChatColor.GREEN + args[0] +
    						"'s location is " +
    						Math.round(targetLoc.getX()) + ", " +
    						Math.round(targetLoc.getY()) + ", " +
    						Math.round(targetLoc.getZ()));
    				double distance = Math.pow(playerLoc.getX() - targetLoc.getX(), 2);
    				distance += Math.pow(playerLoc.getZ() - targetLoc.getZ(), 2);
    				distance = Math.sqrt(distance);
    				player.sendMessage(ChatColor.YELLOW + "At a distance of " +
    						Math.round(distance) + " meters!");
    				return true;
    			}
    		}
    		if (sender instanceof Player)
			{
				Player player = (Player) sender;
				Location playerLoc = player.getLocation();
				player.sendMessage(ChatColor.GREEN + "Your location is " +
						Math.round(playerLoc.getX()) + ", " +
						Math.round(playerLoc.getY()) + ", " +
						Math.round(playerLoc.getZ()));
				return true;
			}
    	}
    	if (cmd.getName().equalsIgnoreCase("jail"))
    	{
    		if (sender instanceof Player)
    		{
    			Player player = (Player) sender;
    			Block b = null;
    			player.sendMessage("Player has been instantiated");
    			Location playerLocation = player.getLocation();
    			player.sendMessage("args.length = " + args.length);
    			if (args.length == 3)
    			{
    				playerLocation.setX(Integer.parseInt(args[0]));
    				int startX = (int)playerLocation.getX();
    				//player.sendMessage("X: " + playerLocation.getX());
    				playerLocation.setY(Integer.parseInt(args[1]));
    				int startY = (int)playerLocation.getY();
    				//player.sendMessage("Y: " + playerLocation.getY());
    				playerLocation.setZ(Integer.parseInt(args[2]));
    				int startZ = (int)playerLocation.getZ();
    				//player.sendMessage("Z: " + playerLocation.getZ());
    				int endX = startX + 10;
    				int endY = startY + 10;
    				int endZ = startZ + 10;
    				for (int i = startX; i < endX; i++)
    				{
    					playerLocation.setX(i);
    					for (int j = startY; j < endY; j++)
    					{
    						playerLocation.setY(j);
    						for (int k = startZ; k < endZ; k++)
    						{
    							playerLocation.setZ(k);
    							if ((k == startZ) || (k == endZ - 1) ||
    								(j == startY) || (j == endY - 1) ||
    								(i == startX) || (i == endX - 1))
    							{
    								b = playerLocation.getBlock();
    								b.setType(Material.BEDROCK);
    							}
    							//player.sendMessage(playerLocation.getX() + " " + playerLocation.getY() + " " + playerLocation.getZ());
    						}
    					}
    				}
    			}
    		}
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("gline"))
    	{
    		Player player = null;
    		Location playerLocation = null;
    		if (sender instanceof Player)
    		{
    			player = (Player) sender;
				playerLocation = player.getLocation();
    		}
    		if (args.length == 1)
    		{
    			if (isInteger(args[0])) // The only argument is a number
    			{
    				int lineLength = Integer.parseInt(args[0]);
    				int lineStart = (int)Math.floor(playerLocation.getX());
    				//player.sendMessage("Length: " + lineLength);
    				for (int i = lineStart + 1; i < lineLength + lineStart + 1; i++)
    				{
    					//player.sendMessage("Iterator is " + i);
    					playerLocation.setX(i);
    					Block block = playerLocation.getBlock();
    					block.setType(Material.STONE);
    				}
    			return true;
    			}
    			else // The only argument is a String
    			{
    				Location otherPlayerLocation = getServer().getPlayer(args[0]).getLocation();
    				//player.sendMessage("Other player's location: " + otherPlayerLocation.toString());
    				playerLocation.setY(playerLocation.getY() - 1);
    				otherPlayerLocation.setY(otherPlayerLocation.getY() - 1);
    				//player.sendMessage("Your location: " + playerLocation.toString());
    				//player.sendMessage("Other player's location: " + otherPlayerLocation.toString());
    				double verticalSlope = (otherPlayerLocation.getY() - playerLocation.getY()) /
    						(otherPlayerLocation.getX() - playerLocation.getX());
    				double horizontalSlope = (otherPlayerLocation.getZ() - playerLocation.getZ()) /
    						(otherPlayerLocation.getX() - playerLocation.getX());
    				int endX = (int)otherPlayerLocation.getX();
    				double stepSize = 1;
    				horizontalSlope *= stepSize;
    				verticalSlope *= stepSize;
    				double xIncrement = (otherPlayerLocation.getX() - playerLocation.getX() > 0 ? stepSize : -stepSize);
    				while ((int)playerLocation.getX() != endX)
    				{
    					Block b = playerLocation.getBlock();
    					b.setType(Material.OBSIDIAN);
    					Location torchLocation = new Location(playerLocation.getWorld(),
    							(int)playerLocation.getX(),
    							(int)playerLocation.getY() + 1,
    							(int)playerLocation.getZ());
    					b = torchLocation.getBlock();
    					b.setType(Material.TORCH);
    					playerLocation.setX(playerLocation.getX() + xIncrement);
    					playerLocation.setY(playerLocation.getY() + verticalSlope * xIncrement);
    					playerLocation.setZ(playerLocation.getZ() + horizontalSlope * xIncrement);
    				}
    				player.sendMessage("Line to " + getServer().getPlayer(args[0]).getName() + " is complete!");
    				return true;
    			}
    		} // One argument
    	}
    	// No applicable command was issued.
    	return false; 
    }
    
    private boolean isInteger(String s)
    {
    	try
    	{
    		Integer.parseInt(s);
    	}
    	catch(Exception e)
    	{
    		return false;
    	}
    	return true;
    }
}