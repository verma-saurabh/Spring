package com.Spring.FrameWork.Spring.config;

import com.Spring.FrameWork.Spring.constants.Constants;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class BatchJobConfiguration {

    ApplicationProperties properties;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Bean
    public Job job(Step step) throws Exception {
        return jobBuilderFactory
                .get(Constants.JOB_NAME)
                .validator(validator())
                .start(step)
                .build();
    }

    @Bean
    public JobParametersValidator validator() {
        return new JobParametersValidator() {
            @Override
            public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
                String fileName = jobParameters.getString(Constants.JOB_NAME);
                if (StringUtils.isBlank(fileName)) {
                    throw new JobParametersInvalidException("Filename param is required");
                }

                try {
                    Path file = Paths.get(properties.getBatch().getInputpath() + File.separator + fileName);

                    if (Files.notExists(file) || !Files.isReadable(file)) {
                        throw new Exception("File does not exist or is not readable");
                    }
                } catch (Exception e) {
                    throw new JobParametersInvalidException("location has to be valid");
                }
            }
        };
    }
}
