package com.king.config.data;

import com.king.config.RootConfig;
import com.king.domain.Employee;
import com.king.domain.builder.EmployeeBuilder;
import com.king.domain.builder.EmployeeBuilderImpl;
import com.king.enums.SexEnum;
import com.king.repository.EmployeeRepository;
import com.king.service.EmployeeService;
import com.king.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class})
public class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTemplate<String, Object> objRedisTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository repository;

    private Logger logger;

    @Before
    public void setUp() {
        logger = LogManager.getLogger();
    }

    @Test
    public void redisTemplate() {
        Employee unsaved = getEmployee();
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            @SuppressWarnings("unchecked")
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForValue().set("employee", unsaved);
                return redisOperations.opsForValue().get("employee");
            }
        };
        Employee saved = (Employee) objRedisTemplate.execute(sessionCallback);
        System.out.println(saved);
    }

    private Employee getEmployee() {
        EmployeeBuilder employeeBuilder = new EmployeeBuilderImpl(new Employee());
        return employeeBuilder.buildRealName("jack")
                .buildBirthday(DateUtil.buildDate(1994, 10, 4))
                .buildMobile("18617164782")
                .buildEmail("lost0000@outlook.com")
                .buildSex(SexEnum.MALE)
                .buildNote("by Redis")
                .buildPosition("广东深圳")
                .getEmployee();
    }

    @Test
    public void testString() {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set("key1", "value1");
        redisTemplate.opsForValue().set("key2", "value2");
        String value1 = redisTemplate.opsForValue().get("key1");
        String value2 = redisTemplate.opsForValue().get("key2");
        logger.info("key1 : " + value1 + "\nkey2 : " + value2);
        logger.info(redisTemplate.opsForValue().size("key2"));
        logger.info("oldValue :" + redisTemplate.opsForValue().getAndSet("key2", "new_value2")
                + "\nnewValue : " + redisTemplate.opsForValue().get("key2"));
        logger.info(redisTemplate.opsForValue().get("key2", 0, 3));
    }

    @Test
    public void testNumber() {
        redisTemplate.opsForValue().set("i", "9");
        printCurrValue("i");
        redisTemplate.opsForValue().increment("i", 10.1);
        printCurrValue("i");
        //原始的不能减浮点数
//        redisTemplate.getConnectionFactory().getConnection().decr(redisTemplate.getStringSerializer().serialize("i"));
        redisTemplate.opsForValue().increment("i", -2.2);
        printCurrValue("i");
    }

    @Test
    public void testHash() {
        String key = "hash";
        Map<String, String> map = new HashMap<>();
        map.put("f1", "val1");
        map.put("f2", "val2");

        redisTemplate.opsForHash().putAll(key, map);

        redisTemplate.opsForHash().put(key, "f3", "6");

        logger.info(redisTemplate.opsForHash().hasKey(key, "f3"));
        Map keyValMap = redisTemplate.opsForHash().entries(key);
        logger.info(keyValMap);
        redisTemplate.opsForHash().increment(key, "f3", -2.1);
        logger.info(redisTemplate.opsForHash().get(key, "f3"));

        List valueList = redisTemplate.opsForHash().values(key);
        logger.info(valueList);

        Set keyList = redisTemplate.opsForHash().keys(key);
        logger.info(keyList);

        List<Object> fieldList = new ArrayList<>();
        fieldList.add("f1");
        fieldList.add("f3");
        List valueList2 = redisTemplate.opsForHash().multiGet(key, fieldList);
        logger.info(valueList2);

    }

    @Test
    public void testLinkedList() throws UnsupportedEncodingException {
        List<String> nodeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nodeList.add("node" + i);
        }
        redisTemplate.delete("list");
        redisTemplate.opsForList().leftPushAll("list", nodeList);
        logger.info(redisTemplate.opsForList().range("list", 0, 4));
        redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8")
                , RedisListCommands.Position.AFTER, "node2".getBytes("utf-8"), "after_node".getBytes("utf-8"));
        logger.info(redisTemplate.opsForList().range("list", 0, 4));

        logger.info(redisTemplate.opsForList().leftPop("list"), 1, TimeUnit.SECONDS);


    }

    @Test
    public void testSet() {
        redisTemplate.opsForSet().add("set1", "v1", "v8", "v3", "v4", "v5", "v6");
        redisTemplate.opsForSet().add("set2", "v0", "v2", "v4", "v7", "v5", "v6");
        logger.info(redisTemplate.opsForSet().size("set1"));
        logger.info(redisTemplate.opsForSet().difference("set1", "set2"));
        logger.info(redisTemplate.opsForSet().intersect("set1", "set2"));
        logger.info(redisTemplate.opsForSet().union("set1", "set2"));
        logger.info(redisTemplate.opsForSet().members("set1"));
        logger.info(redisTemplate.opsForSet().pop("set1"));
        logger.info(redisTemplate.opsForSet().members("set1"));
        logger.info(redisTemplate.opsForSet().randomMember("set1"));

    }

    @Test
    public void testZSet() {
        Set<ZSetOperations.TypedTuple<String>> set1 = new HashSet<>();
        Set<ZSetOperations.TypedTuple<String>> set2 = new HashSet<>();
        int j = 9;
        for (int i = 1; i <= 9; i++) {
            j--;
            double score1 = (double) i;
            String value1 = "x" + i;
            double score2 = j;
            String value2 = j % 2 == 1 ? "y" + j : "x" + j;
            ZSetOperations.TypedTuple<String> typedTuple1 = new DefaultTypedTuple<>(value1, score1);
            set1.add(typedTuple1);
            ZSetOperations.TypedTuple<String> typedTuple2 = new DefaultTypedTuple<>(value2, score2);
            set2.add(typedTuple2);
        }

        redisTemplate.opsForZSet().add("zSet1", set1);
        redisTemplate.opsForZSet().add("zSet2", set2);

        logger.info(redisTemplate.opsForZSet().zCard("zSet1"));
        logger.info(redisTemplate.opsForZSet().count("zSet1", 3, 6));
        logger.info(redisTemplate.opsForZSet().range("zSet1", 0, 8));
        logger.info(redisTemplate.opsForZSet().range("zSet2", 0, 8));

        printTypedTuple(redisTemplate.opsForZSet().rangeWithScores("zSet2", 0, 8));

        logger.info(redisTemplate.opsForZSet().intersectAndStore("zSet1", "zSet2", "inter_zset"));
        logger.info(redisTemplate.opsForZSet().range("inter_zset", 0, 4));

        RedisZSetCommands.Range range = RedisZSetCommands.Range.range();
        range.lt("x8").gt("x1");
        logger.info(redisTemplate.opsForZSet().rangeByLex("zSet1", range));

        RedisZSetCommands.Limit limit = RedisZSetCommands.Limit.limit();
        limit.offset(3).count(2);
        logger.info(redisTemplate.opsForZSet().rangeByLex("zSet1", range, limit));

        redisTemplate.opsForZSet().removeRange("zSet2", 3, 9);
        logger.info(redisTemplate.opsForZSet().range("zSet2", 0, 9));

        logger.info(redisTemplate.opsForZSet().reverseRange("zSet2", 0, 9));

    }

    @Test
    public void testHyperLogLog() {

    }

    @Test
    public void testTransaction() {
        SessionCallback callback = new SessionCallback() {
            @Override
            @SuppressWarnings("unchecked")
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("key1", "value1");
                logger.info("进入队列未被执行 " + operations.opsForValue().get("key1"));
                operations.exec();
                return operations.opsForValue().get("key1");
            }
        };
        logger.info(redisTemplate.execute(callback));
    }

    @Test
    public void testPipeLine() {
        long usualTime = usePipeLine();
        long pipeLineTime = usePlByRedisTemplate();
        logger.info("usualTime : " + usualTime + "\npipeLineTime : " + pipeLineTime);
    }

    private long usePipeLine() {
        long start = System.currentTimeMillis();
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 100000; i++) {
            int j = i + 1;
            pipeline.set("pipeline_key_" + j, "pipeline_value_" + j);
            pipeline.get("pipeline_key_" + j);
        }
        pipeline.syncAndReturnAll();
        return System.currentTimeMillis() - start;
    }

    private long usePlByRedisTemplate() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            redisTemplate.executePipelined(new MySessionCallback());
        }
        return System.currentTimeMillis() - start;
    }

    private long usualMethod() {
        long start = System.currentTimeMillis();
        redisTemplate.execute(new MySessionCallback());
        return System.currentTimeMillis() - start;
    }

    private class MySessionCallback implements SessionCallback<String> {
        @Override
        public String execute(RedisOperations operations) throws DataAccessException {
            for (int i = 0; i < 1000; i++) {
                int j = i + 1;
                operations.opsForValue().set("pipeline_key_" + j, "pipeline_value_" + j);
                operations.opsForValue().get("pipeline_key_" + j);
            }
            return null;
        }
    }


    @Test
    public void testLuaFile() throws IOException {
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        String helloJava = (String) jedis.eval("return 'hello java' ");
        logger.info(helloJava);
        jedis.eval("redis.call('set',KEYS[1],ARGV[1])", 1, "lua-key", "lua-value");
        String luaValue = jedis.get("lua-key");
        logger.info(luaValue);
        String sha1 = jedis.scriptLoad("redis.call('set',KEYS[1],ARGV[1])");
        jedis.evalsha(sha1, 1, "sha1-key", "sha1-value");
        logger.info(jedis.get("sha1-key"));

        DefaultRedisScript<Employee> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText("redis.call('set',KEYS[1],ARGV[1]) return redis.call('get',KEYS[1])");
        //定义key
        List<String> keyList = new ArrayList<>();
        keyList.add("emp");
        Employee employee = getEmployee();
        String emp_sha1 = redisScript.getSha1();
        logger.info(emp_sha1);

        redisScript.setResultType(Employee.class);
        RedisSerializer redisSerializer = new JdkSerializationRedisSerializer();
        Employee redis_emp = redisTemplate.execute(redisScript, redisSerializer, redisSerializer, keyList, employee);
        logger.info(redis_emp);

        //执行test.lua
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("test.lua"));
        String sha2 = script.getSha1();

        //流方式读取文件
        InputStream inputStream = new FileInputStream("F:\\WorkSpace\\LearnSpringMVC\\src\\main\\resources\\test.lua");
        byte[] by = new byte[inputStream.available()];
        //读入字节数组中
        inputStream.read(by);
        String script1 = new String(by);
        String sha3 = jedis.scriptLoad(script1);

        Object object = jedis.evalsha(sha2, 2, "key1", "key2", "2", "4");
        logger.info(object);
        jedis.close();
    }

    @Test
    public void testRedisCache() {
        Employee employee = getEmployee();
        long id=employeeService.insertEmployee(employee).getId();
        Employee redis_emp = employeeService.getEmployee(id);
        redis_emp.setNote("redis_emp_update");
        employeeService.updateEmployee(redis_emp);
    }

    @Test
    public void testUseCache() {
        logger.info("不使用缓存"+ notUseCache());
        logger.info("使用缓存"+ useCache());
    }

    private long useCache() {
        long start = System.currentTimeMillis();
        List<Long> idList=new ArrayList<>();
        for (int i=0;i<100;i++) {
            Employee employee = getEmployee();
            employeeService.insertEmployee(employee);
            idList.add(employee.getId());
        }
        idList.forEach(it->{
            employeeService.getEmployee(it);
        });
        return System.currentTimeMillis() - start;
    }

    private long notUseCache() {
        long start = System.currentTimeMillis();
        List<Long> idList=new ArrayList<>();
        for (int i=0;i<100;i++) {
            Employee employee = getEmployee();
            repository.insertEmployee(employee);
            idList.add(employee.getId());
        }
        idList.forEach(it->{
            repository.getEmployee(it);
        });
        return System.currentTimeMillis() - start;
    }

    private void printTypedTuple(Set<ZSetOperations.TypedTuple<String>> zSet) {
        for (ZSetOperations.TypedTuple<String> stringTypedTuple : zSet) {
            logger.error(stringTypedTuple.getValue() + " " + stringTypedTuple.getScore());
        }
    }

    private void printCurrValue(String key) {
        logger.info(redisTemplate.opsForValue().get(key));
    }



}