package com.hnyp.axon.test;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import com.hnyp.axon.test.aggregate.VportAggregate;
import com.hnyp.axon.test.command.CreateVportCommand;
import com.hnyp.axon.test.command.UpdateVportNameCommand;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.AggregateConfigurer;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class App {

    public static void main(String[] args) {
        Configuration config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(
                        AggregateConfigurer
                                .defaultConfiguration(VportAggregate.class)
                                .configureRepository(
                                        c -> new EventSourcingRepository<>(VportAggregate.class, eventStore()))

                )
                .buildConfiguration();

        config.start();

        config.commandBus().dispatch(asCommandMessage(new CreateVportCommand("new vport 1")));
        config.commandBus().dispatch(asCommandMessage(new UpdateVportNameCommand("new vport 1", "vport name")));

    }

    private static EventStore eventStore() {
        LocalContainerEntityManagerFactoryBean emFactoryBean = entityManagerFactory();
        emFactoryBean.afterPropertiesSet();

        EntityManagerFactory emFactory = emFactoryBean.getObject();

        EntityManager em = emFactory.createEntityManager();

        EntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(em);
        TransactionManager tx = new SpringTransactionManager(new JpaTransactionManager(emFactory));

        return new EmbeddedEventStore(new JpaEventStorageEngine(entityManagerProvider, tx));
    }

    private static LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("eventStore");
//        em.setPackagesToScan(new String[] { "org.baeldung.persistence.model" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    private static DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/axon-test");
        dataSource.setUsername("root");
        dataSource.setPassword("toor");
        return dataSource;
    }

    private static Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }

}
