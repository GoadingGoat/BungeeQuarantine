package io.github.goadinggoat.BungeeQuarantine.Config;

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

import java.io.File;

import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.YamlConfig;

import net.md_5.bungee.api.plugin.Plugin;



public class MessagesConfig extends YamlConfig {
	public MessagesConfig(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "messages.yml");
	}

	@Comment("Contact Address, Please change to something useful, as it gets appended to disconnect messages")
	public String CONTACTADDRESS = "&aThe IDIOT who skipped the config of this plugin";
	@Comment("Network name, displayed in messages")
	public String NETWORKNAME = "&bGoat's MC Network";

	@Comment("Server disconnect messages")
	public String NOQUARANTINESERVERS = "&4[Rules] &fSorry, our new player first join server is missing in the config, please report the problem to: ";
	public String TARGETSERVERMISCONFIGURED = "&4[Rules] &fSorry, our new player first join server is misconfigured, please report the problem to: ";
	public String TARGETSERVEROFFLINE = "&4[Rules] &fSorry, our new player first join server is offline, please try again in a few minutes. If the problem persists, report to: ";

	@Comment("Canceled event messages")
	public String CHATEVENTCANCELLED = "&4[Rules] &fYou need to accept our rules before you can chat!";
	public String COMMANDEVENTCANCELLED = "&4[Rules] &fYou need to accept our rules before you can run commands!";

	@Comment("Rules header messages")
	public String GLOBALRULESHEADER = "&6Global rules:";
	public String SERVERRULESHEADER = "&b{server} rules:";

	@Comment("/acceptrules messages")
	
	public String ACCEPTRULESHEADER = "&a[Rules] &fWelcome to {networkname}&f!, you need to accept our rules before you can play.";
	public String REACCEPTRULESHEADER = "&a[Rules] &fWelcome back to {networkname}&f! We have updated our rules, please accept the new rules to keep playing.";
	public String ACCEPTRULESQUESTION = "&e===============&3Will you accept our rules?&e===============\n";
	public String RULESALREADYACCPETED = "&a[Rules] &fThankyou, you have already accepted our rules.";
	public String RULESACCEPTED = "&a[Rules] &fThankyou for accepting the rules, you may now use our servers.";
	public String RULESDECLINED = "&4[Rules] &fYou have been kicked from the server, as you did not agree to our rules!";
	public String TOOMANYARGSACCEPT = "&4[Rules] &fYou parsed too many Argumnets to /acceptrules, please relog and try again! \n The only way to accept the rules is by running /acceptrules, then clicking [accept].";
	public String NORULESTOACCEPT = "&a[Rules] &fThis server has no rules to accept";
	
	
	@Comment("Config reload messages")
	public String CONFIGRELOADEDSUCCESSFULLY = "&a[Rules] &fConfiguration reloaded.";
	public String CONFIGRELOADFAILED = "&4[Rules] &fFailed to reload config.";

	@Comment("No permission error message")
	public String NOPERMISSION = "&4[Rules] &fYou do not have the required permssion!";
	
	public String MUSTBERUNASPLAYER = "&4[Rules] &fCommand must be run by a player!";

	@Comment("Standardised error messages")
	public String TOOMANYARGS = "&4[Rules] &fToo many Arguments!";
	public String TOOFEWARGS = "&4[Rules] &fNot Enough Arguments!";
	public String INVALIDARG = "&4[Rules] &fInvalid Argument!";
	public String INVALIDSERVER = "&4[Rules] &fInvalid Server!";
	public String INVALIDNUMBER = "&4[Rules] &fInvalid Number Argument!";
	public String NUMBEROUTOFRANGE = "&4[Rules] &fNumber Out Of Range!";
	public String UUIDNOTFOUNDINDB = "&4[Rules] &fUUID not found in database!";
	
	
	public String YAMLFILENOTEXISTS = "&4[Rules] &fYaml file does not exist, do /rules {server} create to create it!";
	public String YAMLNOTLOADEDINMEMORY = "&4[Rules] &fYaml is not loaded in memory!";
	public String FILEDOESNOTEXIST = "&4[Rules] &fFile does not exist!";
	public String YAMLFILEALREADYEXISTS = "&4[Rules] &fYaml file already exists, delete it if you would like to recreate it, or clear it!";
	public String FAILEDTOADDRULE = "&4[Rules] &fFailed to add rule to {server}.";
	public String FAILEDTOSAVERULES = "&4[Rules] &fFailed to save rules for {server}.";
	public String FAILEDTOCREATEYAMLFILE = "&4[Rules] &fFailed to create YAML file for {server}.";
	public String YAMLFILECREATED = "&a[Rules] &fSuccessfully created YAML file for {server}.";
	public String YAMLFILEDELETED = "&a[Rules] &fSuccessfully deleted YAML file for {server}.";
	public String ADDEDRULETOYAMLFILE = "&a[Rules] &fSuccessfully add rule to end of YAML file for {server}.";
	public String INSERTEDRULEINTOYAMLFILE = "&a[Rules] &fSuccessfully inserted rule at position {pos} in YAML file for {server}.";
	public String YAMLRULESCLEARED = "&a[Rules] &fSuccefully cleared rules of {server}.";
	public String MOVEDRULE = "&a[Rules] &fSuccefully moved rule {pos} to {num} on {server}.";
	public String REMOVEDRULE = "&a[Rules] &fSuccefully removed rule {pos} on {server}.";
	
	
	public String DROPPEDLIVERULES = "&a[Rules] &fSuccefully dropped rules for {server} from database.";
	public String PULLEDLIVE = "&a[Rules] &fSuccessfully pulled live rules from database for {server}.";
	public String PUSHEDLIVE = "&a[Rules] &fSuccessfully pushed rules to live database for {server}.";
	
	
	@Comment("BungeeCord logger messages")
	public String PLAYERLOAD = "Loaded player &9{player}&7 ({uuid}).";
	public String PLAYERLOADCACHED = "Loaded player from cache &9{player}&7 ({uuid}).";
	public String PLAYERCREATE = "Created player &b{player}&7 ({uuid}).";
	public String PLAYERUNLOAD = "Unloaded player &c{player}.";

	@Comment("Help messages")
	public String HELPRULES = "&e/rules &f- &bSee the global rules.";
	public String HELPRULESSERVER = "&e/rules <server> &f- &bSee the rules of specified server.";
	public String HELPRULESLISTSERVERS = "&e/rules listservers &f- &bLists enabled servers.";
	public String HELPSERVERRULES = "&e/srules &f- &bSee the rules of the current server.";
	public String HELP = "&e/rules help &f- &bPlugin help.";

	@Comment("Admin Help messages")
	public String HELPRELOAD = "&e/rules reload  &f- &bReload the config.";
	public String HELPADD = "&e/rules <server> add [rule] &f- &bAdd a rule to specified server.";
	public String HELPINSERT = "&e/rules <server> insert <pos> [rule] &f- &bInsert a rule at specified pos.";
	public String HELPMOVE = "&e/rules <server> move <pos> <up/down> <amount> &f- &bMove a rule to specified pos.";
	public String HELPREMOVE = "&e/rules <server> remove <pos> &f- &bRemove a rule from server at specified pos.";
	public String HELPCLEAR = "&e/rules <server> clear &f- &bClear the rules of specified server.";
	public String HELPCLEARACCEPTEDPLAYER = "&e/rules clearacceptedplayer <player> &f- &bSets player as not accepted rules.";
	public String HELPCLEARACCEPTEDPLAYERS = "&e/rules clearacceptedplayers &f- &bSets all players as not accepted rules.";
	public String HELPPUSHLIVE = "&e/rules <server> pushlive &f- &bPush debug rules to live. If server = Global, sets all players unnaccepted.";
	public String HELPPULLLIVE = "&e/rules <server> pulllive &f- &bPull live rules to debug, for editing.";
	public String HELPCONFIRM = "&e/rules confirm &f- &bConfirm potentially dangerous action.";


	public String PLAYERSETUNNACCEPTED = "&a[Rules] &f{UUID} set unaccepted.";
	public String SETALLPLAYERSUNACCEPTED = "&a[Rules] &fSet all players unaccepted.";
	
	public String NORULES = "&a[Rules] &fThis server currently has no rules defined.";
	public String LIVERULESNOTEXIST = "&4[Rules] &fLive rules do not exist for {server}!";
	
	public String ACCEPTEDPLAYERSERVERNOTEXISTS = "&4[Rules] &fAccepted player database does not exist for {server}!";
	
	
	
	@Comment("Command Confirmation Messages")
	public String CONFIRMCLEAR = "&4[Rules] &fAre you sure you want to clear the rules for {server}? do '/rules confirm' to execute";
	public String CONFIRMDELETE = "&4[Rules] &fAre you sure you want to delete {server}? do '/rules confirm' to execute";
	public String CONFIRMPULLLIVE = "&4[Rules] &fAre you sure you want to overwrite the debug rules for {server}? do '/rules confirm' to execute";
	public String CONFIRMPUSHLIVE = "&4[Rules] &fAre you sure you want to update the live rules for {server}? do '/rules confirm' to execute";
	public String CONFIRMDROP = "&4[Rules] &fAre you sure you want to drop the live rules for {server}? do '/rules confirm' to execute";
	public String CONFIRMCLEARPLAYERS = "&4[Rules] &fAre you sure you want to set all player unaccepted for {server}? do '/rules confirm' to execute";
	public String CONFIRMCLEARPLAYER = "&4[Rules] &fAre you sure you want to set {uuid} unaccepted for {server}? do '/rules confirm' to execute";
	public String NOCOMMANDTOCONFIRM = "&4[Rules] &fThere was no command to confirm, or it timed out";
	
	
}
