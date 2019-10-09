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
package in.bi.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.bi.batch.processor.BlockInterceptProcessor;
import in.bi.batch.reader.BlockInterceptReader;
import in.bi.batch.utils.CommonConstants;
import in.bi.batch.writer.BlockInterceptWriter;
import in.bi.ethsigner.rawdata.RawDataSigner;

@Configuration
public class BatchInterceptJobConfig {
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private JobRegistry jobRegistry;


    @Bean
    public Job batchInterceptBatchName() {
    	Job job = this.jobBuilderFactory.get(CommonConstants.BATCH_INTERCEPT_JOB_NAME)
                .start(batchInterceptBatchStep1()).incrementer(new RunIdIncrementer())
                .build();
    	logger.info("created job instance...");
    	try {
    		
			jobRegistry.register(new ReferenceJobFactory(job));
		} catch (DuplicateJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return job;
    }

    @Bean
    public Step batchInterceptBatchStep1() {
        return this.stepBuilderFactory.get(CommonConstants.BATCH_INTERCEPT_STEP_ONE_NAME)
        		 .chunk(CommonConstants.CHUNK_SIZE)
                 .reader(reader())
                 .processor(processor())
                 .writer(writer())
                 .build();
                                  
    }
    
	

   
    @Bean
    public ItemWriter<? super Object> writer() {	
		return new BlockInterceptWriter();
	}
    @Bean
    public ItemProcessor<? super Object, ? extends Object> processor() {		
		return new BlockInterceptProcessor();
	}
    @Bean
    public ItemReader<? extends Object> reader() {	
		return new BlockInterceptReader();
	}

    @Bean
	RawDataSigner createRawDataSigner() {
		return new RawDataSigner();
	}

}
