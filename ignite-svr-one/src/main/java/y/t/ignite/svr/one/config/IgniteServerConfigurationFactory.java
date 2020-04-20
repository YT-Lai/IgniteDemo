package y.t.ignite.svr.one.config;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.cache.configuration.Factory;
import javax.sql.DataSource;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.DB2Dialect;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import com.ibm.db2.jcc.DB2SimpleDataSource;

public class IgniteServerConfigurationFactory {
	private static IgniteDb2ConnProperties db2ConnProperties;
	
	public static void setDb2ConnProperties(IgniteDb2ConnProperties properties) {
    	IgniteServerConfigurationFactory.db2ConnProperties = properties;
    }
    
    public static class DataSources {
        public static final DB2SimpleDataSource INSTANCE_dsDB2_Insdb = createdsDB2_Insdb();

        private static DB2SimpleDataSource createdsDB2_Insdb() {
        	DB2SimpleDataSource dsDB2_Insdb = new DB2SimpleDataSource();

            dsDB2_Insdb.setServerName(db2ConnProperties.getServer_name());
            dsDB2_Insdb.setPortNumber(Integer.parseInt(db2ConnProperties.getPort_number()));
            dsDB2_Insdb.setDatabaseName(db2ConnProperties.getDatabase_name());
            dsDB2_Insdb.setDriverType(Integer.parseInt(db2ConnProperties.getDriver_type()));
            dsDB2_Insdb.setUser(db2ConnProperties.getUsername());
            dsDB2_Insdb.setPassword(db2ConnProperties.getPassword());

            return dsDB2_Insdb;
        }
    }

    /**
     * Configure grid.
     *
     * @return Ignite configuration.
     * @throws Exception If failed to construct Ignite configuration instance.
     **/
    public static IgniteConfiguration createConfiguration() throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setIgniteInstanceName("DemoIgniteCache");

        TcpDiscoverySpi discovery = new TcpDiscoverySpi();

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();

        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47510"));

        discovery.setIpFinder(ipFinder);

        cfg.setDiscoverySpi(discovery);

        cfg.setCacheConfiguration(cacheIgnite1Cache());

        return cfg;
    }

    /**
     * Create configuration for cache "".
     *
     * @return Configured cache.
     * @throws Exception if failed to create cache configuration.
     **/
    public static CacheConfiguration cacheIgnite1Cache() throws Exception {
        CacheConfiguration ccfg = new CacheConfiguration();

        ccfg.setName("Ignite1Cache");
        ccfg.setCacheMode(CacheMode.PARTITIONED);
        ccfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        ccfg.setBackups(1);
        ccfg.setReadFromBackup(true);
        ccfg.setCopyOnRead(true);

        CacheJdbcPojoStoreFactory cacheStoreFactory = new CacheJdbcPojoStoreFactory();

        cacheStoreFactory.setDataSourceFactory(new Factory<DataSource>() {
            /** {@inheritDoc} **/
            @Override
            public DataSource create() {
                return DataSources.INSTANCE_dsDB2_Insdb;
            };
        });

        cacheStoreFactory.setDialect(new DB2Dialect());

        cacheStoreFactory.setTypes(jdbcTypeIgnite1Cache(ccfg.getName()));

        ccfg.setCacheStoreFactory(cacheStoreFactory);

        ccfg.setReadThrough(true);
        ccfg.setWriteThrough(true);
        ccfg.setEagerTtl(true);

        ArrayList<QueryEntity> qryEntities = new ArrayList<>();

        QueryEntity qryEntity = new QueryEntity();

        qryEntity.setKeyType("y.t.ignite.svr.one.entity.dbeb.Ignite1Key");
        qryEntity.setValueType("y.t.ignite.svr.one.entity.dbeb.Ignite1");

        HashSet<String> keyFields = new HashSet<>();

        keyFields.add("fieldOne");

        qryEntity.setKeyFields(keyFields);

        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        fields.put("fieldOne", "java.lang.String");
        fields.put("fieldTwo", "java.lang.String");
        fields.put("fieldThree", "java.lang.String");
        fields.put("fieldFour", "java.lang.String");

        qryEntity.setFields(fields);

        HashMap<String, String> aliases = new HashMap<>();

        aliases.put("fieldOne", "FIELD_ONE");
        aliases.put("fieldTwo", "FIELD_TWO");
        aliases.put("fieldThree", "FIELD_THREE");
        aliases.put("fieldFour", "FIELD_FOUR");

        qryEntity.setAliases(aliases);
        qryEntities.add(qryEntity);

        ccfg.setQueryEntities(qryEntities);

        return ccfg;
    }

    /**
     * Create JDBC type for "jdbcTypeDtebz010".
     *
     * @param cacheName Cache name.
     * @return Configured JDBC type.
     **/
    private static JdbcType jdbcTypeIgnite1Cache(String cacheName) {
        JdbcType type = new JdbcType();

        type.setCacheName(cacheName);
        type.setKeyType("y.t.ignite.svr.one.entity.dbeb.Ignite1Key");
        type.setValueType("y.t.ignite.svr.one.entity.dbeb.Ignite1");
        type.setDatabaseSchema("DBEB");
        type.setDatabaseTable("Ignite1");

        type.setKeyFields(new JdbcTypeField(Types.VARCHAR, "FIELD_ONE", String.class, "fieldOne"));

        type.setValueFields(
        		new JdbcTypeField(Types.VARCHAR, "FIELD_ONE", String.class, "fieldOne"),
                new JdbcTypeField(Types.VARCHAR, "FIELD_TWO", String.class, "fieldTwo"),
        		new JdbcTypeField(Types.VARCHAR, "FIELD_THREE", String.class, "fieldThree"),
                new JdbcTypeField(Types.CHAR, "FIELD_FOUR", String.class, "fieldFour"));
        return type;
    }
}