package y.t.ignite.svr.two.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "ignite.db2.jdbc")
@Data
public class IgniteDb2ConnProperties {
	String server_name;
	String port_number;
	String database_name;
	String driver_type;
	String username;
	String password;
}
