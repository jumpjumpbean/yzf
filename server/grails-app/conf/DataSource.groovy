import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

def pros
try {
    pros = PropertiesLoaderUtils.loadProperties(new ClassPathResource("app-config.properties"))
} catch (IOException e) {
    e.printStackTrace();
}
grails{
    filePath{
        imagePath = pros.getProperty("img.filePath")
    }
}
dataSource {
//    pooled = true
//    jmxExport = true
//    driverClassName = "org.h2.Driver"
//    username = "sa"
//    password = ""
    pooled = true
    driverClassName = pros.getProperty("jdbc.driverClassName")
    username = pros.getProperty("jdbc.username")
    password = pros.getProperty("jdbc.password")
    //loggingSql=true
    //	dialect = org.hibernate.dialect.MySQLInnoDBDialect
    properties {
        maxActive = Integer.parseInt(pros.getProperty("jdbc.properties.maxActive"))
        maxIdle = Integer.parseInt(pros.getProperty("jdbc.properties.maxIdle"))
        minIdle = Integer.parseInt(pros.getProperty("jdbc.properties.minIdle"))
        initialSize = Integer.parseInt(pros.getProperty("jdbc.properties.initialSize"))
        minEvictableIdleTimeMillis = Integer.parseInt(pros.getProperty("jdbc.properties.minEvictableIdleTimeMillis"))
        timeBetweenEvictionRunsMillis = Integer.parseInt(pros.getProperty("jdbc.properties.timeBetweenEvictionRunsMillis"))
        maxWait = Integer.parseInt(pros.getProperty("jdbc.properties.maxWait"))
        validationQuery = "select 1"
        testOnBorrow = true
        testWhileIdle = true
    }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
//    singleSession = true // configure OSIV singleSession mode
}

// environment specific settings
environments {

    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = pros.getProperty("jdbc.url")
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = pros.getProperty("jdbc.url")
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            url = pros.getProperty("jdbc.url")
            properties {
               // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
               jmxEnabled = true
               initialSize = 5
               maxActive = 50
               minIdle = 5
               maxIdle = 25
               maxWait = 10000
               maxAge = 10 * 60000
               timeBetweenEvictionRunsMillis = 5000
               minEvictableIdleTimeMillis = 60000
               validationQuery = "SELECT 1"
               validationQueryTimeout = 3
               validationInterval = 15000
               testOnBorrow = true
               testWhileIdle = true
               testOnReturn = false
               jdbcInterceptors = "ConnectionState"
               defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
            }
        }
    }
}