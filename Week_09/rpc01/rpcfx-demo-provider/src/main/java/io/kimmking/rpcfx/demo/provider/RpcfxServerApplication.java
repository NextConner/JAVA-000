package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.demo.provider.server.ProviderServer;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class RpcfxServerApplication {

	private static ConfigurableApplicationContext configurableApplicationContext;

	public static Object getBeanByClass(Class clazz){
	    return configurableApplicationContext.getBean(clazz);
    }

	public static void main(String[] args) throws Exception {

		// xxx "io.kimmking.rpcfx.demo.api.UserService"

		ServiceProviderDesc desc = new ServiceProviderDesc();
		desc.setHost(InetAddress.getLocalHost().getHostAddress());
		desc.setPort(8080);
		desc.setServiceClass("io.kimmking.rpcfx.demo.api.UserService");

        new Thread(()->ProviderServer.startServer()).start();
		// Curator.
		configurableApplicationContext =  SpringApplication.run(RpcfxServerApplication.class, args);
	}

	@Autowired
	RpcfxInvoker invoker;

	@PostMapping("/")
	public RpcfxResponse invoke(@RequestBody RpcfxRequest request) throws InstantiationException, IllegalAccessException {
		return invoker.invoke(request);
	}

	@RequestMapping("/test")
	public String receiveRequest(String name){
		return "success:"+name;
	}

	@Bean
	public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver){
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public RpcfxResolver createResolver(){
		DemoResolver resolver = new DemoResolver();
		resolver.setApplicationContext(configurableApplicationContext);
		return resolver;
	}

	// 能否去掉name
	//

	// annotation


	@Bean(name = "io.kimmking.rpcfx.demo.api.UserService")
	public UserService createUserService(){
		return new UserServiceImpl();
	}

	@Bean(name = "io.kimmking.rpcfx.demo.api.OrderService")
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
