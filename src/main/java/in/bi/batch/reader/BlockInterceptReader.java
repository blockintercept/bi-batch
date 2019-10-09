/*******************************************************************************
 * Copyright (C) 2019 blockintercept
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package in.bi.batch.reader;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import in.bi.batch.reader.db_mysql.MySQLQueryService;
import in.bi.batch.utils.CommonConstants;

public class BlockInterceptReader implements ItemReader<Object> {

	org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MySQLQueryService mySQLQueryService;

	@Autowired
	private Environment environment;

	private boolean read = false;

	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (CommonConstants.DB_MYSQL.equals(environment.getProperty(CommonConstants.READER_TYPE))) {
			
			if (read) {
				return null;
			}
			logger.info("configuration found for mysql db reader");
			Map<String, List<Object>> data = mySQLQueryService.getData();
			read = true;
			return data;

		} else {
			logger.info("invalid or no config for " + CommonConstants.READER_TYPE);
			return null;
		}

	}

}
