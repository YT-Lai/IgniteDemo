package y.t.ignite.svr.one.cache.dto;


import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SimpleCacheBodyDto {
	@QuerySqlField
	@JsonProperty("CITY")
	private String city;
	
	@JsonProperty("POSTALCODE")
	private String postalCode;

	public SimpleCacheBodyDto(String city, String postalCode) {
		this.city = city;
		this.postalCode = postalCode;
	}
	
	public SimpleCacheBodyDto() {
	}
	
}
