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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;

public class RulesDataBase extends DataBase {
	public static String table = "{server}";
	public static String dbName = "rules";
	public static String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS " + table + " ("
			+ "number INTEGER(8) DEFAULT 1," + "rule VARCHAR(255) NOT NULL," + "PRIMARY KEY (number)" + ");";

	public RulesDataBase(BungeeQuarantine pl) {
		super(pl, dbName);
	}

	public boolean createTable(String server) {
		if (initTable(SQLiteCreateTokensTable.replace("{server}", server))) {
			return true;
		} else {
			return false;
		}

	}

	public SQLStates checkIfServerExists(String server) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("SELECT name FROM sqlite_master WHERE type = ? AND name = ?;");
			ps.setString(1, "table");
			ps.setString(2, server);
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
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return SQLStates.ERROR;
	}

	public ArrayList<String> getRules(String server) {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<String> rules = new ArrayList<String>();
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("SELECT * FROM "+server+" ORDER BY number ASC;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rules.add(rs.getString("rule"));
			}
			return rules;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return null;
	}

	public boolean setRule(String server, String rule, int pos) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("INSERT OR REPLACE INTO "+server+" VALUES(?, ?);");

			ps.setInt(1, pos);
			ps.setString(2, rule);
			ps.executeUpdate();
			ps.close();

			return true;

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}

	public boolean dropServer(String server) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement("DROP TABLE "+server+";");
			ps.executeUpdate();
			return true;

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return false;
	}

	public ArrayList<String> listServers() {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<String> servers = new ArrayList<String>();
		try {
			connection = getSQLConnection();
			ps = connection.prepareStatement(
					"SELECT name FROM sqlite_master WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%';");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				servers.add(rs.getString("name"));
			}
			return servers;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, sqlConnectionCloseError(), ex);
			}
		}
		return null;
	}

}