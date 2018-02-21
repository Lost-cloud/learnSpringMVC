package test;

import com.king.config.RootConfig;
import com.king.config.WebConfig;
import com.king.domain.Role;
import com.king.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes ={WebConfig.class,RootConfig.class})
public class ParamsControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        this.mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testJsonRequest() throws Exception {
        mockMvc.perform(get("/params/json/findEmployee"))
                .andDo(print())
                .andExpect(view().name("postParams"));
    }

    @Test
    public void testJsonResponse() throws Exception {
        String requestBody="{\"roleName\":\"king\"}";
        mockMvc.perform(post("/params/json/listEmployee")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(jsonPath("$.roleName").value("king"))
//                .andExpect(view().name("employeeList"));
                .andExpect(view().name("params/json/listEmployee"));
    }
}