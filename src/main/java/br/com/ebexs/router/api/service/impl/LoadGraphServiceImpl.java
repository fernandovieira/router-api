package br.com.ebexs.router.api.service.impl;

import br.com.ebexs.router.api.service.LoadGraphService;
import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import br.com.ebexs.routerfinderservice.service.GenerateGraph;
import br.com.ebexs.routerfinderservice.service.ReadRouteRegistry;
import br.com.ebexs.routerfinderservice.service.impl.GenerateGraphImpl;
import br.com.ebexs.routerfinderservice.service.impl.ReadCSV;
import org.springframework.core.env.Environment;


import java.util.List;

public class LoadGraphServiceImpl implements LoadGraphService {

    private Environment environment;
    private ReadRouteRegistry<RouteModel> readCSV;

    public LoadGraphServiceImpl(Environment environment, ReadRouteRegistry<RouteModel> readCSV)
    {
        this.environment = environment;
        this.readCSV = readCSV;
    }

    @Override
    public Graph loadGraph() throws ResourceRouterUnavailableException {

        List<RouteModel> routeModels = readCSV.read(environment.getProperty("file.path"));

        GenerateGraph generateGraph = new GenerateGraphImpl(routeModels);

        return generateGraph.generateGraph();
    }
}
