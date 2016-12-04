#BungeeQuarantine - Alpha Release - Probably contains many bugs!

Disclaimer: 
	Before you go ahead and download this, please note that support for this plugin may be limited.
	This is my first attempt at a publishable plugin, the code in it may not be optimal,
	because I'm still learning java, and I'm used to writing Embedded C/C++ code, it probably contains 
	lots of magic numbers, and lacks consistency in implementation.
	
Plugin Support:
	I write plugins to use on the network of servers i maintain. I only write them because I cannot find
	something suitable. The community is more than welcome to request features, but the honest truth is i probably
	will not add them, I just don't have the time - try your luck though, you never know, I might. The plugin will
	likely be kept up to date, as I need it for my network.

License:
	This plugin is licensed under GNU GENERAL PUBLIC LICENSE Version 2, June 1991.
	
Alpha Release:
	For the alpha release while i said I said I'm probably not going to add features, i'm happy to iron out bugs, try to optimise the code,
	if people can provide detailed useful information on the bugs or better implementation. Feedback on the suitability of
	certain functionality is also welcome, changes to command names, or the messages they display. I aim to publish a beta release at a later date
	and would like to have most things sorted by then, and everything implemented described below.
	Current stability:
		- The rules editing, and rules databases should perform as described below with only minor bugs.
		- Players accepting the rules, is still a bit up in the air (as i recently changed the player database layout)
		- Quarantining needs work, the Bungeecord developers (as of build 1206) have enforced the idea that players should
		not be left only connected to the proxy, the current code does this, and I'm searching for a remedy that still allows
		keeping the custom disconnect messages, may have to ask BungeeCord developers if they would add extra functionality...
		- About command needs to be written
		- Server checking needs work
		- need to implement deleteplayer command.
		- need to add some of the last minute commands i have written to the help file
		- Probably has lots of bugs.
		

BungeeQuarantine is a Bungeecord plugin that helps you manage server rules, and getting players to accept them.
It does not require a special group be made for unaccepted players, like those implemented in bukkit/spigot.
The plugin aims to be as fair as possible to all players. Owners, admins/staff cannot be exempted from accepting the rules.

Features:
	- Debug and live rules, so rules can be edited and reviewed, then pushed live
	- All players must accept rules, no exceptions. (in chat click of [accept] or [decline])
	- Intercepts server connection events and redirects to quarantine servers (will redirect forced hosts).
	- Customisable messages (mainly for servers that aren't english).
	Global Rules:
		- Set global rules.
		- Player accept global rules.
		- Cancel chat events till players accept rules.
		- Cancel non-approved commands till players accept rules.
		- Quarantine players to a server or set of servers till they accept the Global rules (Experimental Load balancing for sets of servers).
	Server Rules:
		- Set rules per server.
		- Player accept server rules (must specify which servers, and the rules must exist for that server for check to be enforced).
		- Players cannot be Quarantined per server, they will not be able to chat or run commands though.
		
Player Commands:
	- /rules - list global rules.
	- /rules <server> list rules of specified server.
	- /rules about - gives brief description about plugin.
	- /rules listservers - lists servers.
	- /srules - list rules of current server.
	- /rules help - displays plugin help.
	
Admin Commands:
	- /rules reload - reloads config and messages.
	- /rules confirm - confirms execution of dangerous commands.
	- /rules deleteplayer <player> - deletes a player from the database.
	- /rules <server> clearacceptedplayer <player> - removes accepted flag of player
	- /rules <server> clearacceptedplayers - removes accepted flag of all players
	Rules Editing:
	- /rules <server> show - shows the debug rules, if they exist.
	- /rules <server> create - creates a new debug rules file
	- /rules <server> delete - deletes a debug rules file
	- /rules <server> add [Rule] - appends a rule
	- /rules <server> insert <pos> [rule] - inserts a rule at specified position
	- /rules <server> move <pos> {up/down} <amount> - moves a rule to new position
	- /rules <server> remove <pos> - removes a rules from specified position
	- /rules <server> clear - clears all rules
	- /rules <server> pulllive - pulls live rules to debug, for editing
	- /rules <server> pushlive - pushs debug rules to live, and may set players unaccepted on server.
	- /rules <server> droplive - drops live rules from database.
	
Permissions:
	- bq.admin - gives access to the admin commands
	
SourceCode:



	
	