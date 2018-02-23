package test;

import com.king.config.web.RootConfig;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootConfig.class)
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
        int[] deleteID={5,6,7,8,9,10,11,13,14,15,16,17,18,19,22,23,24};
        for (int i:deleteID){
            employeeRepository.deleteEmployee(i);
        }
    }
}