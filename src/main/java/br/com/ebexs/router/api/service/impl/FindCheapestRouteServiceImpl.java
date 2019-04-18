package br.com.ebexs.router.api.service.impl;

import br.com.ebexs.router.api.service.FindCheapestRouteService;
import br.com.ebexs.router.api.service.LoadGraphService;
import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteFind;
import br.com.ebexs.routerfindermodel.model.Vertex;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindCheapestRouteServiceImpl implements FindCheapestRouteService {

    private CalculateCheapestWay calculateCheapestWay;
    private LoadGraphService loadGraphService;

    @Autowired
    public FindCheapestRouteServiceImpl(CalculateCheapestWay calculateCheapestWay,
                                    LoadGraphService loadGraphService) {
        this.calculateCheapestWay = calculateCheapestWay;
        this.loadGraphService = loadGraphService;

    }

    @Override
    public ResponseEntity findRoute(String from,
                                    String to
                                    ) {

        if (from == null || from.isEmpty()) {
            return ResponseEntity.status(422).body("Field ''from'' must be not equal null or empty");
        }

        if (to == null || to.isEmpty()) {
            return ResponseEntity.status(422).body("Field ''to'' must be not equal null or empty");
        }

        try {
            Graph graph = loadGraphService.loadGraph();
            RouteFind routeFind = calculateCheapestWay.doTheMath(graph, new Vertex(from), new Vertex(to));
            Map<Integer, List<String>> result = new HashMap<>();
            result.put(routeFind.getTotal(), routeFind.getRoute());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
