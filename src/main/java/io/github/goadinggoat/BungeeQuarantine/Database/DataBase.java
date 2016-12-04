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


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;

public class DataBase {

	protected BungeeQuarantine plugin;
	protected Connection connection;
	private String databaseName;

	public DataBase(BungeeQuarantine pl, String dbName) {
		plugin = pl;
		databaseName = dbName;
	}
	
	public enum SQLStates {
		TRUE, FALSE, ERROR
	};

	// SQL creation stuff, You can leave the blow stuff untouched.
	protected Connection getSQLConnection() {
		File dataFolder = new File(plugin.getDataFolder(), Paths.get("Storage", "db", databaseName + ".db").toString());
		;
		if (!dataFolder.exists()) {
			try {
				if (dataFolder.getParentFile() != null) {
					dataFolder.getParentFile().mkdirs();
				}
				dataFolder.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.SEVERE, "File write error: " + databaseName + ".db");
			}
		}
		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			return connection;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. It should have been shaded with this plugin. Contact plugin dev");
		}
		return null;
	}

	public boolean initTable(String SQLiteCreateTokensTable) {
		connection = getSQLConnection();
		try {
			Statement s = connection.createStatement();
			if (SQLiteCreateTokensTable != null) {
				s.executeUpdate(SQLiteCreateTokensTable);
			}
			s.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
    public static String sqlConnectionCloseError(){
        return "Failed to close MySQL connection: ";
    }

}