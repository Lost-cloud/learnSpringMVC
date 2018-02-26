package web;

import com.king.config.RootConfig;
import com.king.config.web.WebConfig;
import com.king.domain.Employee;
import com.king.domain.builder.EmployeeBuilder;
import com.king.domain.builder.EmployeeBuilderImpl;
import com.king.enums.SexEnum;
import com.king.service.EmployeeService;
import com.king.util.DateUtil;
import com.king.web.controller.ConvertController;
import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", classes = RootConfig.class),
        @ContextConfiguration(name = "child", classes = WebConfig.class)
})
public class ConvertControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private EmployeeService employeeService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void insertEmployees() throws Exception {
//        employeeService = mock(EmployeeService.class);
//        EmployeeBuilder employeeBuilder = new EmployeeBuilderImpl(new Employee());
//        Employee unsaved = employeeBuilder.buildRealName("jack")
//                .buildBirthday(DateUtil.buildDate(1994, 10, 4))
//                .buildMobile("18617164782")
//                .buildEmail("lost0000@outlook.com")
//                .buildSex(SexEnum.MALE)
//                .buildNote("by converter")
//                .buildPosition("广东深圳")
//                .getEmployee();
//        when(employeeService.insertEmployee(unsaved)).thenReturn(146);
        MvcResult result = mockMvc.perform(post("/convert/insertEmployee")
                .param("employeeList", "jack-1-1994/10/04-18617164782-lost0000@outlook.com-广东深圳-byconverter" +
                        ",jack-1-1994/10/04-18617164782-lost0000@outlook.com-广东深圳-byconverter"))
                .andDo(print())
                .andExpect(view().name("testConvert")).andReturn();
    }
}