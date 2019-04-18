package br.com.ebexs.router.api.controller;

import br.com.ebexs.router.api.config.StartBootApplication;
import br.com.ebexs.router.api.service.FindCheapestRouteService;
import br.com.ebexs.router.api.service.InsertRouteService;
import br.com.ebexs.router.api.service.LoadGraphService;
import br.com.ebexs.router.api.service.impl.LoadGraphServiceImpl;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;
import br.com.ebexs.routerfinderservice.service.impl.CalculateCheapestWayImpl;
import br.com.ebexs.routerfinderservice.service.impl.ReadCSV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes= AppConfig.class)
public class RouterControllerTest {

    @LocalServerPort
    private int port;

    @Mock
    FindCheapestRouteService findCheapestRouteService;

    @Mock
    InsertRouteService insertRouteService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void findRoute() {

        ResponseEntity<String> responseEntity = ResponseEntity.ok("teste");

       ResponseEntity<String> r = restTemplate.getForEntity(
               "/cheapeastroutes/GRU/BRC",
               String.class,
               responseEntity);

       Assert.assertEquals(r.getStatusCode().value(), 200);
    }

    @Test
    public void insertRoute() {

        ResponseEntity<String> responseEntity = ResponseEntity.status(200).build();

        ResponseEntity<String> r = restTemplate.postForEntity("/routes",
                 new RouteModel(),
                String.class,
                responseEntity);

        Assert.assertEquals(r.getStatusCode().value(), 200);
    }

}

@SpringBootApplication
class AppConfig {

    public static void main(String[] args) {
          SpringApplication.run(AppConfig.class, args);
    }

    @Bean
    public ReadRouteRegistry readRouteRegistry() {
        return  Mockito.mock(ReadRouteRegistry.class);
    }

    @Bean
    @Autowired
    public LoadGraphService loadGraph(final Environment environment, ReadRouteRegistry readCSV) throws ResourceRouterUnavailableException {
        return Mockito.mock(LoadGraphService.class);
    }

    @Bean
    public FindCheapestRouteService findCheapestRouteService() {
        return Mockito.mock(FindCheapestRouteService.class);
    }

    @Bean
    public CalculateCheapestWay calculateCheapestWay() {
        return Mockito.mock(CalculateCheapestWay.class);
    }

    @Bean
    public InsertRouteService insertRouteService() {
        return Mockito.mock(InsertRouteService.class);
    }

}


