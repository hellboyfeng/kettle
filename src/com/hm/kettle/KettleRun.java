package com.hm.kettle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Maps;


/**
 * kettle执行
 * @Description
 * @date 2014年11月24日  
 * @author fengping
 */
public class KettleRun {
	static Log logger = LogFactory.getLog(KettleRun.class);
	private static HashMap<String, KettleType> kettleTypes = Maps.newHashMap();
	static{
		init();
	}
	/**
	 * 初始化kettle.properties配置文件
	 * @Description
	 * @param 
	 * @return 
	 * @date 2014年11月27日  
	 * @author fengping
	 */
	public static void init() {
		try {
			Properties prop = new Properties();
			prop.load(KettleRun.class.getResourceAsStream("/kettle.properties"));
			PropertiesParser parser = new PropertiesParser(prop);
			String[] typeNames = parser.getPropertyGroups("kettle");
			for (String typeName : typeNames) {
				Properties pp = parser.getPropertyGroup("kettle." + typeName,
						true, null);
				KettleType kettleType = new KettleType();
				kettleType.setName(typeName);
				kettleType.setKtr(Boolean.valueOf(pp.getProperty("ktr")));
				kettleType.setKtrPath(pp.getProperty("ktrpath"));
				kettleType.setKjb(Boolean.valueOf(pp.getProperty("kjb")));
				kettleType.setKjbPath(pp.getProperty("kjbpath"));
				kettleType.setSchedule(pp.getProperty("schedule"));
				kettleTypes.put(typeName, kettleType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws SchedulerException, IOException, InterruptedException  {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		for (Entry<String, KettleType> entry : kettleTypes.entrySet()) {
			KettleType kt = entry.getValue();
			if(!kt.isKtr()) continue;
			JobDetail jobDetail = JobBuilder.
					newJob(KettleTask.class).
					withIdentity(kt.getName(), "ktr").
					build();
			jobDetail.getJobDataMap().put("ktrpath", kt.getKtrPath());
			CronTrigger cronTrigger = TriggerBuilder.
					newTrigger().
					withIdentity(kt.getName(), "ktr").
					withSchedule(CronScheduleBuilder.cronSchedule(kt.getSchedule())).
					build();
			scheduler.scheduleJob(jobDetail, cronTrigger);
		}
		scheduler.start();
	/*	if(args.length>0&&"stop".equals(args[0]))
			scheduler.shutdown(true);*/
	}

}
