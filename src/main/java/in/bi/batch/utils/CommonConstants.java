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

public interface CommonConstants {

	String BATCH_INTERCEPT_JOB_NAME = "batchInterceptBatchName";
	String BATCH_INTERCEPT_STEP_ONE_NAME = "batchInterceptBatchStep1";
	int CHUNK_SIZE = 1;
	String TO_ADDRESS = "blockintercet.eth.blockchain.to.address";
	String PRIVATE_KEY_PARAM = "blockintercept.eth.blockchain.private.key";
	String RPC_NODE = "blockintercept.eth.blockchain.rpc.node";
	String READER_TYPE = "blockintercept.batch.readerType";
	String DB_MYSQL = "db_mysql";
		
	String MYSQL_DRIVER = "blockintercept.batch.readerType.db_mysql.driver";
	String MYSQL_URL = "blockintercept.batch.readerType.db_mysql.jdbc-url";
	String MYSQL_USERNAME = "blockintercept.batch.readerType.db_mysql.username";
	String MYSQL_PASSWORD = "blockintercept.batch.readerType.db_mysql.password";
	String MYSQL_QUERY = "blockintercept.batch.readerType.db_mysql.query";


}
