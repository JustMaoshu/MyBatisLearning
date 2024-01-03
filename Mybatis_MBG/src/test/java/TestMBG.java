import com.sumail.mybatis.mapper.EmpMapper;
import com.sumail.mybatis.pojo.Emp;
import com.sumail.mybatis.pojo.EmpExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMBG {

    /**
     * 逆向工程生成的查询语句是QBC风格的：QueryByCriteria，使用Criteria查询，比较面向对象，不自己写SQL语句
     */
    @Test
    public void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession session = sessionFactory.openSession(true);
        EmpMapper mapper = session.getMapper(EmpMapper.class);
        //1、查询所有数据
        List<Emp> emps1 = mapper.selectByExample(null);
        emps1.forEach(System.out::println);
        //2、根据条件查询，例如查询性别为男的、年龄超过30岁的员工，或者年龄超过40岁的员工
        EmpExample example = new EmpExample();
        example.createCriteria().andSexEqualTo("男").andAgeGreaterThan(30);
        example.or().andAgeGreaterThan(40);
        List<Emp> emps2 = mapper.selectByExample(example);
        emps2.forEach(System.out::println);
        //3、根据主键修改
        //updateByPrimaryKey(new Emp(...))方法，如果new的Emp中有null，则会将原值修改为null
        //而updateByPrimaryKeySelective(new Emp(...))方法，new的Emp中有null，不会将原来的值改为null
        //inert方法也一样，如果选择性添加，不会将null添加到数据表中，当然需要先设置默认值否则没有区别
    }

}
