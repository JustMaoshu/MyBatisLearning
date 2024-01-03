import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sumail.mybatis.mapper.EmpMapper;
import com.sumail.mybatis.pojo.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestPageInterceptor {

    /**
     * limit index,pageSize
     * index:当前页的起始索引（第一页从0开始）
     * pageSize:每页显示的条数（一般固定）
     * pageNum:当前页的页码
     * index=(pageNum-1)*pageSize
     */
    @Test
    public void test() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession session = sessionFactory.openSession(true);
        EmpMapper mapper = session.getMapper(EmpMapper.class);

        //查询前开启分页功能
        Page<Object> page = PageHelper.startPage(2, 3);
        List<Emp> emps = mapper.selectByExample(null);
        emps.forEach(System.out::println);
        //startPage方法用于开启分页，要想获得分页的详细信息，有两种做法
        //1、通过startPage的返回值Page<Object> page
        System.out.println(page);
        //2、通过PageInfo，其中navigatePages是导航分页的个数，一般是奇数
        PageInfo<Emp> pageInfo = new PageInfo<>(emps, 5);
        System.out.println(pageInfo);
        //较为推荐做法2，因为PageInfo中包含了1的所有结果，并且在1的基础上多了导航分页的信息
    }
}
