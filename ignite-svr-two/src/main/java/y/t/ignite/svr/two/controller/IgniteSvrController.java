package y.t.ignite.svr.two.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import y.t.ignite.svr.one.cache.dto.SimpleCacheBodyDto;
import y.t.ignite.svr.one.entity.dbeb.Ignite1;
import y.t.ignite.svr.two.service.IgniteClientService;

@Api(tags = "IgniteDemo")
@RestController
@RequestMapping(path = "/ignite")
public class IgniteSvrController {

	@Autowired
	IgniteClientService service;

	@Autowired
	ObjectMapper objectMapper;

	@PostMapping(path = "/trigger")
	public String getCode(@Valid @RequestBody String req) {

		Ignite1 result = service.getParticularIgnite1Cache("0001");

		String resp = "";
		try {
			resp = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			// FIXME do nothing.
		}

		return resp;
	}

	@PostMapping(path = "/cache/clean")
	public String cleanCache(@RequestParam String igniteInstanceName, @RequestParam String cacheName) {

		service.cleanCache(igniteInstanceName, cacheName);

		return "finished";

	}

	@PostMapping(path = "/cache/put")
	public String putCache(@RequestBody List<SimpleCacheBodyDto> cacheBodyList) {

		service.putValueIntoCache(cacheBodyList);

		return "finished";
	}

	@PostMapping(path = "/cache/getBySql")
	public @ResponseBody SimpleCacheBodyDto getCacheBySql(@RequestParam String cityName) {

		SimpleCacheBodyDto rtnDto = service.getObjectFromCacheBySql(cityName);

		return rtnDto;
	}

	@PostMapping(path = "/cache/getByKey")
	public @ResponseBody SimpleCacheBodyDto putCacheByKey(@RequestParam String cityName) {

		SimpleCacheBodyDto rtnDto = service.getObjectFromCacheByKey(cityName);

		return rtnDto;
	}
}
