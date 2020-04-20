package y.t.ignite.svr.one.service;

import java.util.List;

import javax.cache.Cache.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.stereotype.Service;

import y.t.ignite.svr.one.cache.dto.SimpleCacheBodyDto;
import y.t.ignite.svr.one.entity.dbeb.Ignite1;

@Service
public class IgniteSvrService {
	
	
	public void putValueIntoCache(List<SimpleCacheBodyDto> contentList) {
		Ignite ignite = Ignition.ignite("selfControlCache");
		CacheConfiguration<String, SimpleCacheBodyDto> cacheConf = new CacheConfiguration<>();
		cacheConf.setName("SimpleCache");
		cacheConf.setIndexedTypes(String.class, SimpleCacheBodyDto.class);
		IgniteCache<String, SimpleCacheBodyDto> cache = ignite.getOrCreateCache(cacheConf);
		
		for(SimpleCacheBodyDto eachContent : contentList) {
			cache.put(eachContent.getCity(), eachContent);
		}
		
		return;
	}
	
	
	public SimpleCacheBodyDto getObjectFromCacheBySql(String cityName) {
		Ignite ignite = Ignition.ignite("selfControlCache");
		CacheConfiguration<String, SimpleCacheBodyDto> cacheConf = new CacheConfiguration<>();
		cacheConf.setName("SimpleCache");
		cacheConf.setIndexedTypes(String.class, SimpleCacheBodyDto.class);
		IgniteCache<String, SimpleCacheBodyDto> cache = ignite.getOrCreateCache(cacheConf);
		
		String sqlString = "city = ?";
		
		SqlQuery<String, SimpleCacheBodyDto> sqlQuery = new SqlQuery<>(SimpleCacheBodyDto.class, sqlString);

        QueryCursor<Entry<String, SimpleCacheBodyDto>> cursor = cache.query(sqlQuery.setArgs(cityName));
        
        List<Entry<String, SimpleCacheBodyDto>> cacheResultList = cursor.getAll();
        
        if(CollectionUtils.isEmpty(cacheResultList)) {
        	return null;
        }else {
        	for(Entry<String, SimpleCacheBodyDto> elem : cacheResultList) {
        		return elem.getValue();
        	}
        }
        
        return null;
	}
	
	
	public SimpleCacheBodyDto getObjectFromCacheByKey(String cityName) {
		Ignite ignite = Ignition.ignite("selfControlCache");
		IgniteCache<String, SimpleCacheBodyDto> cache = ignite.getOrCreateCache("SimpleCache");
		
		if(cache.containsKey(cityName)) {
			return cache.get(cityName);
		}else {
			return null;
		}
	}
	
	
	public Ignite1 getParticularIgnite1Cache(String key) {
		Ignite ignite = Ignition.ignite("DemoIgniteCache");
		IgniteCache<String, Ignite1> Ignite1TableCache = ignite.cache("Ignite1Cache");
		
		Ignite1TableCache.loadCache(null);
		
		String sqlString = "FIELD_ONE = '" + key + "'";
		
		return sqlQueryCache(sqlString, Ignite1TableCache);
	}
	
	
	public void cleanCache(String igniteInstanceName, String cacheName) {
		Ignite ignite = Ignition.ignite(igniteInstanceName);
		IgniteCache<String, Ignite1> Ignite1TableCache = ignite.cache(cacheName);
		
		Ignite1TableCache.clear();
	}
	
	
	private Ignite1 sqlQueryCache(String sqlString, IgniteCache<String, Ignite1> Ignite1TableCache) {
		SqlQuery<Integer, Ignite1> sqlQuery = new SqlQuery<>(Ignite1.class, sqlString);
		
		QueryCursor<Entry<Integer, Ignite1>> cursor = Ignite1TableCache.query(sqlQuery);
        
        List<Entry<Integer, Ignite1>> cacheResultList = cursor.getAll();
        
        return cacheResultList.get(0).getValue();
	}
	
	
}
