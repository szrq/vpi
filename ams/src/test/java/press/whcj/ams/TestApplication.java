package press.whcj.ams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import press.whcj.ams.common.ColumnName;
import press.whcj.ams.entity.MongoPage;
import press.whcj.ams.entity.ProjectGroup;
import press.whcj.ams.entity.User;
import press.whcj.ams.entity.dto.UserDto;
import press.whcj.ams.entity.vo.UserVo;
import press.whcj.ams.util.FastUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author xyyxhcj@qq.com
 * @since 2019/12/31
 */
@SpringBootTest(classes = {AmsApplication.class})
@RunWith(SpringRunner.class)
public class TestApplication {
	@Resource
	private MongoTemplate mongoTemplate;

	@Test
	public void testMongodb01() {
		User user = new User();
		user.setUserName("lx");
		user.setLoginName("lx");
		mongoTemplate.save(user);
		System.out.println(user);
	}

	@Test
	public void testMongodb02() {
		List<User> all = mongoTemplate.findAll(User.class);
		System.out.println(all);
	}

	@Test
	public void testMongodb03() {
		User user = new User();
		user.setUserName("xkl");
		user.setLoginName("xkl");
		user.setPassword("123");
		user.setPhone("1881");
		user.setEmail("1881");
		user.setAvatarUrl("1881");
		user.setRemark("1881");
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		mongoTemplate.insertAll(Collections.singleton(user));
	}

	@Test
	public void testMongodb04() {
		Query query = new Query(Criteria.where(ColumnName.ID).is("5dddec8e8f11f93d68b92636"));
		Update update = new Update().set("remark", "Developer");
		mongoTemplate.updateFirst(query, update, User.class);
	}

	@Test
	public void testMongodb05() {
		ProjectGroup projectGroup = new ProjectGroup();
		projectGroup.setName("test2");
		User user = new User();
		user.setId("5de75653b503821a6966e655");
		projectGroup.setUpdate(user);
		mongoTemplate.save(projectGroup);
		//LookupOperation lookupOperation = LookupOperation.newLookup().from("user").localField("userId").foreignField("id").as("userList");
		//总数查询
		//AggregationOperation match = Aggregation.match(Criteria.where("projectId").is("5de77d980daf4a4caa9bbc22"));
		/*Aggregation aggregation = Aggregation.newAggregation(
				match(new Criteria()),
				lookup("user",  "userId","_id", "user_list"));
		AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, Constant.CollectionName.PROJECT_USER, Map.class);
		Iterator<Map> iterator = result.iterator();
		List<Map> tmp = new LinkedList<>();
		while (iterator.hasNext()) {
			Map next = iterator.next();
			System.out.println(next);
			tmp.add(next);
		}
		System.out.println(tmp);*/
/*
		LookupOperation lookupOperation = LookupOperation.newLookup().from("user").localField("id").foreignField("userId").as("userList");
		AggregationOperation match = Aggregation.match(Criteria.where("projectId").is("5de77d980daf4a4caa9bbc22"));
		List<Map> userList = mongoTemplate.aggregate(Aggregation.newAggregation(match, lookupOperation), Constant.CollectionName.PROJECT_USER, Map.class).getMappedResults();
*/
	}

	@Test
	public void testMongodb06() {
		ProjectGroup byId = mongoTemplate.findById("5de8b31d9d62405cdf2fd373", ProjectGroup.class);
		//ProjectGroup byId = mongoTemplate.findById("5de8b184145527334d3514d5", ProjectGroup.class);
		System.out.println(byId);
	}

	@Test
	public void testMongodb07() {
		ProjectGroup parent = mongoTemplate.findById("5de8b51bb89618490dd61c6d", ProjectGroup.class);
		ProjectGroup projectGroup = new ProjectGroup();
		mongoTemplate.save(projectGroup);
	}

	@Test
	public void testMongodb08() {
		ProjectGroup projectGroup = mongoTemplate.findById("5de8b5763806693fd40f1fac", ProjectGroup.class);
		System.out.println(projectGroup);
	}
	@Test
	public void testFastUtils01() {
		UserDto userSource = new UserDto();
		userSource.setUserName("梁萧");
		MongoPage<UserVo> page = new MongoPage<>();
		page.setSize(30);
		userSource.setPage(page);
		UserDto userDest = new UserDto();
		userDest.setPhone("test");
		FastUtils.copyProperties(userSource, userDest);
		System.out.println(userSource.getPage());
		System.out.println(userDest.getPage());
		UserDto copy = FastUtils.deepCopy(userSource, new UserDto());
		System.out.println(userSource);
		System.out.println(copy);
	}
}
