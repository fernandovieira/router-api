package br.com.ebexs.router.api.service;

import br.com.ebexs.routerfindermodel.model.Graph;
import br.com.ebexs.routerfinderservice.exception.ResourceRouterUnavailableException;


public interface LoadGraphService {

    Graph loadGraph() throws ResourceRouterUnavailableException;
}
