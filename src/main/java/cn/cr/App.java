package cn.cr;

import cn.cr.dao.UserDOMapper;
import cn.cr.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"cn.cr"})
@RestController
@MapperScan("cn.cr.dao")
public class App 
{


    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String hello(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO == null) {
            return "hello world!";
        }else {
            return userDO.getName();
        }
    }
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
