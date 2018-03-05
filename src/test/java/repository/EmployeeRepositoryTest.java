package repository;

import com.king.config.RootConfig;
import com.king.web.WebConfig;
import com.king.domain.Employee;
import com.king.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class})
public class EmployeeRepositoryTest {

    @Autowired
    @Qualifier("employeeRepository")
    private EmployeeRepository employeeRepository;

    private Logger logger;

    @Before
    public void setUp() {
        logger= LogManager.getLogger();
    }

    @Test
    public void insertTest() {
        Employee employee = employeeRepository.getEmployee(1);
        logger.info("the id is " +employeeRepository.insertEmployee(employee));
        logger.info("the id is " +employee.getId());
    }

    @Test
    public void delete() {
        for (int i=95;i<190;i++){
            employeeRepository.deleteEmployee(i);
        }
    }

    @Test
    public void test() {
        String s="5,6,7,8,9,10,11,13,14,15,16,17,18,19,22,23,24";
        String ss="jack-1-1994/10/04-18617164782-lost0000@outlook.com-广东深圳-by converter" +
        ",jack-1-1994/10/04-18617164782-lost0000@outlook.com-广东深圳-by converter";
        String[] fields = StringUtils.commaDelimitedListToStringArray(ss);
        for (String field : fields) {
            LogManager.getLogger().info(field);
        }
    }
}