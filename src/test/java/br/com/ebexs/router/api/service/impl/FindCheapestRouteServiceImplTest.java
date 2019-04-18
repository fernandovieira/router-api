package br.com.ebexs.router.api.service.impl;

import br.com.ebexs.router.api.service.FindCheapestRouteService;
import br.com.ebexs.router.api.service.LoadGraphService;
import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteFind;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class FindCheapestRouteServiceImplTest {

    @Mock
    private CalculateCheapestWay calculateCheapestWay;
    @Mock
    private LoadGraphService loadGraphService;
    private  RouteFind rf;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        rf = new RouteFind();
        rf.setRoute("BRC - GRU - SAN");
        rf.setTotal(10);

        when(loadGraphService.loadGraph()).thenReturn(new Graph());
        when(calculateCheapestWay.doTheMath(any(Graph.class), any(Vertex.class), any(Vertex.class)))
                .thenReturn(rf);
    }

    @Test()
    public void shouldFindRoute() throws Exception {



        FindCheapestRouteService findCheapestRouteService =
                new FindCheapestRouteServiceImpl(calculateCheapestWay, loadGraphService);



        ResponseEntity<Map<Integer, List<String>>> routeFind
                = findCheapestRouteService.findRoute("BRC", "SAN");

        Assert.assertEquals(routeFind.getStatusCode().value(), 200);
        Assert.assertEquals(routeFind.getBody().get(10), rf.getRoute());

    }

    @Test()
    public void shouldResponseStatus422FromEmpty() throws Exception {



        FindCheapestRouteService findCheapestRouteService =
                new FindCheapestRouteServiceImpl(calculateCheapestWay, loadGraphService);


        ResponseEntity<Map<Integer, List<String>>> routeFind
                = findCheapestRouteService.findRoute(null, "SAN");

        Assert.assertEquals(routeFind.getStatusCode().value(), 422);
    }

    @Test()
    public void shouldResponseStatus422ToEmpty() throws Exception {

        FindCheapestRouteService findCheapestRouteService =
                new FindCheapestRouteServiceImpl(calculateCheapestWay, loadGraphService);
        ResponseEntity<Map<Integer, List<String>>> routeFind
                = findCheapestRouteService.findRoute("BRC", "");

        Assert.assertEquals(routeFind.getStatusCode().value(), 422);
    }

    @Test()
    public void shouldThrowExceptionStatusCode500() throws Exception {

        RouteFind rf = new RouteFind();
        rf.setRoute("BRC - GRU - SAN");
        rf.setTotal(10);

        FindCheapestRouteService findCheapestRouteService =
                new FindCheapestRouteServiceImpl(calculateCheapestWay, loadGraphService);

        doThrow(new ResourceRouterUnavailableException("")).when(loadGraphService).loadGraph();


        ResponseEntity<Map<Integer, List<String>>> routeFind
                = findCheapestRouteService.findRoute("GRU", "SAN");

        Assert.assertEquals(routeFind.getStatusCode().value(), 500);
    }
}
