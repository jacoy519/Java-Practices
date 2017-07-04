package com.example.demo.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by medivh on 2017/7/3.
 */
@Component("dataSourceImportExportProcessor")
public class DataSourceImportExportProcessor {

    private final static Logger logger = Logger.getLogger(DataSourceImportExportProcessor.class);

    @Resource
    private DataSource dataSource;

    private Set<String> tableSet = new HashSet<>();

    @Value("${data.filePath}")
    private String filePath;

    private final static String EXPORT_DB_SQL_TEMP = "call CSVWRITE ( '%s', 'SELECT * FROM %s' )";

    private final static String IMPORT_DB_SQL_TEMP = "INSERT INTO  %s (SELECT * FROM CSVREAD('%s'))";

    @PostConstruct
    public void init() {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(new SimpleBeanDefinitionRegistry());
        scanner.resetFilters(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Table.class));
        Set<BeanDefinition> tableBeanDefinitions = scanner.findCandidateComponents("com.example.demo");
        for(BeanDefinition tableBeanDefinition : tableBeanDefinitions) {
            try {
                Class clazz = Class.forName(tableBeanDefinition.getBeanClassName());
                Table tableAnnotation = (Table)clazz.getAnnotation(Table.class);
                tableSet.add(tableAnnotation.name());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }



    public void exportDB(){
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            for(String tableName : tableSet) {
                String csvFilePath = filePath + tableName + ".csv";
                String sql = String.format(EXPORT_DB_SQL_TEMP, csvFilePath, tableName);
                statement.execute(sql);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void importDB() {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            for(String tableName : tableSet) {
                String csvFilePath = filePath + tableName + ".csv";
                String sql = String.format(IMPORT_DB_SQL_TEMP, tableName, csvFilePath);
                statement.execute(sql);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
