package br.com.senac.Cliente2.shedullers;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class TesteTriggerBuild {

    @Bean
    public JobDetail jobDetail(){
        JobDetail job = JobBuilder.newJob(TesteJob.class)
                .withIdentity("testeJob","grupo1")
                .storeDurably()
                .build();

        return job;
    }

    @Bean
    public Trigger trigger(JobDetail job){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerTeste", "triggerGrupo1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *"))
                .forJob(job)
                .build();

        return trigger;
    }
}
