package io.github.goadinggoat.BungeeQuarantine.Database;
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Objects.GoatPlayer;


public class PlayerDataBase extends DataBase {
	public static String table = "{table}";
	public static String dbName = "acceptedplayers";
	public static String SQLiteServerTokensTable = "CREATE TABLE IF NOT EXISTS " + table + " ("
			+ "uuid VARCHAR(100) NOT NULL,"
			+ "accepted TINYINT(1) DEFAULT 1,"
			+ "PRIMARY KEY (uuid),"
			+ "FOREIGN KEY (uuid) REFERENCES player(uuid)"
			+ ");";
	
	public static String SQLitePlayerTokensTable = "CREATE TABLE IF NOT EXISTS " + table + " ("
			+ "uuid VARCHAR(100) NOT NULL,"
			+ "PRIMARY KEY (uuid)" + ");";
	
	
	public PlayerDataBase(BungeeQuarantine pl) {
		super(pl,dbName);
	}
	
	public boolean createPlayerTable(){
		if (initTable(SQLitePlayerTokensTable.replace("{table}", "player"))){
			return true;
		}else{
			return false;
		}
	}

	public boolean createTable(String server){
		if (initTable(SQLiteServerTokensTable.replace("{table}", server))){
			return true;
		}else{
			return false;
		}
		
	}
	
	public SQLStates checkIfTableExists(String table) {
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("SELECT name FROM sqlite_master WHERE type = ? AND name = ?;");
			ps.setString(1, "table");
			ps.setString(2, table);
			if (ps.executeQuery().next()) {
				return SQLStates.TRUE;
			}
			return SQLStates.FALSE;

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return SQLStates.ERROR;
	}
	
	public SQLStates checkIfPlayerExists(String table,String uuid) {
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("SELECT uuid FROM " + table + " WHERE uuid = ?;");
			ps.setString(1, uuid);
			if (ps.executeQuery().next()){
				return SQLStates.TRUE;
			}
			return SQLStates.FALSE;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return SQLStates.ERROR;
	}

	public boolean insertPlayer( String uuid) {
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection
					.prepareStatement("INSERT INTO " + "player" + " (uuid) VALUES (?);");
			ps.setString(1, uuid);
			ps.executeUpdate();
			return true;
			
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}
	
	public GoatPlayer loadPlayer(String table, String uuid) {
		PreparedStatement ps = null;
		GoatPlayer Gplayer = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE uuid = ?;");
			ps.setString(1, uuid);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				Gplayer = new GoatPlayer(res.getString("uuid"));
			}
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return Gplayer;

	}
	
	public boolean insertPlayerIntoServer(String server, String uuid) {
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection
					.prepareStatement("INSERT INTO " + server + " (uuid,accepted) VALUES (?,1);");
			ps.setString(1, uuid);
			ps.executeUpdate();
			return true;
			
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}



	public boolean setPlayerAccepted(String server, String uuid,  Boolean accepted) {
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("UPDATE "+ server +" SET accepted = ? WHERE uuid = ?;");
			ps.setInt(1, accepted ? 1 : 0);
			ps.setString(2, uuid);
			ps.executeUpdate();
			return true;

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}
	public boolean setAllUnaccepted(String server) {

		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("UPDATE "+ server +" SET accepted = 0 WHERE accepted = 1;");
			ps.executeUpdate();
			return true;

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}

	public boolean loadPlayerAccepted(String server, GoatPlayer player) {
		PreparedStatement ps = null;
		try{
			connection = getSQLConnection();
			ps = connection.prepareStatement("SELECT uuid FROM "+ server +" WHERE uuid = ?");
			ps.setString(1, player.getUUID());
			ResultSet res = ps.executeQuery();
			while (res.next()){
				if (res.getInt("accepted")==1){
					player.setPlayerAcceptedRules(server, true);
				}else{
					player.setPlayerAcceptedRules(server, false);
				}
			}
			return true;
		}catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}



	
}