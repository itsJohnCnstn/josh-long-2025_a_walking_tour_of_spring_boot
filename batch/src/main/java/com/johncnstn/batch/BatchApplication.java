package com.johncnstn.batch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @Bean
    FlatFileItemReader<Dog> dogFlatFileItemReader(@Value("classpath:/dogs.csv") Resource resource) {
        return new FlatFileItemReaderBuilder<Dog>()
                .linesToSkip(1)
                .resource(resource)
                .name("dogsCsvToDb")
                .fieldSetMapper(fieldSet -> new Dog(fieldSet.readInt("id"),
                        fieldSet.readString("name"),
                        fieldSet.readString("owner"),
                        fieldSet.readString("description")
                ))
                .delimited().names("id,name,description,dob,owner,gender,image".split(","))
                .build();
    }

    @Bean
    JdbcBatchItemWriter<Dog> dogJdbcItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Dog>()
                .dataSource(dataSource)
                .assertUpdates(true)
                .sql("insert into dog (id, name, description, owner) values (?,?,?,?)")
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setInt(1, item.id());
                    ps.setString(2, item.name());
                    ps.setString(3, item.description());
                    ps.setString(4, item.owner());
                })
                .build();
    }

    @Bean
    Step csvToDbStep(JobRepository repository, PlatformTransactionManager transactionManager,
                     FlatFileItemReader<Dog> dogFlatFileItemReader,
                     JdbcBatchItemWriter dogJdbcItemWriter) {
        return new StepBuilder("csvToDbStep", repository)
                .<Dog, Dog>chunk(10, transactionManager)
                .reader(dogFlatFileItemReader)
                .writer(dogJdbcItemWriter)
                .build();
    }

    @Bean
    Job csvToDb(JobRepository repository, Step step) {
        return new JobBuilder("csvToDb", repository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}

record Dog(int id, String name, String owner, String description) {
}
