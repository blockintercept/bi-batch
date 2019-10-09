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
package in.bi.batch.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

	static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	public static byte[] convertMapToData(List<? extends Object> items) {
		Map<String, List<Object>> map = (Map<String, List<Object>>) items.get(0);
		StringBuffer data = new StringBuffer();
		for (String key : map.keySet()) {
			data.append(key).append("||||").append(map.get(key));
			
		}
		logger.info(data.toString());
		return data.toString().getBytes();
	}

}
