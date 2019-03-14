package gradleTest.zookeepertest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.songkai.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class rabbitMqTest {

	@Autowired
	protected RabbitTemplate rabbitTemplate;
	
	@Test
	public void sssss(){
		Message message = new Message("hello+11231231231231".getBytes(),new MessageProperties());
		rabbitTemplate.send("PMS_MQ_EXCHANGE", "MQ_TEST_PMS1", message);
	}
	
}
