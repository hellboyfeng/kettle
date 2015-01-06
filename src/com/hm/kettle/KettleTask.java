package com.hm.kettle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * 定时任务执行
 * @Description
 * @date 2014年11月27日  
 * @author fengping
 */
public class KettleTask implements Job {
	static Log logger = LogFactory.getLog(KettleRun.class);
	public void runKtr(String ktrPath) {
		try {
			logger.info("********" + ktrPath + " transform start********");
			KettleEnvironment.init();
			TransMeta transMeta = new TransMeta(
					KettleRun.class.getResourceAsStream(ktrPath), null, false,
					null, null);
			Trans trans = new Trans(transMeta);
			trans.prepareExecution(null);
			trans.startThreads();
			trans.waitUntilFinished();
			logger.info("********" + ktrPath + " transform finished********");
		} catch (Exception e) {
			logger.error(ktrPath + "   " + e.getMessage());
		}
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		String ktrPath = context.getJobDetail().
				getJobDataMap().
				getString("ktrpath");
		runKtr(ktrPath);
	}

}
