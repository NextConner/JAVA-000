package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.client.Rpcfx;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.demo.consumer.service.ServiceCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.util.Random;

@SpringBootApplication
public class RpcfxClientApplication implements ApplicationRunner{

	// 二方库
	// 三方库 lib
	// nexus, userserivce -> userdao -> user
	//

	@Autowired
	private ServiceCreate serviceCreate;

	public static void main(String[] args) throws NoSuchMethodException {

		// UserService service = new xxx();
		// service.findById



//		OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
//		Order order = orderService.findOrderById(1992129);
//		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

		//
//		UserService userService2 = Rpcfx.createFromRegistry(UserService.class, "localhost:2181", new TagRouter(), new RandomLoadBalancer(), new CuicuiFilter());

		SpringApplication.run(RpcfxClientApplication.class, args);
		UserService userService = Rpcfx.create(UserService.class, "localhost:8080/test?name=zoujintao",null);
//		UserService userService = serviceCreate.create(UserService.class,"http://localhost:8080/",null);
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}
}



