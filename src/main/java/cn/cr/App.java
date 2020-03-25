package cn.cr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@RestController
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/")
    public String hello(){
        return "hello world!";
    }
}
