import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.test.Application;
import com.wang.test.domain.User;
import com.wang.test.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 15:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CRUDTest {
        @Autowired
        private UserMapper userMapper;

        @Test
        public void testSelect() {

            //插入
//            userMapper.insert(new User(){{setId(10l);setName("test");}});
//            System.out.println(("----- selectAll method test ------"));
//            List<User> userList = userMapper.selectList(null);
//            //删除
//          User user=  userMapper.selectById(1);
//           int count= userMapper.deleteById(9l);

            //分页
            Page<User> page = new Page<>(1, 2);
            IPage<User> userIPage = userMapper.selectPage(page, new QueryWrapper<User>()
                    .eq("status", 1).eq("name", "2"));
            Assert.assertSame(page, userIPage);
            System.out.println("总条数 ------> " + userIPage.getTotal());
            System.out.println("当前页数 ------> " + userIPage.getCurrent());
            System.out.println("当前每页显示数 ------> " + userIPage.getSize());
            System.out.println("----- baseMapper 自带分页 ------");


        }

}
