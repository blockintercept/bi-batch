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
package in.bi.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import in.bi.batch.utils.CommonConstants;
import in.bi.batch.utils.CommonUtils;
import in.bi.ethsigner.rawdata.RawDataSigner;


public class BlockInterceptWriter implements ItemWriter<Object> {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RawDataSigner rawDataSigner;

	@Autowired
	private Environment environment;

	@Override
	public void write(List<? extends Object> items) throws Exception {
		if (CommonConstants.DB_MYSQL.equals(environment.getProperty(CommonConstants.READER_TYPE))) {
			logger.info("converting data to bytes");
			byte[] data = CommonUtils.convertMapToData(items);

			String transaction = rawDataSigner.addRawData(data, environment.getProperty(CommonConstants.PRIVATE_KEY_PARAM),
					environment.getProperty(CommonConstants.RPC_NODE),
					environment.getProperty(CommonConstants.TO_ADDRESS));
			logger.info("sent data to blockchain, transaction "+transaction);
		}
	}


}
