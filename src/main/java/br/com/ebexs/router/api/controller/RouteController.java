package br.com.ebexs.router.api.controller;

import br.com.ebexs.router.api.service.FindCheapestRouteService;
import br.com.ebexs.router.api.service.InsertRouteService;
import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfindermodel.model.RouteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RouteController {

    @Autowired
    FindCheapestRouteService findCheapestRouteService;

    @Autowired
    InsertRouteService insertRouteService;


    @GetMapping("/cheapeastroutes/{from}/{to}")
    public ResponseEntity findRoutes(@PathVariable String from, @PathVariable String to) {

       return findCheapestRouteService.findRoute(from, to);

    }

    @PostMapping("/routes")
    public ResponseEntity findRoutes(@RequestBody RouteModel routeModel) throws IOException {

        return insertRouteService.insertRoute(routeModel);

    }
}
