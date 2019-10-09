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
package in.bi.batch.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bi.batch.utils.CommonConstants;
import in.bi.ethsigner.rawdata.RawDataSigner;

@RestController
public class Controller {

Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@Autowired
	private JobExplorer jobExplorer;

	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	private RawDataSigner rawDataSigner;
	

	@Autowired
	private Environment environment;

	@RequestMapping(method = RequestMethod.GET, value = "/startAgent")
	public void startAgent() throws Exception {

		List<JobInstance> lastInstances = jobExplorer.getJobInstances(CommonConstants.BATCH_INTERCEPT_JOB_NAME, 0, 1);

		if (lastInstances.isEmpty()) {
			jobLauncher.run(job, new JobParameters());
		} else {
			jobOperator.startNextInstance(CommonConstants.BATCH_INTERCEPT_JOB_NAME);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getBlockchainData/{transaction}")
	public ResponseEntity<String> getData(@PathVariable String transaction) throws Exception {
		String data = rawDataSigner.getRawData(transaction,environment.getProperty(CommonConstants.RPC_NODE));
		return ResponseEntity.ok(data); 		
	}
	

}
