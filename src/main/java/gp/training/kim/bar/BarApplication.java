package gp.training.kim.bar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BarApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BarApplication.class, args);
    }

}
