package br.com.senac.Cliente2.shedullers;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TesteJob implements Job {

    Logger logger = LoggerFactory.getLogger(TesteJob.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("teste job");
        logger.info("Teste Job");
    }
}
