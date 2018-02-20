package test;

import com.king.config.RootConfig;
import com.king.repository.EmployeeRepository;
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

    @Test
    public void delete() {
        int[] deleteID={4};
        for (int i:deleteID){
            employeeRepository.deleteEmployee(i);
        }
    }
}