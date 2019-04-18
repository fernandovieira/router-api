package br.com.ebexs.router.api.service.impl;

import br.com.ebexs.router.api.service.LoadGraphService;
import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.GenerateGraph;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;
import br.com.ebexs.routerfinderservice.service.impl.GenerateGraphImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LoadGraphServiceImplTest {

    @Mock
    private Environment environment;
    @Mock
    private ReadRouteRegistry<RouteModel> readCSV;

    private List<RouteModel> routeModels;

    private LoadGraphService loadGraphService;

    private Graph graph;


    @Before
    public void setup() throws ResourceRouterUnavailableException {
        MockitoAnnotations.initMocks(this);
        routeModels = new ArrayList<>();

        RouteModel routeModel = new RouteModel();
        routeModel.setFrom("BRU");
        routeModel.setFrom("GRU");
        routeModel.setPrice("10");

        routeModels.add(routeModel);

        when(readCSV.read(anyString())).thenReturn(routeModels);

        loadGraphService = new LoadGraphServiceImpl(environment, readCSV);
    }

    @Test
    public void shouldGenerateGraph() throws ResourceRouterUnavailableException {


        Map<Vertex, Map<Vertex, String>> node = new HashMap<>();
        Map<Vertex, String> vertex = new HashMap<>();
        vertex.put(new Vertex("GRU"), "10");
        node.put(new Vertex("BRU"), vertex);

        graph = new Graph();
        graph.setNode(node);

        Assert.assertEquals(loadGraphService.loadGraph().getNode().get("BRU"), graph.getNode().get("BRU"));

    }
}
