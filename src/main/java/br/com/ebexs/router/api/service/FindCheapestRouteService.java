package br.com.ebexs.router.api.service;

import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfinderservice.service.CalculateCheapestWay;
import org.springframework.http.ResponseEntity;

public interface FindCheapestRouteService {

    ResponseEntity findRoute(String from, String to);
}
