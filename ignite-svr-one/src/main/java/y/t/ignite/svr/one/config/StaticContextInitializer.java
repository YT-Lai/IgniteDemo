package y.t.ignite.svr.one.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StaticContextInitializer {
	
	@Autowired
	private IgniteDb2ConnProperties igniteDb2Properties;
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void init() {
		IgniteServerConfigurationFactory.setDb2ConnProperties(igniteDb2Properties);
	}
}
