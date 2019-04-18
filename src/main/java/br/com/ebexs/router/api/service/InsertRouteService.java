package br.com.ebexs.router.api.service;

import br.com.ebexs.routerfindermodel.model.RouteModel;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface InsertRouteService {

    ResponseEntity insertRoute(RouteModel routeModel) throws IOException;
}
