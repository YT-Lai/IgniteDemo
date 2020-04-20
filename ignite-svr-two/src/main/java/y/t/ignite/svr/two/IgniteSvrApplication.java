package y.t.ignite.svr.two;

import java.io.File;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;
import y.t.ignite.svr.one.config.IgniteServerConfigurationFactory;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = { "y.t.ignite.svr.two" })
public class IgniteSvrApplication {
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(IgniteSvrApplication.class, args);

		// 隨著服務啟動也把Ignite啟動
		IgniteConfiguration igniteConf = new IgniteConfiguration();
		igniteConf.setIgniteInstanceName("selfControlCache");
		if (!System.getProperty("os.name").toUpperCase().startsWith("W")) {
			igniteConf.setWorkDirectory(File.separator + "tmp" + File.separator + "ignite" + File.separator + "work");
		}
		Ignition.setClientMode(true);
		
		Ignition.start(igniteConf);

		try {
			// IgniteInstanceName : Ignite1Cache
			IgniteConfiguration dbCacheConf = IgniteServerConfigurationFactory.createConfiguration();
			if (!System.getProperty("os.name").toUpperCase().startsWith("W")) {
				dbCacheConf.setWorkDirectory(File.separator + "tmp" + File.separator + "ignite" + File.separator + "work");
			}
			Ignition.setClientMode(true);
			Ignition.start(dbCacheConf);
		} catch (Exception e) {
			log.error("Ignite DB cache init Failed.", e);
		}
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
	}
}
