/*******************************************************************************
 * * Copyright (C) 2019 blockintercept
 *  * 
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  * 
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Lesser General Public License for more details.
 *  * 
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package in.bi.batch.reader.db_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import in.bi.batch.utils.CommonConstants;

/**
 * Mysql Single data fetch method.
 * 
 * @author blockintercept
 *
 */

@Service
public class MySQLQueryService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Environment environment;

	public Map<String, List<Object>> getData() throws SQLException {
		Connection con = null;
		try {
			Class.forName(environment.getProperty(CommonConstants.MYSQL_DRIVER));
			con = DriverManager.getConnection(environment.getProperty(CommonConstants.MYSQL_URL),
					environment.getProperty(CommonConstants.MYSQL_USERNAME),
					environment.getProperty(CommonConstants.MYSQL_PASSWORD));
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(environment.getProperty(CommonConstants.MYSQL_QUERY));
			return resultSetToArrayList(rs);

		} catch (Exception e) {
			if (con != null) {
				con.close();
			}
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Map<String, List<Object>> resultSetToArrayList(ResultSet rs) throws SQLException {

		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		Map<String, List<Object>> row = new HashMap<>(columns);
		while (rs.next()) {
			for (int i = 1; i <= columns; ++i) {
				if (row.get(md.getColumnName(i)) != null) {
					row.get(md.getColumnName(i)).add(rs.getObject(i));
				} else {
					row.put(md.getColumnName(i), new ArrayList<Object>());
					row.get(md.getColumnName(i)).add(rs.getObject(i));
				}
			}
		}
		return row;
	}

}