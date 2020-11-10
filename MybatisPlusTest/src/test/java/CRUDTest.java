import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.test.Application;
import com.wang.test.domain.User;
import com.wang.test.enums.EnumA;
import com.wang.test.mapper.UserMapper;
import com.wang.test.service.IOmsUserService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 15:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CRUDTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IOmsUserService iOmsUserService;

    /**
     * 自定义mapper
     */
    @Test
    public void customerSelect() {
        List<User> users = userMapper.selectByName("zhangsan");
//        QueryWrapper<User> queryWrapper=new QueryWrapper();
//        queryWrapper.setEntity(new User(){{setUserName("zhangsan");}});
//        List<User> users =userMapper.selectByMyWrapper(queryWrapper);
//        User user = userMapper.selectById(1);
        userMapper.updateAll();
    }


    @Test
    public void testMapper() {
        //插入
//        userMapper.insert(new User() {{
//            setId(5L);
//            setName("test");
//            setPassword("test");
//            setStatus(EnumA.ENABLE);
//        }});
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
        //删除
        User user = userMapper.selectById(4);
        int count = userMapper.deleteById(4L);
//
//            //分页
        Page<User> page = new Page<>(1, 2);
        IPage<User> userIPage = userMapper.selectPage(page, new QueryWrapper<User>()
                .eq("status", 1).eq("name", "test"));
        Assert.assertSame(page, userIPage);
        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());
        System.out.println("----- baseMapper 自带分页 ------");

    }

    /**
     * #Save
     */
    @Test
    public void testSave() {
        // count
        int count = iOmsUserService.count();
        System.out.println(count);
        // Save
        User user = new User();
        user.setUserName("tss");
        user.setUserPassword("sss");
        user.setUserStatus(EnumA.DISENABLE);
        user.setVersion(0);
        user.setCreateTime(LocalDateTime.now());
        // 插入一条记录（选择字段，策略插入）
        iOmsUserService.save(user);
        // 插入（批量）
//        List<User> users = new ArrayList<>(2);
//        User user1 = new User();
//        user1.setId(13L);
//        user1.setName("ts");
//        user1.setPassword("sss");
//        user1.setStatus(EnumA.ENABLE);
//        user1.setCreateTime(LocalDateTime.now());
//        User user2 = new User();
//        user2.setId(14L);
//        user2.setName("ts");
//        user2.setPassword("sss");
//        user2.setStatus(EnumA.ENABLE);
//        user2.setCreateTime(LocalDateTime.now());
//        users.add(user1);
//        users.add(user2);
//        iOmsUserService.saveBatch(users);
        // 插入（批量） 限制批次数量  按指定批次生成insert
//        List<User> users = new ArrayList<>(3);
//        User user1 = new User();
//        user1.setId(15L);
//        user1.setName("ts");
//        user1.setPassword("sss");
//        user1.setStatus(EnumA.ENABLE);
//        User user2 = new User();
//        user2.setId(16L);
//        user2.setName("ts");
//        user2.setPassword("sss");
//        user2.setStatus(EnumA.ENABLE);
//        User user3 = new User();
//        user3.setId(17L);
//        user3.setName("ts");
//        user3.setPassword("sss");
//        user3.setStatus(EnumA.ENABLE);
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        iOmsUserService.saveBatch(users,2);
    }

    /**
     * SaveOrUpdate
     */
    @Test
    public void testSaveOrUpdate() {
//// TableId 注解存在更新记录，否插入一条记录
//        boolean saveOrUpdate (T entity);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        User user1 = new User();
        user1.setId(151L);
        user1.setUserName("1");
        user1.setUserPassword("ssss");
        user1.setUserStatus(EnumA.ENABLE);
        updateWrapper.setEntity(user1);
        iOmsUserService.saveOrUpdate(user1);
//// 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
//        boolean saveOrUpdate (T entity, Wrapper < T > updateWrapper);
//// 批量修改插入
//        boolean saveOrUpdateBatch (Collection < T > entityList);
//// 批量修改插入
//        boolean saveOrUpdateBatch (Collection < T > entityList,int batchSize);
    }

    @Test
    public void testRemove() {
//        // 根据 entity 条件，删除记录
//        boolean remove(Wrapper<T> queryWrapper);
        QueryWrapper<User> updateWrapper = new QueryWrapper<>();
        // 直接设置SQL 语句
//        updateWrapper.setSql("name=1");
        // 设置 set
//        updateWrapper.set("name",1);
        User user1 = new User();
        user1.setId(15L);
        user1.setUserName("1");
        user1.setUserPassword("sss");
        user1.setUserStatus(EnumA.ENABLE);
        updateWrapper.setEntity(user1);
//        iOmsUserService.remove(updateWrapper);
//// 根据 ID 删除
        iOmsUserService.removeById(18);
//// 根据 columnMap 条件，删除记录
//        boolean removeByMap(Map<String, Object> columnMap);
//// 删除（根据ID 批量删除）
//        boolean removeByIds(Collection<? extends Serializable> idList);
    }

    @Test
    public void testUpdate() {
//        // 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 直接设置SQL 语句
//        updateWrapper.setSql("name=1");
        // 设置 set
//        updateWrapper.set("name",1);
//        User user1 = new User();
//        user1.setId(15L);
//        user1.setUserName("1");
//        user1.setUserPassword("sss");
//        user1.setUserStatus(EnumA.ENABLE);
//        updateWrapper.setEntity(user1);
//        iOmsUserService.update(updateWrapper);

//// 根据 whereEntity 条件，更新记录
//        boolean update(T entity, Wrapper<T> updateWrapper);
//        User update = new User();
//        update.setId(1324668024457437185L);
//        update.setUserName("ts1");
//        update.setVersion(0);
////        iOmsUserService.update(update,updateWrapper);
////// 根据 ID 选择修改
//        iOmsUserService.updateById(update);
//// 根据ID 批量更新
//        boolean updateBatchById(Collection<T> entityList);
//// 根据ID 批量更新
//        boolean updateBatchById(Collection<T> entityList, int batchSize);

    }

    /**
     * get
     */
    @Test
    public void testGet() {
        User user1 = iOmsUserService.getById(1);
//// 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.setEntity(new User() {{
//            setId(111L);
            setUserName("ts");
        }});
//// 根据 Wrapper，查询一条记录 如果超过1行 true抛异常  false 返回第一个
        user1 = iOmsUserService.getOne(wrapper, false);
//// 根据 Wrapper，查询一条记录
        Map<String, Object> userMap = iOmsUserService.getMap(wrapper);
    }

    /**
     * count
     */
    @Test
    public void testCount() {
        // 查询总记录数
        int count = iOmsUserService.count();
        // 根据 Wrapper 条件，查询总记录数
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.setEntity(new User() {{
            setUserStatus(EnumA.ENABLE);
        }});
        int count1 = iOmsUserService.count(wrapper);
    }

    /**
     * list
     */
    @Test
    public void testList() {
//        List<T> list(Wrapper<T> queryWrapper);
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.setEntity(new User() {{
            setUserStatus(EnumA.ENABLE);
        }});
        List<User> users = iOmsUserService.list(wrapper);
        // 查询（根据ID 批量查询）
        users = iOmsUserService.listByIds(new ArrayList<Integer>() {{
            add(1);
            add(2);
        }});

        // 查询（根据 columnMap 条件）
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        users = iOmsUserService.listByMap(map);
// 查询所有列表
        users = iOmsUserService.list();
        // 查询列表
//        List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
// 查询全部记录
//        List<Object> listObjs();
// 查询全部记录
//<V> List<V> listObjs(Function<? super Object, V> mapper);
// 根据 Wrapper 条件，查询全部记录
//        List<Object> listObjs(Wrapper<T> queryWrapper);
// 根据 Wrapper 条件，查询全部记录
//<V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);
    }

    @Test
    public void testPage() {
        // 无条件分页查询
        Page<User> page = new Page<>(1, 2);
        IPage<User> page1 = iOmsUserService.page(page);
// 条件分页查询
//        IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.setEntity(new User() {{
            setUserStatus(EnumA.ENABLE);
        }});
        page1 = iOmsUserService.page(page, wrapper);
// 无条件分页查询
//        IPage<Map<String, Object>> pageMaps(IPage<T> page);
// 条件分页查询
//        IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);
    }


    @Test
    public void testOther() {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.setEntity(new User() {{
            setUserStatus(EnumA.ENABLE);
        }});
        // select 设置查询字段
//        wrapper.select("id,name");
        // 注意wapper 直接绑定的参数 是在()中
        // SELECT id,name,password,status,create_time FROM OMS_USER WHERE status=? AND (status BETWEEN ? AND ?)
        // between
//        wrapper.between("status",0,1);
        // notBetween
//        wrapper.notBetween("status",0,1);
        // eq =
//        wrapper.eq("name","zhangsan");
        // ne !=
//        wrapper.ne("name","zhangsan");
        // gt >
//        wrapper.gt("status",0);
        // ge >=
//        wrapper.ge("status",0);
        // lt <
//        wrapper.lt("status",1);
        // le <=
//        wrapper.le("status",1);
        // like
//        wrapper.like("name","1");
        // notLike
//        wrapper.notLike("name","1");
        // likeLeft
//        wrapper.likeLeft("name","1");
        // likeRight
//        wrapper.likeRight("name","1");
        // isNull
//        wrapper.isNull("name");
        // isNotNull
//        wrapper.isNotNull("name");
        // in
//        wrapper.in("name","1,2");
        // notIn
        // inSql
//        wrapper.inSql("name","select 1 from OMS_USER");
        // notInSql
        // groupBy
        wrapper.groupBy("id");
        //orderByAsc
//        wrapper.orderByAsc("id");
        //orderByDesc
        //orderBy
        wrapper.orderBy(true, true, "name");
        //having
        wrapper.having("id>1", "1");
        //or
        //and
        //nested
        //apply
        //last
        //exists
        //notExists
        List<User> users = iOmsUserService.list(wrapper);
    }


    /**
     * 自动填充默认值
     */
    @Test
    public void autoInitData(){
        User user = new User();
        user.setUserName("tss1");
        user.setUserPassword("sss");
        user.setUserStatus(EnumA.DISENABLE);
        // 插入一条记录（选择字段，策略插入）
        iOmsUserService.save(user);
    }

}