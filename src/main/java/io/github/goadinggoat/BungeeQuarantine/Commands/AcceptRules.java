package io.github.goadinggoat.BungeeQuarantine.Commands;

/**
 * 
 *  BungeeQuarantine
    Copyright (C) 2016  Sean Fleck
    email: goadinggoat@gmail.com

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Util.displayrules;
import io.github.goadinggoat.BungeeQuarantine.Database.DataBase.SQLStates;
import io.github.goadinggoat.BungeeQuarantine.Objects.GoatPlayer;
import io.github.goadinggoat.BungeeQuarantine.managers.PlayerManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AcceptRules extends Command {
	private BungeeQuarantine plugin;
	private static final int iterations = 1000;
	private static final int desiredKeyLen = 120;

	public AcceptRules(BungeeQuarantine pl) {
		super("acceptrules", null, new String[] {});
		plugin = pl;
	}

	@Override
	public void execute(CommandSender sender, String[] arg) {
		if (!(sender instanceof ProxiedPlayer)) {
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) sender;
		String server = player.getServer().getInfo().getName();
		GoatPlayer goatPlayer = PlayerManager.getPlayer(player);
		boolean reaccept;// is the player re-accepting the rules?
		// need to check whether server has rules to accept first...
		if (plugin.playerDataBase.checkIfTableExists(server) == SQLStates.FALSE) {
			player.sendMessage(plugin.msg(plugin.messages.NORULESTOACCEPT));
			return;
		}
		if (goatPlayer.servers.containsKey(server)) {
			if (goatPlayer.servers.get(server) == true) {
				// player has already accepted the rules
				player.sendMessage(plugin.msg(plugin.messages.RULESALREADYACCPETED));
				return;
			} else {
				reaccept = true;
			}
		} else {
			reaccept = false;
		}
		if (arg.length == 0) {
			if (plugin.config.AcceptRules == 1) {
				player.sendMessage(acceptRulesMsg(player, "Global", reaccept));
			}
			if (plugin.config.AcceptRules == 2) {
				player.sendMessage(acceptRulesMsg(player, player.getServer().getInfo().getName(), reaccept));
			}
		} else if (arg.length == 1) {
			if (arg[0].equalsIgnoreCase(hash("iagree" + player.getUniqueId().toString(), plugin.salt))) {

				if (plugin.playerDataBase
						.checkIfTableExists(player.getServer().getInfo().getName()) == SQLStates.TRUE) {
					if ((plugin.playerDataBase.checkIfPlayerExists(player.getServer().getInfo().getName(),
							player.getUniqueId().toString()) == SQLStates.FALSE)) {
						plugin.playerDataBase.insertPlayerIntoServer(player.getServer().getInfo().getName(),
								player.getUniqueId().toString());
					} else {
						plugin.playerDataBase.setPlayerAccepted(player.getServer().getInfo().getName(),
								goatPlayer.getUUID(), true);
					}
					goatPlayer.servers.put(server, true);
				}
				player.sendMessage(plugin.msg(plugin.messages.RULESACCEPTED));
			} else if (arg[0].equalsIgnoreCase(hash("idisagree" + player.getUniqueId().toString(), plugin.salt))) {
				player.disconnect(plugin.msg(plugin.messages.RULESDECLINED));
			} else {
				player.disconnect(plugin.msg(plugin.messages.TOOMANYARGSACCEPT));
			}
		} else {
			player.disconnect(plugin.msg(plugin.messages.TOOMANYARGSACCEPT));
		}
	}

	// generate rules and accept/decline text
	public TextComponent acceptRulesMsg(ProxiedPlayer player, String server, boolean reaccept) {
		TextComponent message = new TextComponent();
		// this isn't valid check anymore...
		if (!reaccept) {
			message.addExtra(plugin
					.msg(plugin.messages.ACCEPTRULESHEADER.replace("{networkname}", plugin.messages.NETWORKNAME)));
		} else {
			message.addExtra(plugin
					.msg(plugin.messages.REACCEPTRULESHEADER.replace("{networkname}", plugin.messages.NETWORKNAME)));
		}
		message.addExtra("\n");
		TextComponent rules = new displayrules(plugin).getFormattedRules(server, true);
		if (rules != null) {
			message.addExtra(rules);
		} else {
			// should the player need to accept the rules if this occurs,
			// probably not... need to fix?
			message.addExtra(plugin.msg(plugin.messages.NORULES));
			message.addExtra("\n");
		}
		message.addExtra(plugin.msg(plugin.messages.ACCEPTRULESQUESTION));
		message.addExtra("\n");
		message.addExtra("                   ");
		TextComponent accept = new TextComponent("[Accept]");
		accept.setColor(ChatColor.GREEN);
		accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
				"/acceptrules " + hash("iagree" + player.getUniqueId().toString(), plugin.salt)));
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder("Click to accept the rules!").create()));
		message.addExtra(accept);
		message.addExtra("                   ");
		TextComponent decline = new TextComponent("[Decline]");
		decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
				"/acceptrules " + hash("idisagree" + player.getUniqueId().toString(), plugin.salt)));
		decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder("Click to decline the rules!").create()));
		decline.setColor(ChatColor.RED);
		message.addExtra(decline);
		return message;
	}

	// Hashing of on click command args to make them unique to each player.
	private static String hash(String password, byte[] salt) {

		SecretKeyFactory f;
		SecretKey key;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

			key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
			return Base64.encodeBase64String(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}