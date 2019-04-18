package br.com.ebexs.router.api.config;
import br.com.ebexs.router.api.service.LoadGraphService;
import br.com.ebexs.router.api.service.impl.LoadGraphServiceImpl;
import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;
import br.com.ebexs.routerfinderservice.service.impl.CalculateCheapestWayImpl;
import br.com.ebexs.routerfinderservice.service.impl.ReadCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan({"br.com.ebexs.router.api.controller",
"br.com.ebexs.router.api.service.impl"})
public class StartBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartBootApplication.class, args);
    }

    @Bean
    public ReadRouteRegistry readRouteRegistry() {
        return  new ReadCSV();
    }

    @Bean
    @Autowired
    public LoadGraphService loadGraph(final Environment environment, ReadRouteRegistry readCSV) throws ResourceRouterUnavailableException {
        return new LoadGraphServiceImpl(environment, readCSV);
    }

    @Bean
    public CalculateCheapestWay calculateCheapestWay() {
        return new CalculateCheapestWayImpl();
    }
}
